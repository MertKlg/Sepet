import mongoose from "mongoose";

const deliveryAddressScheme = new mongoose.Schema({
    user : {
        type : mongoose.Types.ObjectId,
        ref : "User"
    },
    name : String,
    town : String,
    neighbourhood : String,
    fullAddressText : String,
    
})

const deliveryAddressModel = mongoose.model("DeliveryAddress",deliveryAddressScheme)
export default deliveryAddressModel