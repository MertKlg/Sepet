import { API_URL } from "~/common/Api"
import type { IProfile } from "~/model/interface/IProfile"
import { authStorage } from "./AuthStorage"
import type { IResponse } from "~/model/interface/IResponse"
import type { IAddress } from "~/model/interface/IAddress"


export const profileStore = defineStore("profileStore", () => {

    const auth = authStorage()

    const userProfile = ref<IProfile>({
        username : "",
        email : "",
        image : "",
        phone : ""
    })

    

    async function profile() {
        try{
            const {data, error} = await useAsyncData("profile", () => $fetch((API_URL + "v1/profile"),{ credentials : "include", method : "get" }) )
            
            if(error.value){
                auth.isAuth = false
                if(error.value.statusCode === 401){
                    await auth.refresh()
                }
                return
            }


            auth.isAuth = true
            const response = data.value as IResponse
            userProfile.value = response.data[0] as IProfile
        }catch(e){
            console.error("profile ",e)
        }
    }


    return {userProfile, profile}
})