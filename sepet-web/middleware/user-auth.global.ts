import { profileStore } from "~/storage/ProfileStorage"
import { authStorage } from "~/storage/AuthStorage"


export default defineNuxtRouteMiddleware(async (to) => {
    const profile = profileStore()
    const auth = authStorage()
    
    if (import.meta.client) {
        await profile.profile()
        
        if(auth.isAuth){
            if(to.path == "/sign-in" || to.path == "/sign-up"){
                return await navigateTo("/")
            } 
        }
    }
})