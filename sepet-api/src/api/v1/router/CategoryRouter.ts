import { Router } from "express";
import { createCategory, deleteCategory, getCategories, updateCategory } from "../controller/CategoryController";
import { inputValidators } from "../middleware/Others/InputValidatorMiddleware";
import { check } from "express-validator";
import { accessTokenValidate } from "../middleware/Others/VerifyToken";
import multerService from "../service/MulterService";
const categoryRouter = Router()

categoryRouter.get("/:id?", [
    inputValidators([
        check("id")
        .escape()
        .isMongoId()
        .withMessage("Invalid category id")
        .optional()
    ])
] ,getCategories)




/*
categoryRouter.post("/", [
    accessTokenValidate,
    adminValidator,
    multerService.single("image"),
    check("name")
    .isEmpty()
    .withMessage("PRODUCT_NAME_REQUIRED")
], createCategory)

categoryRouter.delete("/", [
    accessTokenValidate,
    adminValidator,
    check("id")
    .isMongoId()
    .withMessage("INVALID_PRODUCT_ID")
], deleteCategory)

categoryRouter.put("/", [
    accessTokenValidate,
    adminValidator,
    multerService.single("image"),
    check("id")
    .isMongoId()
    .withMessage("INVALID_PRODUCT_ID"),

    check("name")
    .optional()

], updateCategory)*/
export default categoryRouter