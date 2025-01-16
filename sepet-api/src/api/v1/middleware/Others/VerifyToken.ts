import { Request, Response, NextFunction } from "express";
import jwt from "jsonwebtoken"
import { SECRET_KEY } from "../../config/TokenConfig";
import userEntity from "../../model/entity/UserEntity";
import ResponseModel from "../../model/response/ResponseModel";

export async function accessTokenValidate(req: Request, res: Response, next: NextFunction) {
    try {

        var token = req.cookies["access-token"] || req.headers.authorization?.replace("Bearer ", "") as string

        const decodedToken = jwt.verify(token, SECRET_KEY)

        const decode = JSON.parse(JSON.stringify(decodedToken))

        res.locals.userId = decode["userId"]

        next()

    } catch (e) {
        return res.status(401).json(new ResponseModel("INVALID TOKEN",401))
    }
}



export async function refreshTokenValidate(req: Request, res: Response, next: NextFunction) {
    try {

        var token = req.cookies["refresh-token"] || req.headers.authorization?.replace("Bearer ", "") as string

        const decodedToken = jwt.verify(token, SECRET_KEY)

        const decode = JSON.parse(JSON.stringify(decodedToken))

        if (!decode["isRefreshKey"])
            throw new ResponseModel("Just only accept refresh token, please check your token", 400)

        res.locals.userId = decode["userId"]

        next()


    } catch (e) {
        next(e)
    }
}