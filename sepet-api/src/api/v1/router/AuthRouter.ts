import { Router } from "express";
import {
  login,
  logOut,
  refresh,
  refreshPassword,
  register,
} from "../controller/AuthController";

import { inputValidators } from "../middleware/Others/InputValidatorMiddleware";

import {
  accessTokenValidate,
  refreshTokenValidate,
} from "../middleware/Others/VerifyToken";

import { check } from "express-validator";

import ERROR_CODES from "../model/enum/ResponseCodes";
import userAgentMiddleware from "../middleware/Others/UserAgentMiddleware";

const authRouter = Router();

authRouter.post(
  "/register",
  inputValidators([
    check("username")
    .escape()
      .isLength({ min: 3, max: 12 })
      .withMessage("Your name must be min 3, max 12 characters"),

    check("email").escape().isEmail().withMessage("Send a validate email address"),

    check("phone")
    .escape()
      .isMobilePhone("tr-TR")
      .withMessage("Send a validate phone number"),

    check("password")
    .escape()
      .isStrongPassword()
      .withMessage(
        "Password must have one uppercase,lowercase,special characters and min 8 length"
      ),

    check("passwordAgain")
    .escape()
      .isStrongPassword()
      .withMessage(
        "Password must have one uppercase,lowercase,special characters and min 8 length"
      )
      .custom(async (confirmPassword, { req }) => {
        const passwordAgain = confirmPassword as string;
        const password = req.body.password as string;
        if (passwordAgain !== password)
          throw new Error("Passwords must be same");
      }),
  ]),
  register
);

authRouter.post(
  "/login",
  [
    inputValidators([
      check("email").escape().isEmail().withMessage(ERROR_CODES.INVALID_EMAIL),

      check("password")
      .escape()
        .isStrongPassword()
        .withMessage(ERROR_CODES.WEAK_PASSWORD)
        .isLength({ min: 8 })
        .withMessage(ERROR_CODES.PASSWORD_MIN),
    ]),
    userAgentMiddleware,
  ],
  login
);



authRouter.post(
  "/refresh",
  [userAgentMiddleware, refreshTokenValidate],
  refresh
);

authRouter.put("/re-password",[accessTokenValidate, inputValidators([
  check("password")
    .escape()
      .isStrongPassword()
      .withMessage(
        "Password must have one uppercase,lowercase,special characters and min 8 length"
      ),

  check("newPassword")
  .escape()
  .isStrongPassword()
  .withMessage(ERROR_CODES.WEAK_PASSWORD)
  .isLength({ min: 8 })
  .withMessage(ERROR_CODES.PASSWORD_MIN)
])], refreshPassword)

authRouter.post("/logOut",[accessTokenValidate,userAgentMiddleware], logOut)

export default authRouter;
