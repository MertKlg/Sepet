import mongoose,{ model, Schema, } from "mongoose";

const cartSchema = new Schema({
    user : {
        type : mongoose.Schema.ObjectId,
        ref : "User"
    },
    
    products: [
        {
            product : {
                type : mongoose.Schema.ObjectId,
                ref : "Product"
            },
            count : Number
        }
    ],

    total : {
        type : Number,
        default : 0
    },
})

const cartModel = model("Cart", cartSchema)

export default cartModel