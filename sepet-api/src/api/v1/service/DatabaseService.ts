import mongoose from "mongoose";
import { DATABASE_NAME, DATABASE_URL } from "../config/DatabaseConfig";

(async() => {
    mongoose.connect(`${DATABASE_URL}${DATABASE_NAME}`)
    console.log("Successfully connected !")
})()