import { Request, Response, NextFunction } from "express"
import ResponseModel from "../model/response/ResponseModel"
import { StatusCode } from "../model/enum/StatusEnum"
import type ICart from "../model/interface/ICart"
import cartModel from "../model/entity/CartEntity"
import mongoose from "mongoose"
import productEntity from "../model/entity/ProductEntity"
import calculateCartTotal from "../utility/CalcCartTotal"


export async function addToCart(req: Request, res: Response, next: NextFunction) {
    try {

        const { product, count } = req.body as ICart

        const userId = res.locals.userId


        var findActiveCart = await cartModel.findOne({ user: userId })

        if (!findActiveCart) {
            await createNewCart(userId, async (e: mongoose.Document) => {
                findActiveCart = await cartModel.findOne({ _id: e._id })
            });
        }

        const findProduct = await productEntity.findOne({ _id: new mongoose.Types.ObjectId(product), closed: false })

        if (!findProduct)
            throw new ResponseModel("Product not found", StatusCode.NOT_FOUND)


        if (findActiveCart) {

            const findProductOfCart = findActiveCart.products.findIndex(e => e.product?._id.toString() === findProduct._id.toString())
            if(findProductOfCart == -1){
                findActiveCart.products.push({product : findProduct._id, count : count})
            }else if(count == 0){
                findActiveCart.products.splice(findProductOfCart, 1)
            }else{
                findActiveCart.products[findProductOfCart].count = count
            }

            // If user add a new product or update a products total, this func every time calculate cart total price
            await calculateCartTotal(findActiveCart.products, (total, exception) => {
                if (exception != null)
                    throw new ResponseModel(exception, StatusCode.SOMETHING_WENT_WRONG)

                findActiveCart!.total = Number(total)
            })

            // Save new updates to the database
            await findActiveCart.save()

            return res.json(new ResponseModel(`${findProduct.name} successfully added`, StatusCode.SUCCESS))
        }

        res.json(new ResponseModel(`No active cart founded`, StatusCode.BAD_REQUEST))

    } catch (e) {
        next(e)
    }
}

export const getCart = async (req: Request, res: Response, next: NextFunction) => {
    try {
        const userId = res.locals.userId;


        let userCart = await cartModel.findOne({ user: userId })
            .populate("products.product", ["_id", "name", "image", "price", "discountPrice", "description"])
            .sort({ name : "descending" })
            .lean();

        if (!userCart) {
            throw new ResponseModel("No card found", 404)
        }

        const dto = userCart?.products.map(e => {
            return {
                "product": e.product,
                "count": e.count
            }
        })

        res.json(new ResponseModel("SUCCESS", 200, [{
            _id: userCart?._id,
            products: dto,
            total: userCart?.total,
        }]));
    } catch (e) {
        next(e)
    }
}


const createNewCart = async (userId: string, callback?: (cart: mongoose.Document) => void) => {
    const newCart = await cartModel.create({ user: userId, total: 0 });
    await newCart.save()
    if (callback) {
        callback(newCart)
    }
};



export async function updateCart(req: Request, res: Response, next: NextFunction) {
    try {
        const { cartId } = req.params

        const userId = res.locals.userId
        const filter = { _id: cartId, user: userId }

        const findUserCart = await cartModel.findOne(filter)

        if (!findUserCart)
            throw new ResponseModel("Cart not found", StatusCode.NOT_FOUND)

        const body = req.body as ICart

        findUserCart.products.map((e, index) => {
            if (body.count == 0 && body.product == (e.product ?? "")) {
                findUserCart.products.splice(index, 1)
            } else if (body.product == (e.product ?? "")) {
                e.count = body.count
            }
        })

        await calculateCartTotal(findUserCart.products, (total, exception) => {
            if (exception != null)
                throw new ResponseModel(exception, StatusCode.SOMETHING_WENT_WRONG)
            findUserCart.total = Number(total)
        })

        await findUserCart.save()

        res.json(new ResponseModel("Cart updated", StatusCode.SUCCESS))
    } catch (e) {
        next(e)
    }
}


export const cartDelete = async (req: Request, res: Response, next: NextFunction) => {
    try {
        const { cartId } = req.params
        const userId = res.locals.userId
        const findCart = await cartModel.findOne({ user: userId, _id: cartId })
        if (!findCart)
            throw new ResponseModel("Cart not found", 400)

        await findCart.deleteOne()

        res.json(new ResponseModel("Cart deleted", StatusCode.SUCCESS))
    } catch (e) {
        next(e)
    }
}