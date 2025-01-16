import { Request, Response, NextFunction } from "express";
import ResponseModel from "../model/response/ResponseModel";
import { StatusCode, StatusMessage } from "../model/enum/StatusEnum";
import cartModel from "../model/entity/CartEntity";
import orderEntity from "../model/entity/OrderEntity";
import deliveryAddressModel from "../model/entity/DeliveryAddressEntity";
import IOrder from "../model/interface/IOrder";
import { verifyOrderCode, orderCodes } from "../model/codes/OrderCodes";


export const orders = async(req : Request, res : Response, next : NextFunction) => {
    try{
        const userId = res.locals.userId

        const getUserOrders = await orderEntity.find({user : userId},{ 'products' : { $slice : 3 } })
        .populate({
            path : "products.product",
            select : "image -_id"
        })
        .populate("address",["-_id"])
        .populate("user", ["username","-_id"])


        const dto = getUserOrders.map(e => {
            return {
                _id : e._id,
                products : e.products,
                orderDate : e.orderDate,
                status : e.status,
                address : e.address,
                total : e.total,
                user : e.user
            }
        })

        return res.json(new ResponseModel(StatusMessage.SUCCESS, StatusCode.SUCCESS, dto))
    }catch(e){
        next(e)
    }
}    

export const order = async(req : Request, res : Response, next : NextFunction) => {
    try{
        const { cart } = req.params
        const { address } = req.body as IOrder
        const user = res.locals.userId

        const findCart = await cartModel.findOne({ user : user, _id : cart })
        
        const findAddress = await deliveryAddressModel.findOne({ user : user, _id: address })


        if(!findAddress)
            throw new ResponseModel("No address found", 400)

        if(!findCart)
            throw new ResponseModel("No cart found", 404)

        await orderEntity.create({
            user : user,
            address : {
                town : findAddress.town,
                neighbourhood : findAddress.neighbourhood,
                fullAddressText : findAddress.fullAddressText,
            },
            products : findCart.products,
            total : findCart.total
        })

        await findCart.deleteOne()

        res.json(new ResponseModel("We're take your order!", StatusCode.SUCCESS))
    }catch(e){
        next(e)
    }
}


export const updateOrder = async (req : Request, res : Response, next : NextFunction) => {
    try{
        const userId = res.locals.userId
        const { order } = req.params
        const { address,orderStatus } = req.body


        const findOrder = await orderEntity.findOne({_id : order , user : userId})
        if(!findOrder)
            throw new ResponseModel("Order not found", 400)


        if(address){
            const findAddress = await deliveryAddressModel.findOne({_id : address, user : userId})
            if(!findAddress){
                throw new ResponseModel("Address not found", 400)
            }


            findOrder.address = {
                town : findAddress.town,
                neighbourhood : findAddress.neighbourhood,
                fullAddressText : findAddress.fullAddressText
            }
        }

        if(orderStatus){

            if(findOrder.status != orderCodes.ORDER_ORDERED){
                const message = (findOrder.status == orderCodes.ORDER_SHIPPED) ? ("Your order now have shipped, shipped has status cannot cancable") : (orderCodes.ORDER_DELIVERED) ? ("Your order delivered, delivered orders cannot cancable") : "Your order already cancaled"
                return res.json(new ResponseModel(message, 200))
            }
            console.log(orderStatus)
            const findStatus = verifyOrderCode(orderStatus)
            console.log(findStatus)
            if(findStatus != undefined){
                findOrder.status = findStatus
            }
        }

        
        await findOrder.save()
        res.json(new ResponseModel("Order updated", 200))
    }catch(e){
        next(e)
    }
}


export const orderDetail =  async (req : Request, res : Response, next : NextFunction) => {
    try{

        const { id } = req.params
        const user = res.locals.userId

        const findOrder = await orderEntity.findOne({_id : id, user : user})
        .populate("products.product" , ["-_id", "image", "name", "price", "discountPrice"])
        .populate("address" , ["-_id"])
        .populate("user", ["-_id"])
        .lean()
        .exec()

        if(!findOrder)
            throw new ResponseModel("Order not found", 404)


        res.json(new ResponseModel("Success", 200 , [findOrder]))
    }catch(e){
        next(e)
    }
}