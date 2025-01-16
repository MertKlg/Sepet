import type { IAddress } from "./IAddress";
import type { ICartOfProduct } from "./ICart";
import type { IProfile } from "./IProfile";

export interface IOrder{
    _id : string,
    products: ICartOfProduct[],
    total : number,
    address : IAddress,
    status : string,
    user : IProfile,
    orderDate : Date
}