<template>
    <NuxtLayout>
        <section class="min-vh-100 mt-5">

            <ToastMessages :messages="status" @close-toast="removeToast"></ToastMessages>

            <div class="container">
                <div class="col h-100 " v-if="!auth.isAuth">
                    <h1>Please go sign in</h1>
                </div>


                <div class="row" v-else>
                    <h3 class="text-center mb-5">Welcome back <span class="fw-bold">{{ profile.userProfile.username
                            }}</span></h3>

                    <div class="col d-flex align-items-center justify-content-center">
                        <img :src="profile.userProfile?.image" decoding="async" v-if="profile.userProfile.image" />
                        <span style="border-color: var(--tertitary-color) !important;"
                            class="material-symbols-outlined fs-1 border p-5 rounded-circle" v-else>
                            person </span>
                    </div>


                    <div class="col">
                        <form @submit.prevent="updateProfile">
                            <div class="mb-3">
                                <label for="username-input" class="form-label">Username</label>
                                <input type="text" v-model="profile.userProfile.username" class="form-control"
                                    id="username-input">
                            </div>

                            <div class="mb-3">
                                <label for="email-input" class="form-label">Email</label>
                                <input type="email" v-model="profile.userProfile.email" class="form-control"
                                    id="email-input">
                            </div>

                            <div class="mb-3">
                                <label for="phone-input" class="form-label">Phone</label>
                                <input type="tel" min="0" max="11" v-model="profile.userProfile.phone"
                                    class="form-control" id="phone-input">
                            </div>

                            <input type="submit" class="btn w-100 text-white" style="background: var(--primary-color);"
                                value="Update profile">
                        </form>

                        <button class="btn bg-danger text-white w-100 mt-2" @click="deleteProfile">Delete profile</button>
                    </div>
                </div>
            </div>


        </section>
    </NuxtLayout>
</template>

<script lang="ts" setup>
import { API_URL } from '~/common/Api';
import type { IResponse } from '~/model/interface/IResponse';
import { authStorage } from '~/storage/AuthStorage';
import { profileStore } from '~/storage/ProfileStorage';
import { ref } from "vue"
import type { IProduct } from '~/model/interface/IProduct';

const auth = authStorage()

const profile = profileStore()
const status = ref<IResponse[]>([])

const updateProfile = async () => {

    const { data, error } = await useAsyncData("updateProfile", () => $fetch((API_URL + "v1/profile/update"), {
        credentials: "include", method: "put",
        body: profile.userProfile
    }))

    if (error.value) {
        status.value.push(error.value.data as IResponse)
        return
    }
    status.value.push(data.value as IResponse)
}


const deleteProfile  = async () => {
    const { data, error } = await useAsyncData("updateProfile", () => $fetch((API_URL + "v1/profile/delete"),  {credentials : "include" , method : "delete"}))

    if (error.value) {
        status.value.push(error.value.data as IResponse)
        return
    }
    status.value.push(data.value as IResponse)
}


const removeToast = (product: IProduct) => {
    status.value.splice(product, 1)
}

</script>