import { Router } from "express";
import { accessTokenValidate } from "../middleware/Others/VerifyToken";
import { order, orderDetail, orders, updateOrder } from "../controller/OrderController";
import { inputValidators } from "../middleware/Others/InputValidatorMiddleware";
import { check } from "express-validator";
const ordersRouter = Router()



ordersRouter.get("/detail/:id?", [accessTokenValidate, inputValidators([
    check("id")
        .isMongoId()
        .withMessage("Invalid order")
        .escape()
])], orderDetail)

ordersRouter.get("/all", accessTokenValidate, orders)

ordersRouter.put("/update/:order?", [accessTokenValidate, inputValidators([

    check("order")
        .escape()
        .isMongoId()
        .withMessage("Invalid order id"),

    check("orderStatus")
        .isString()
        .escape()
        .optional(),

    check("address")
        .escape()
        .isMongoId()
        .withMessage("Invalid address")
        .optional()

])], updateOrder)


ordersRouter.post("/:cart?", [accessTokenValidate, inputValidators([

    check("cart")
        .escape()
        .isMongoId()
        .withMessage("Invalid cart"),


    check("address")
        .escape()
        .isMongoId()
        .withMessage("Invalid address"),

])], order)







export default ordersRouter