import type { IProduct } from "./IProduct"

export interface ICart{
    _id : string,
    products : ICartOfProduct[],
    total : number
}

export interface ICartOfProduct{
    product : IProduct,
    count : number
}