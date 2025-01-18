import express from "express"
import productRouter from "./api/v1/router/ProductRouter"
import "../src/api/v1/service/DatabaseService"
import ErrorHandler from "./api/v1/middleware/Handler/ErrorHandler"
import authRouter from "./api/v1/router/AuthRouter"
import categoryRouter from "./api/v1/router/CategoryRouter"
import "./api/v1/extension/StringExtension"
import cartRouter from "./api/v1/router/CartRouter"
import cors from "cors"
import cookieParser from "cookie-parser"
import ordersRouter from "./api/v1/router/OrderRouter"
import addressRouter from "./api/v1/router/AddressRouter"
import profileRouter from "./api/v1/router/ProfileRouter"

const app = express()

/* Sepet api */
app.use(cookieParser())
app.use("/storage", express.static("storage"))
app.use(express.urlencoded({extended : true}))
app.use(express.json())

app.use(cors({
    origin : ["http://localhost:3000"],
    credentials : true
}))

app.use("/v1/product", productRouter)

app.use("/v1/category", categoryRouter)

app.use("/v1/auth", authRouter)

app.use("/v1/cart",cartRouter)

app.use("/v1/order", ordersRouter)

app.use("/v1/address",addressRouter)

app.use("/v1/profile", profileRouter)

app.use(ErrorHandler)
app.listen(3001)
