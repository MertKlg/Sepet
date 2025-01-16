import { Router } from "express";
import { createProduct, deleteMany, deleteProduct, getProducts, updateProduct, updateMany, getProduct } from "../controller/ProductController";
import multerService from "../service/MulterService";
import { accessTokenValidate } from "../middleware/Others/VerifyToken";
import { check } from "express-validator";
import { inputValidators } from "../middleware/Others/InputValidatorMiddleware";
import ERROR_CODES from "../model/enum/ResponseCodes";
import { adminValidator } from "../middleware/Others/RoleValidator";
const productRouter = Router()

productRouter.get("/all",[
    inputValidators([
        check("min")
        .escape()
        .isNumeric()
        .withMessage("Min just only numeric value")
        .optional(),

        check("max")
        .escape()
        .isNumeric()
        .withMessage("Max just only numeric value")
        .optional(),


        check("minPrice")
        .escape()
        .isFloat()
        .withMessage("Min price just only numeric value")
        .optional(),

        check("maxPrice")
        .escape()
        .isFloat()
        .withMessage("Max price just only numeric value")
        .optional(),

    ])
], getProducts)

productRouter.get("/:id?",[
    inputValidators([
        check("id")
        .escape()
        .isMongoId()
        .withMessage("Invalid product id")
    ])
] ,getProduct)



productRouter.post("/create", [
    multerService.array("image", 5),
    accessTokenValidate,
    adminValidator,

    inputValidators([
        check("name")
            .isLength({ min: 3, max: 32 })
            .withMessage(ERROR_CODES.PRODUCT_NAME_ERROR),

        check("description")
            .isLength({ min: 3, max: 32 })
            .withMessage(ERROR_CODES.PRODUCT_DESCRIPTION_ERROR),

        check("price")
            .isLength({ min: 1, max: 4 })
            .withMessage(ERROR_CODES.PRODUCT_PRICE_LENGTH_ERROR)
            .custom(input => {
                if (input === 0 || input === undefined || input === null)
                    throw new Error("Product price not be null or 0")
                return true
            }),

        check("discount")
            .isLength({ min: 1, max: 4 })
            .withMessage(ERROR_CODES.PRODUCT_PRICE_LENGTH_ERROR)
            .custom(input => {
                if (input === 0 || input === undefined || input === null)
                    throw new Error("Product price not be null or 0")
                return true
            })
            .optional(),

        check("categoryId")
            .isMongoId()
            .withMessage(ERROR_CODES.INVALID_MONGO_ID)
    ])
], createProduct)


productRouter.delete("/delete/:id?", [
    accessTokenValidate,
    adminValidator,

    inputValidators([check("id").isMongoId().withMessage(ERROR_CODES.INVALID_MONGO_ID)])

], deleteProduct)

productRouter.delete("/delete", [
    accessTokenValidate,
    adminValidator,

    inputValidators([check("*.id").isMongoId().withMessage(ERROR_CODES.INVALID_MONGO_ID)])

], deleteMany)

productRouter.put("/update", [
    accessTokenValidate,
    adminValidator,
    multerService.array("image", 5),

    inputValidators([
        check("id")
        .isMongoId()
        .withMessage(ERROR_CODES.INVALID_MONGO_ID),

    check("name")
        .optional()
        .isLength({ min: 3, max: 32 })
        .withMessage(ERROR_CODES.PRODUCT_NAME_ERROR),

    check("description")
        .optional()
        .isLength({ min: 3, max: 32 })
        .withMessage(ERROR_CODES.PRODUCT_DESCRIPTION_ERROR),


    check("price")
        .isLength({ min: 1, max: 4 })
        .withMessage(ERROR_CODES.PRODUCT_PRICE_LENGTH_ERROR)
        .isNumeric()
        .withMessage(ERROR_CODES.PRODUCT_PRICE_NUMERIC_ERROR)
        .custom(input => {
            if (input === 0 || input === undefined || input === null)
                throw new Error("Product price not be null or 0")
            return true
        })
        .optional(),

        check("discount")
        .isLength({min:1 , max : 4})
        .withMessage(ERROR_CODES.PRODUCT_PRICE_LENGTH_ERROR)
        .isNumeric()
        .withMessage(ERROR_CODES.PRODUCT_PRICE_NUMERIC_ERROR)
        .custom(input => {
            if(input === 0 || input === undefined || input === null)
                throw new Error("Discount price not be null or 0")
            return true
        })
        .optional()

    ])

    
], updateProduct)

productRouter.put("/updateMany", [
    accessTokenValidate,
        adminValidator,


        inputValidators([
            check("*.id")
            .isMongoId()
            .withMessage(ERROR_CODES.INVALID_MONGO_ID),
    
            check("*.name" , ERROR_CODES.PRODUCT_NAME_ERROR)
            .optional(),
    
            check("*.description" , ERROR_CODES.PRODUCT_DESCRIPTION_ERROR)
            .optional(),
    
            check("*.price" , ERROR_CODES.PRODUCT_PRICE_LENGTH_ERROR)
            .optional()
            .isNumeric()
            .withMessage(ERROR_CODES.PRODUCT_PRICE_NUMERIC_ERROR),
    
            check("*.discount" , ERROR_CODES.PRODUCT_PRICE_LENGTH_ERROR)
            .optional()
            .isNumeric()
            .withMessage(ERROR_CODES.PRODUCT_PRICE_NUMERIC_ERROR),
        ])
        
], updateMany)

export default productRouter