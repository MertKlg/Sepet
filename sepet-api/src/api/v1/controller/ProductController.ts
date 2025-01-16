import { Request, Response, NextFunction } from "express";
import productEntity from "../model/entity/ProductEntity";
import IProduct from "../model/interface/IProduct";
import { fileExists, removeFile, removeFiles } from "../service/MulterService";
import ResponseModel from "../model/response/ResponseModel";
import { validationResult } from "express-validator";
import { StatusCode, StatusMessage } from "../model/enum/StatusEnum";
import mongoose from "mongoose";

export async function getProducts(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const { min, max, category, minPrice, maxPrice } = req.query;
    const query = [];

    const minQuery = Number(parseInt(min?.toString() ?? "0")) || 0;
    const maxQuery = Number(parseInt(max?.toString() ?? "10")) || 10;

    const convertMinPrice = Number(minPrice?.toString() ?? "0");
    const convertMaxPrice = Number(maxPrice?.toString() ?? Number.MAX_VALUE.toString());

    query.push({ price: { $gte: convertMinPrice, $lte: convertMaxPrice } });

    if (category) {
      const categories = category
        .toString()
        .split(",")
        .map((e) => {
          try {
            return new mongoose.Types.ObjectId(e.toString());
          } catch (e) {
            return undefined;
          }
        })
        .filter((e) => e != undefined);

      query.push({ category: { $in: categories } });
    }

    const filter = query.length > 0 ? { $and: query } : {};

    const result = await productEntity.aggregate([
      { $match: filter },
      {
        $facet: {
          products: [{ $skip: minQuery }, { $limit: maxQuery - minQuery }],
          totalLength: [{ $count: "total" }],
        },
      },
    ]);

    const products: any[] = [];

    result[0]["products"].forEach((e: any) => {
      products.push({
        _id: e._id,
        name: e.name,
        description: e.description,
        price: e.price,
        image: e.image,
        discountPrice: e.discountPrice,
      });
    });

    const totalFilterSize = Math.ceil(
      (result[0].totalLength.length ? result[0].totalLength[0].total : 1) /
        (maxQuery - minQuery)
    );

    const minMaxValues: { min: number; max: number, value : number }[] = [{ min: 0, max: 10, value : 1 }];

    for (let i = 1; i < totalFilterSize; i++) {
      const previousMinMax = minMaxValues[i - 1];
      minMaxValues.push({ min: previousMinMax.max, max: previousMinMax.max + 10, value : previousMinMax.value + 1 });
    }

    res.json(
      new ResponseModel(StatusMessage.SUCCESS, 200, [
        {
          products: products,
          minMax: minMaxValues,
        }
      ])
    );
  } catch (e) {
    next(e);
  }
}

export async function getProduct(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const { id } = req.params;
    const findProduct = await productEntity.find({ _id: id });

    if (findProduct == null) throw new ResponseModel("Product not found", 404);

    const dto = findProduct.map((e) => {
      return {
        _id: e.id,
        name: e.name,
        description: e.description,
        price: e.price,
        image: e.image,
        discountPrice: e.discountPrice,
      };
    });

    res.json(new ResponseModel(StatusMessage.SUCCESS, StatusCode.SUCCESS, dto));
  } catch (e) {
    next(e);
  }
}

export async function createProduct(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const validation = validationResult(req);
    if (!validation.isEmpty()) throw new ResponseModel("FAILED", 400);

    if (!req.files || req.files.length === 0)
      throw new ResponseModel("No images were uploaded", 400);

    const productInfo = req.body as IProduct;
    const queryName = productInfo.name.generateQueryName();
    const findProduct = await productEntity.find({ queryName: queryName });
    const images = req.files as Array<Express.Multer.File>;

    if (findProduct.length > 0) {
      images.map((e) => {
        removeFile(e.filename);
      });
      throw new ResponseModel("This product already exists", 400);
    }

    await productEntity.create({
      name: productInfo.name,
      queryName: queryName,
      description: productInfo.description,
      price: productInfo.price,
      image: images.map((e) => e.filename),
      createdDate: Date.now(),
      updatedDate: Date.now(),
      discountStatus:
        productInfo.discount === undefined ? 0 : productInfo.discount,
      discountPrice: productInfo.discount === undefined ? false : true,
      category: productInfo.categoryId,
    });

    res.json(new ResponseModel("Product successfully added", 200));
  } catch (e) {
    next(e);
  }
}

export async function deleteMany(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const errors: string[] = [];
    const ids = req.body as IProduct[];

    const convertObjectIds = ids
      .filter((e) => e._id.length == 24)
      .map((e) => mongoose.Types.ObjectId.createFromHexString(e._id));

    if (convertObjectIds === undefined || convertObjectIds.length <= 0)
      throw new ResponseModel("Products not found", 400);

    const findedProducts = await productEntity.find({
      _id: { $in: convertObjectIds },
    });

    if (findedProducts !== undefined) {
      findedProducts.map((e) => {
        removeFiles(e.image);
        return ids.splice(ids.indexOf(e.id.toString()), 1);
      });
      await productEntity.deleteMany({ _id: findedProducts });
      ids.map((e) => {
        errors.push(`${e._id} product not be found`);
      });
    }

    res.json(new ResponseModel("Products successfully deleted", 200));
  } catch (e) {
    next(e);
  }
}

export async function deleteProduct(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const valResult = validationResult(req);
    if (!valResult.isEmpty()) throw new ResponseModel("FAILED", 400);

    const { id } = req.params;

    if (id == undefined) throw new ResponseModel("Product id required", 400);

    await productEntity
      .findByIdAndDelete(new mongoose.Types.ObjectId(id))
      .catch((e) => {
        throw new ResponseModel(e, 400);
      });

    res.json(new ResponseModel("Product successfully removed", 200));
  } catch (e) {
    next(e);
  }
}

export async function updateProduct(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const valResult = validationResult(req);
    if (!valResult.isEmpty()) throw new ResponseModel("FAILED", 400);

    const { _id, name, description, price, discount, selectedImages } =
      req.body as IProduct;

    const productDb = await productEntity.findById(_id);

    if (productDb === undefined)
      throw new ResponseModel("Product not found", 404);

    const productImages = productDb?.image;

    if (req.files != undefined) {
      const images = req.files as Array<Express.Multer.File>;

      if (selectedImages !== undefined) {
        selectedImages.map((sImages) => {
          if (fileExists(sImages) && productImages !== undefined) {
            removeFile(sImages);
            productImages.splice(productImages.indexOf(sImages), 1);
          }
        });
      }

      if (images !== undefined && productImages !== undefined) {
        images.forEach((element) => {
          productImages.push(element.filename);
        });
      }
    }

    await productEntity
      .updateOne({
        image: productImages,
        name: name,
        description: description,
        price:
          price === undefined || Number.parseFloat(`${price}`) === 0
            ? 0
            : price,
        discountPrice:
          discount === undefined || Number.parseFloat(`${discount}`) === 0
            ? 0.0
            : discount,
        discountStatus:
          discount === undefined || Number.parseFloat(`${discount}`) === 0
            ? false
            : true,
        queryName: name.generateQueryName(),
      })
      .catch((e) => {
        throw new ResponseModel(e, 400);
      });

    res.json(new ResponseModel("Product successfully updated", 200));
  } catch (e) {
    next(e);
  }
}

export async function updateMany(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const errors: string[] = [];
    const products = req.body as IProduct[];
    const productsDto = products.map((e) => {
      return {
        _id: mongoose.Types.ObjectId.createFromHexString(e._id),
        name: e.name,
        description: e.description,
        price: e.price,
        discount: e.discount,
        discountStatus:
          e.discount == undefined || Number.parseFloat(`${e.discount}`) === 0
            ? false
            : true,
        productClose:
          e.price == undefined || Number.parseFloat(`${e.price}`) === 0
            ? true
            : false,
        queryName: e.name.generateQueryName(),
      };
    });

    const valid = validationResult(req);
    if (!valid.isEmpty()) throw new ResponseModel("Failed", 400);

    const findProducts = await productEntity.find({
      _id: { $in: productsDto.map((e) => e._id) },
    });

    if (findProducts === undefined || findProducts.length <= 0)
      throw new ResponseModel("Products not found", 400);

    productsDto.map(async (e) => {
      try {
        await productEntity.updateOne(e);
      } catch (e) {
        errors.push(`${e} this product cannot be updated try update later`);
      }
    });

    res.json(new ResponseModel("Products updated successfully", 200));
  } catch (e) {
    next(e);
  }
}
