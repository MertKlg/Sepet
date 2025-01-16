import type { ICategory } from "./ICategory";

export interface IProduct{
    _id : string,
    category : ICategory,
    name : string,
    description : string,
    price : number,
    image : string[],
    discountPrice : number
}