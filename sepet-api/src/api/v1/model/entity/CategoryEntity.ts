import mongoose from "mongoose";

const categoryScheme = new mongoose.Schema({
    name : {
        type : String,
        required : true
    },
    
    image : {
        type : String,
        required : true
    },
    
    queryName : {
        type : String,
        required : true
    }
})

const categoryEntity = mongoose.model("Category", categoryScheme)

export default categoryEntity