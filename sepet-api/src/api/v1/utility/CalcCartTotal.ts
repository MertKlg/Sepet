import mongoose, { MongooseError } from "mongoose";
import productEntity from "../model/entity/ProductEntity";

const calculateCartTotal = async (products : mongoose.Types.DocumentArray<{product?: mongoose.Types.ObjectId | null | undefined;count?: number | null | undefined;}>, callback : (total : Number, exception : any | null ) => void ) => {
    try {
        const cartTotal = await products.reduce(async (total, product) => {
            const findProduct = await productEntity.findOne({ _id : product?.product , closed : false })
            if(findProduct){
                return await total + (((findProduct.discountPrice != 0) ? findProduct.discountPrice ?? 0 : findProduct.price) * (product.count ?? 1));
            }

            return total
        },Promise.resolve(0))

        callback(cartTotal, null)
    } catch (e) {
        if(e instanceof Error ){
            callback(0 , e.message)
        }else if(e instanceof MongooseError){
            callback(0, e.message)
        }else{
            callback(0, e)
        }
        
    }
}

export default calculateCartTotal