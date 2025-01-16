import { Request, Response,NextFunction } from "express"
import userEntity from "../model/entity/UserEntity"
import ResponseModel from "../model/response/ResponseModel"
import cartModel from "../model/entity/CartEntity"
import orderEntity from "../model/entity/OrderEntity"
import deliveryAddressModel from "../model/entity/DeliveryAddressEntity"

export const getProfile = async(req : Request, res : Response, next : NextFunction) => {
    try{
        const userId = res.locals.userId

        const user = await userEntity.findOne({_id : userId}).lean()

        if(!user)
            throw new ResponseModel("User not found", 404)


        res.json(new ResponseModel("SUCCESS", 200,  [{ 
            username: user.username,
            email: user.email,
            image: user.image ?? "",
            phone: user.phone
        }]));

    }catch(e){
        next(e)
    }
}

export const updateProfile = async (req: Request, res : Response, next : NextFunction) => {
    try{
        const {username, email, phone, image} = req.body
        const userId = res.locals.userId
        await userEntity.findOneAndUpdate({_id: userId}, { username : username, email : email, phone : phone, image : image})

        res.json(new ResponseModel("Profile successfully updated", 200))
    }catch(e){
        next(e)
    }
}


export const deleteProfile = async (req: Request, res : Response, next : NextFunction) => {
    try{
        const userId = res.locals.userId
        
        const user = await userEntity.findOne({_id : userId})

        if(!user)
            throw new ResponseModel("User not found", 404)

        await user.deleteOne()
  
        await cartModel.findOne({user : user._id}).deleteMany()
        
        await orderEntity.find({user : user._id}).deleteMany()

        await deliveryAddressModel.find({user : user._id}).deleteMany()


        res.json(new ResponseModel("Profile deleted",200 ))
    }catch(e){
        next(e)
    }
}