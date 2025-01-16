import { Router } from "express";
import { addToCart, cartDelete, getCart, updateCart } from "../controller/CartController";
import { accessTokenValidate } from "../middleware/Others/VerifyToken";
import { inputValidators } from "../middleware/Others/InputValidatorMiddleware";
import { check } from "express-validator";


const cartRouter = Router()


cartRouter.get("/all", accessTokenValidate,getCart)

cartRouter.post("/add", [
    accessTokenValidate,

    inputValidators([
        check("product")
        .escape()
        .isMongoId()
        .withMessage("Invalid product id"),

        check("count")
        .escape()
        .isNumeric()
        .withMessage("Product count just only numeric")
        
    ])

],addToCart)


cartRouter.put("/update/:cartId?", [
    accessTokenValidate, 
    inputValidators([

        check("cartId")
        .escape()
        .isMongoId()
        .withMessage("Invalid cart id"),

        check("product")
        .escape()
        .isMongoId()
        .withMessage("Invalid product id"),

        check("count")
        .escape()
        .isNumeric()
        .withMessage("Product count just only numeric")
        .optional(),
    ])
], updateCart)



cartRouter.delete("/delete:/cartId?", [
    accessTokenValidate,
    inputValidators([
        check("cartId")
        .isMongoId()
        .withMessage("Invalid cart id")
    ])
], cartDelete)


export default cartRouter