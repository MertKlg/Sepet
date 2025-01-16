<template>
    <NuxtLayout>
        <section class="container mt-3 vh-100">

            <div class="h-100" v-if="react.status.status === 1">
                <h4>{{ react.status.message }}</h4>
                <RouterLink to="/address" class="btn text-white" style="background-color: var(--tertitary-color);">Go back</RouterLink>
            </div>

            <h3>Update address</h3>
            <div>
                <form @submit.prevent="updateAddress">
                    <input type="text"  v-model="react.address._id" hidden/>

                    <div class="mb-3">
                        <label for="addressNameInput" class="form-label">Address name</label>
                        <input type="text" class="form-control" v-model="react.address.name" id="addressNameInput">
                    </div>

                    <div class="row">
                        <div class="col">
                            <div class="mb-3">
                                <label for="townInput" class="form-label">Town</label>
                                <input type="text" class="form-control" v-model="react.address.town" id="townInput">
                            </div>
                        </div>

                        <div class="col">
                            <div class="mb-3">
                                <label for="neighbourhoodInput" class="form-label">Neighbourhood</label>
                                <input type="text" class="form-control" v-model="react.address.neighbourhood"
                                    id="neighbourhoodInput">
                            </div>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="fullAddressInput" class="form-label">Full address</label>
                        <textarea type="text" class="form-control" id="fullAddressInput"
                            v-model="react.address.fullAddressText"></textarea>
                    </div>

                    <input type="submit" class="btn text-white " style="background-color: var(--tertitary-color);"
                        value="Update address">
                </form>

                <div v-if="react.status" class="mt-5">
                    <h4>{{ react.status.message }}</h4>
                </div>
            </div>
        </section>
    </NuxtLayout>
</template>

<script lang="ts" setup>
import { API_URL } from '~/common/Api';
import type { IAddress } from '~/model/interface/IAddress';
import type { IResponse } from '~/model/interface/IResponse';

import { reactive, onMounted } from 'vue';
import { useRoute } from '#app';

const { id } = useRoute().params

const react = reactive({
    address: {} as IAddress,
    status: {} as IResponse
})


onMounted(async () => {
    await findAddress(String(id))
})

const findAddress = async (addressId: string | null) => {
    try {

        if(!addressId){
            react.status = {message : "No addres found", data : [], status : 1 } as IResponse
            return
        }
        const res = await $fetch((API_URL + `v1/address/${id}`), { credentials: "include", method: "get" })

        if (!res) {
            react.status = res as IResponse
            return
        }

        const response = res as IResponse
        react.address = response.data[0] as IAddress

    } catch (e) {
        react.status = { message: "Something went wrong", status : 1, data : [] } as IResponse
    }
}

const updateAddress = async () => {
    try {
        
        const json  = JSON.parse(JSON.stringify(react.address))
        
        const res = await $fetch((API_URL + `v1/address/update`), { credentials: "include", method: "put", body : json })

        react.status = res as IResponse
    } catch (e) {
        react.status = { message: "Something went wrong", status : 1, data : [] } as IResponse
    }
}

</script>

<style scoped></style>