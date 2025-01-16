import { Request, Response,NextFunction } from "express";
import ResponseModel from "../../model/response/ResponseModel";
import { TokenExpiredError } from "jsonwebtoken";
import ERROR_CODES from "../../model/enum/ResponseCodes";

export default function(err : Error, req : Request, res : Response, next : NextFunction){
    if(err instanceof ResponseModel){
        const dto = {
            "message" : err.message,
            "status": err.status,
            "data" : err.data
        }

        return res.status(err.status).json(err)
    }else if(err instanceof TokenExpiredError){

        const dto = {
            "code" : ERROR_CODES.TOKEN_EXPIRED,
            "message": "The token expired",
            "data" : []
        }
        return res.status(401).json(dto)
    }else{
        const dto = {
            "code" : ERROR_CODES.SOMETHING_WENT_WRONG,
            "message": err.message ?? "Something went wrong",
            "data" : []
        }
        return res.status(500).json(dto)
    }
}