import { ObjectId } from "mongoose"

export default interface IDeliveryAddress{
    _id : ObjectId,
    name : string,
    town : string,
    neighbourhood : string,
    fullAddressText : string
}