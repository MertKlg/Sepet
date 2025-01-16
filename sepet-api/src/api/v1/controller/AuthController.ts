import { Request, Response, NextFunction } from "express";
import ResponseModel from "../model/response/ResponseModel";
import userEntity from "../model/entity/UserEntity";
import IAuth from "../model/interface/IAuth";
import bcrypt from "bcrypt";
import Role from "../model/enum/Role";
import jwt from "jsonwebtoken";
import {
  EXPIRE_DATE,
  REFRESH_EXPIRE_DATE,
  SECRET_KEY,
} from "../config/TokenConfig";
import { StatusCode } from "../model/enum/StatusEnum";
import ERROR_CODES from "../model/enum/ResponseCodes";


export async function register(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const user = req.body as IAuth;

    const findUser = await userEntity.findOne({ email: user.email });

    if (findUser !== null)
      throw new ResponseModel("This user already exists", StatusCode.NOT_FOUND);

    await userEntity.create({
      username: user.username,
      password: await bcrypt.hash(user.password, 10),
      email: user.email,
      address: user.address,
      phone: user.phone,
      image: "",
      role: Role.USER,
    });
    res.json(new ResponseModel("Successfully sign up", StatusCode.CREATED));
  } catch (e) {
    next(e);
  }
}

export async function login(req: Request, res: Response, next: NextFunction) {
  try {
    const { mobileOrTablet, browser } = res.locals["deviceTypes"];

    const user = req.body as IAuth;

    const findUser = await userEntity.findOne({ email: user.email });

    if (
      findUser == null ||
      !(await bcrypt.compare(user.password, findUser.password))
    )
      throw new ResponseModel(
        ERROR_CODES.EMAIL_OR_PASSWORD_WRONG,
        StatusCode.NOT_FOUND
      );

    const accessToken = jwt.sign({ userId: findUser._id }, SECRET_KEY, {
      expiresIn: EXPIRE_DATE,
    });

    const refreshToken = jwt.sign(
      { userId: findUser._id, isRefreshKey: true },
      SECRET_KEY,
      { expiresIn: REFRESH_EXPIRE_DATE }
    );

    if (mobileOrTablet && browser) {
      // mobile browser
      res.cookie("access-token", accessToken, {
        path: "/",
        httpOnly: true,
        secure: true,
        expires: new Date(Date.now() + parseInt(EXPIRE_DATE)),
      });

      res.cookie("refresh-token", refreshToken, {
        path: "/",
        httpOnly: true,
        secure: true,
        expires: new Date(Date.now() + parseInt(REFRESH_EXPIRE_DATE)),
      });
    } else if (!mobileOrTablet && browser) {
      // web application

      res.cookie("access-token", accessToken, {
        path: "/",
        httpOnly: true,
        secure: true,
        expires: new Date(Date.now() + parseInt(EXPIRE_DATE)),
      });

      res.cookie("refresh-token", refreshToken, {
        path: "/",
        httpOnly: true,
        secure: true,
        expires: new Date(Date.now() + parseInt(REFRESH_EXPIRE_DATE)),
      });
    } else {
      // others
      return res.json(
        new ResponseModel(ERROR_CODES.SUCCESS, 200, [
          {
            accessToken,
            refreshToken,
          }
        ])
      );
    }

    res.json(new ResponseModel(ERROR_CODES.SUCCESS, StatusCode.SUCCESS));
  } catch (e) {
    next(e);
  }
}

export async function updatePassword(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const password = req.body as IAuth;
    const userID = res.locals.userId;

    await userEntity
      .findById(userID)
      .updateOne({ password: await bcrypt.hash(password.password, 10) });

    res.json(new ResponseModel("Password successfully updated", 200));
  } catch (e) {
    next(e);
  }
}

export async function refresh(req: Request, res: Response, next: NextFunction) {
  try {
    const { mobileOrTablet, browser } = res.locals["deviceTypes"];
    const userId = res.locals.userId
    const accessToken = jwt.sign({ userId: userId }, SECRET_KEY, {
      expiresIn: EXPIRE_DATE,
    });



    if (mobileOrTablet && browser) {
      // mobile browser
      res.cookie("access-token", accessToken, {
        path: "/",
        httpOnly: true,
        secure: true,
        expires: new Date(Date.now() + parseInt(EXPIRE_DATE)),
      });

    } else if (!mobileOrTablet && browser) {
      // web application

      res.cookie("access-token", accessToken, {
        path: "/",
        httpOnly: true,
        secure: true,
        expires: new Date(Date.now() + parseInt(EXPIRE_DATE)),
      });

    }else{
      return res.json(new ResponseModel("Success", StatusCode.SUCCESS, [{"accessToken" : accessToken }]))
    }

    res.json(new ResponseModel("Success", 200));
  } catch (e) {
    next(e);
  }
}


export const logOut =  async(req: Request, res: Response, next: NextFunction) => {
  try{

    const { mobileOrTablet, browser } = res.locals["deviceTypes"];


    if (mobileOrTablet && browser) {
      // mobile browser
      res.cookie("access-token", "", {
        path: "/",
        httpOnly: true,
        secure: true,
        expires: new Date(Date.now() - 1),
      });

      res.cookie("refresh-token", "", {
        path: "/",
        httpOnly: true,
        secure: true,
        expires: new Date(Date.now() - 1),
      });

    } else if (!mobileOrTablet && browser) {
      // web application
      res.cookie("access-token", "", {
        path: "/",
        httpOnly: true,
        secure: true,
        expires: new Date(Date.now() - 1),
      });

      res.cookie("refresh-token", "", {
        path: "/",
        httpOnly: true,
        secure: true,
        expires: new Date(Date.now() - 1),
      });
    }

    res.json(new ResponseModel("LogOut", 200))
  }catch(e){
    next(e)
  }
}

export const refreshPassword = async(req: Request, res: Response, next: NextFunction) => {
  try{
    const userId = res.locals.userId
    const {password, newPassword} = req.body

    const findUser = await userEntity.findById(userId)

    if(!findUser){
      throw new ResponseModel("User not founded", 400)
    }


    const comparePass = await bcrypt.compare(password, findUser.password)
    if(!comparePass){
      throw new ResponseModel("This password is not your current password", 404)
    }


    await findUser.updateOne({password : await bcrypt.hash(newPassword, 10)})
    await findUser.save()

    res.json(new ResponseModel("Password updated", 200))
  }catch(e){
    next(e)
  }
}