import mongoose from "mongoose";
import { orderCodes } from "../codes/OrderCodes";

const orderModel = new mongoose.Schema({
    products : [
        {
            product : {
                type : mongoose.Schema.ObjectId,
                ref : "Product"
            },
            count : Number
        },
    ],

    user : {
        type : mongoose.Schema.ObjectId,
        ref : "User"
    },

    total : Number,
    
    address : {
        town : String,
        neighbourhood : String,
        fullAddressText : String,
    },

    orderDate : {
        type : Date,
        default : Date.now()
    },

    status : {
        type : String,
        default : orderCodes.ORDER_ORDERED
    }
})


const orderEntity = mongoose.model("Order", orderModel)

export default orderEntity