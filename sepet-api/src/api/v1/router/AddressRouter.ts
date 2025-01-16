import { Router } from "express"
import { check } from "express-validator"
import { inputValidators } from "../middleware/Others/InputValidatorMiddleware"
import { getAddress, addAddress, deleteAddress, updateAddress } from "../controller/AddressController"
import { accessTokenValidate } from "../middleware/Others/VerifyToken"


const addressRouter = Router()


addressRouter.get("/all/:id?", [
    accessTokenValidate,
    inputValidators([
        check("id")
            .optional()
            .escape()
            .isMongoId()
            .withMessage("Mongo id not validated")
    ])
], getAddress)



addressRouter.post("/add", [
    accessTokenValidate,

    inputValidators([

        check("name")
            .escape()
            .notEmpty()
            .withMessage("ADDRESS_NAME_REQUIRED"),

        check("town")
            .notEmpty()
            .withMessage("TOWN REQUIRED")
            .escape(),

        check("neighbourhood")
            .notEmpty()
            .withMessage("neighbourhood REQUIRED")
            .escape(),

        check("fullAddressText")
            .notEmpty()
            .withMessage("fullAddressText REQUIRED")
            .escape(),
    ])

], addAddress)


addressRouter.put("/update", [
    accessTokenValidate,

    inputValidators([
        check("_id")
            .isMongoId()
            .withMessage("INVALID_ADDRESS_ID"),

        check("name")
            .escape()
            .notEmpty()
            .withMessage("ADDRESS_NAME_REQUIRED"),

        check("town")
            .notEmpty()
            .withMessage("TOWN REQUIRED")
            .escape(),

        check("neighbourhood")
            .notEmpty()
            .withMessage("neighbourhood REQUIRED")
            .escape(),

        check("fullAddressText")
            .notEmpty()
            .withMessage("fullAddressText REQUIRED")
            .escape(),
    ])
], updateAddress)

addressRouter.delete("/:id?", [
    accessTokenValidate,
    inputValidators([
        check("id")
            .escape()
            .isMongoId()
            .withMessage("Address id required")
    ])
], deleteAddress)

export default addressRouter