
export default class ResponseModel{
    constructor(
        public readonly message : string,
        public readonly status : number,
        public readonly data : object[] = []
    ) {}
}