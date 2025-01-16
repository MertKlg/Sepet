import { Router } from "express";
import { accessTokenValidate } from "../middleware/Others/VerifyToken";
import { deleteProfile, getProfile, updateProfile } from "../controller/ProfileController";
import { check } from "express-validator";
import { inputValidators } from "../middleware/Others/InputValidatorMiddleware";

const profileRouter = Router()


profileRouter.get("/all", accessTokenValidate, getProfile)

profileRouter.put("/update", [accessTokenValidate , inputValidators([

    check("username")
    .optional()
    .escape()
    .notEmpty()
    .withMessage("Username not be empty"),

    check("email")
    .optional()
    .escape()
    .notEmpty()
    .withMessage("Email not be empty"),

    check("phone")
    .optional()
    .escape()
    .notEmpty()
    .withMessage("Phone not be empty")

  ])], updateProfile)

profileRouter.delete("/delete", accessTokenValidate, deleteProfile)

export default profileRouter