import { NextFunction, Request, Response } from "express";
import parser from "ua-parser-js";

const ua = new parser()

const userAgentMiddleware = (req : Request, res : Response, next : NextFunction) => {
    try{
        
        const result = ua.setUA(req.headers["user-agent"] ?? "").getResult()
        
        
        const deviceType = result.device?.type ?? "unknown";
        const browserName = result.browser?.name ?? "unknown";


        res.locals.deviceTypes = {
            mobileOrTablet: deviceType === "mobile" || deviceType === "tablet",
            browser: browserName !== "undefined" && browserName !== "unknown"
        }


        next()
    }catch(e){
        console.error(e)
        next(e)
    }
}

export default userAgentMiddleware