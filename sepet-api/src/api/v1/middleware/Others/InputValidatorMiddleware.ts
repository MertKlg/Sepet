import { ContextRunner } from "express-validator";
import ResponseModel from "../../model/response/ResponseModel";
import { Request, Response, NextFunction } from "express";

export const inputValidators = (validations : ContextRunner[]) => {
    return async(req : Request, res : Response, next : NextFunction) => {
        try{
            for(const valid of validations){
                const results = await valid.run(req)
                if(!results.isEmpty()){
                    throw new ResponseModel(results.array()[0].msg, 400)
                }
            }
    
            res.locals["email"] = req.body["email"]
            next()
        }catch(e){
            next(e)
        }
    }
}