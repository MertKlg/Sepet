import { Request,Response,NextFunction } from "express"
import { StatusCode, StatusMessage } from "../model/enum/StatusEnum"
import ResponseModel from "../model/response/ResponseModel"
import DeliveryAddressModel from "../model/entity/DeliveryAddressEntity"
import IDeliveryAddress from "../model/interface/IDeliveryAddress"

export async function getAddress(req : Request, res : Response, next : NextFunction) {
    try{
        const userId = res.locals.userId

        const {id} = req.params

        const query = []

        if(id){
            query.push({ _id : id})
        }

        if(userId){
            query.push({user : userId})
        }

        const findAddress = await DeliveryAddressModel.find({$and : query})

        const dto = findAddress.map(e => {
            return {
                "_id" : e._id,
                "name" : e.name,
                "fullAddressText" : e.fullAddressText,
                "neighbourhood" : e.neighbourhood,
                "town" : e.town
            }
        })

        return res.json(new ResponseModel(StatusMessage.SUCCESS, StatusCode.SUCCESS, dto))
    }catch(e){
        next(e)
    }
}



export async function addAddress(req : Request, res : Response, next : NextFunction) {
    try{


        const userId = res.locals.userId
        const address = req.body as IDeliveryAddress

        const findUserAddresses = await DeliveryAddressModel.find({user : userId})

        if(findUserAddresses.length == 5)
            throw new ResponseModel("You just only add max 5 address", StatusCode.BAD_REQUEST)

        await DeliveryAddressModel.create({
            user: userId,
            name : address.name,
            fullAddressText : address.fullAddressText,
            neighbourhood : address.neighbourhood,
            town : address.town
        })
        
        res.json(new ResponseModel("Address successfully added",StatusCode.SUCCESS))
    }catch(e){
        next(e)
    }
}

export async function updateAddress(req : Request, res : Response, next : NextFunction) {
    try{

        const userId = res.locals.userId
        const address = req.body as IDeliveryAddress

        const dbAddress = await DeliveryAddressModel.findOne({_id : address._id,user : userId})
        if(dbAddress == null || dbAddress == undefined){
            throw new ResponseModel("Adress not found", StatusCode.NOT_FOUND)
        }


        await dbAddress?.updateOne(address) 

        res.json(new ResponseModel("Address successfully updated",StatusCode.SUCCESS))
    }catch(e){
        next(e)
    }
}


export async function deleteAddress(req : Request, res : Response, next : NextFunction) {
    try{

        const userId = res.locals.userId
        const {id} = req.params
        
        const dbAddress = await DeliveryAddressModel.findOne({_id : id, user : userId})
        if(dbAddress == null || dbAddress == undefined){
            throw new ResponseModel("Adress not found", StatusCode.NOT_FOUND)
        }

        await dbAddress.deleteOne()

        res.json(new ResponseModel("Address successfully deleted",StatusCode.SUCCESS))
    }catch(e){
        next(e)
    }
}
