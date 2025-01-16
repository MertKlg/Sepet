import { API_URL } from "~/common/Api"
import type { IProfile } from "~/model/interface/IProfile"
import type { IResponse } from "~/model/interface/IResponse"


export const authStorage = defineStore("authStorage",() => {
    const isAuth = ref(false)
    
    async function signIn(values : string[] , callback : (response : IResponse) => void) {
        try{
            const { data, error  } = await useFetch((API_URL + "v1/auth/login"), {body : values, method : "POST", credentials : "include"})

            if(error.value != null) {
                callback(error.value.data as IResponse)
                return
            }

            callback(data.value as IResponse)

        }catch(e){
            callback({message : "Something went wrong", status: 500, data : []})
        }
    }

    async function refresh() {
        try{
            const res = await $fetch((API_URL + "v1/auth/refresh"), { credentials : "include", method : "post", headers : {} })
            const convResponse = res as IResponse

            if(convResponse.status !== 200){
                isAuth.value = false
                return
            }
    
            isAuth.value = true
        }catch(e){
            console.error(e)
        }
    }

    async function logOut(){
        try{
            const res = await $fetch((API_URL + "v1/auth/logOut"), { credentials : "include", method : "post" })
            const convResponse = res as IResponse

            if(convResponse.status !== 200){
                isAuth.value = false
                return
            }
    
            isAuth.value = true
        }catch(e){
            console.error(e)
        }
    }

    


    async function signUp() {

    }

    async function signOut() {
        
    }

    return {isAuth, signIn, signOut, signUp,refresh,logOut}
})