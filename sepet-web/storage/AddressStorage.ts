import { API_URL } from "~/common/Api"
import type { IAddress } from "~/model/interface/IAddress"
import type { IResponse } from "~/model/interface/IResponse"

const addressStorage = defineStore("addressStorage", () => {

    const address = ref<IAddress[]>([])

    async function getAddresses(callback : (iresponse : IResponse) => void) {
        try{
            const {data, error} = await useAsyncData("profile", () => $fetch((API_URL + "v1/address/all"),{ credentials : "include", method : "get" }) )
            if(error.value){
                callback(error.value.data as IResponse)
                return
            }

            const response = data.value as IResponse

            if(response.data){
                address.value = response.data as IAddress[]
            }
            
        }catch(e){
            callback({message : "Something went wrong", status : 500} as IResponse)
        }
    }

    async function updateAddress(address : IAddress | null,callback : (iresponse : IResponse) => void) {
        try{
            if(address) {
                callback({message : "Please send validated address", status : 400} as IResponse)
                return
            }

            const body = JSON.stringify(JSON.parse(String(address)))

            const {data,error} = await useAsyncData("updateAddress", () => $fetch((API_URL + 'v1/address/add'), { credentials: "include", method: "POST", body: body }) )

            if(error.value){
                callback(error.value.data as IResponse)
                return
            }

            callback(data.value as IResponse)

            await getAddresses(callback)

        }catch(e){
            callback({message : "Something went wrong", status : 500}  as IResponse)
        }
    }


    

    return {address,getAddresses}
})