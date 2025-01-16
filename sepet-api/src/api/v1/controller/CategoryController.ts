import { Request,Response,NextFunction } from "express";
import { validationResult } from "express-validator";
import ResponseModel from "../model/response/ResponseModel";
import { StatusCode, StatusMessage } from "../model/enum/StatusEnum";
import ICategory from "../model/interface/ICategory";
import categoryEntity from "../model/entity/CategoryEntity";
import { removeFile } from "../service/MulterService";
import ERROR_CODES from "../model/enum/ResponseCodes";

export async function getCategories(req : Request, res : Response, next : NextFunction){
    try{
        var query = {}
        const { id } = req.params
        
        if(id){
            query = {_id : id}
        }

        const category = await categoryEntity.find(query).lean()

        if(!category){
            throw new ResponseModel("Category not found", StatusCode.BAD_REQUEST)
        }

        
        const dto = category.map(e => {
            return {
                "_id" : String(e._id),
                "name" : e.name,
                "image" : e.image
            }
        })


        res.json(new ResponseModel("Success", 200, dto))
    }catch(e){
        next(e)
    }
}

export async function createCategory(req : Request, res : Response, next : NextFunction){
    try{
        const valid = validationResult(req)
        if(!valid.isEmpty())
            throw new ResponseModel(StatusMessage.BAD_REQUEST, StatusCode.BAD_REQUEST)
        
        const {name} = req.body as ICategory
        const queryName = name?.generateQueryName()

        const findCategory = await categoryEntity.findOne({queryName : queryName})

        if(findCategory != undefined || findCategory != null)
            throw new ResponseModel("This category already exists", StatusCode.BAD_REQUEST)

        if(req.file == undefined)
            throw new ResponseModel("Category image required", StatusCode.BAD_REQUEST)

        await categoryEntity.create({
            name : name,
            image : req.file.filename,
            queryName : queryName
        })

        res.status(StatusCode.CREATED).json(new ResponseModel(StatusMessage.CREATED, StatusCode.CREATED))
    }catch(e){
        if(req.file != undefined)
            removeFile(req.file.filename)
        next(e)
    }
}

export async function deleteCategory(req : Request, res : Response, next : NextFunction){
    try{
        const valid = validationResult(req)
        if(!valid.isEmpty())
            throw new ResponseModel(StatusMessage.BAD_REQUEST, StatusCode.BAD_REQUEST)
        
        const category = req.body as ICategory

        const findCategory = await categoryEntity.findById(category.id)
        if(findCategory === undefined || findCategory === null)
            throw new ResponseModel("Category not found", StatusCode.NOT_FOUND)

        removeFile(findCategory.image)
        await findCategory.deleteOne()

        res.json(new ResponseModel("Category successfully deleted", StatusCode.SUCCESS))
    }catch(e){
        next(e)
    }
}

export async function updateCategory(req : Request, res : Response, next : NextFunction){
    try{
        const valid = validationResult(req)
        if(!valid.isEmpty())
            throw new ResponseModel(StatusMessage.BAD_REQUEST, StatusCode.BAD_REQUEST)
        
        const category = req.body as ICategory

        const findCategory = await categoryEntity.findById(category.id)

        if(findCategory === undefined || findCategory === null)
            throw new ResponseModel("Category not found", StatusCode.NOT_FOUND)

        removeFile(findCategory.image)

        await findCategory.updateOne({
            name : (category?.name == undefined || category?.name.length <= 0) ? findCategory.name : category.name ,
            image: (req.file == undefined) ? findCategory.image : req.file?.filename,
            queryName : (category?.name == undefined || category?.name.length <= 0) ? findCategory.queryName : category.name.generateQueryName()
        })

        res.json(new ResponseModel("Category successfully updated", StatusCode.SUCCESS))
    }catch(e){
        if(req.file != undefined)
            removeFile(req.file.filename)
        next(e)
    }
}