import mongoose from "mongoose";

const userSchema = new mongoose.Schema({
    username : {
        type:String,
        required: true
    },

    email : {
        type:String,
        required: true
    },

    password : {
        type:String,
        required: true
    },

    address : {
        type : mongoose.Schema.ObjectId,
        ref : "DeliveryAddress"  
    },

    phone : {
        type:String,
        required: true
    },

    image : {
        type : String,
        required : false
    },
    role : {
        type:Number,
        required: true
    },
})

const userEntity = mongoose.model("User", userSchema)
export default userEntity
