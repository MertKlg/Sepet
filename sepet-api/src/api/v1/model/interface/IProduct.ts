export default interface IProduct{
    _id : string,
    name : string,
    description : string,
    price : number,
    discount? : number,
    selectedImages : string[],
    categoryId : string,
}