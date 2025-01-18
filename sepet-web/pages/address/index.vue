<template>
    <NuxtLayout>
        <section class="min-vh-100 container mt-3">

            <div class="col h-100 " v-if="!auth.isAuth">
                <h1>Please go sign in</h1>
            </div>

            <div class="col">
                <div class="mb-3">
                    <h3 class="mb-3">Delivery address</h3>
                    <RouterLink to="/address/add" class="btn text-white"
                        style="background-color: var(--tertitary-color);">Add address</RouterLink>
                </div>

                <div class="col" v-if="addressDatas.address.length <= 0">
                    <h6>No address found</h6>
                </div>

                <div v-else class="col">
                    <div class="d-grid" style="gap: 10px; grid-template-columns: repeat(4, auto) !important;">
                        <div class="card" v-for="item in addressDatas.address" :key="item._id">
                            <div class="card-body">
                                <h5 class="card-title"><span class="fw-bold">{{ item.name }}</span></h5>
                                <div class="card-text">
                                    <p>
                                        {{ item.town }}
                                    </p>

                                    <p>
                                        {{ item.neighbourhood }}
                                    </p>

                                    <p>
                                        {{ item.fullAddressText }}
                                    </p>
                                </div>

                                <button class="btn text-white bg-danger mx-1"
                                    @click="deleteAddress(item._id)">Delete</button>

                                <RouterLink :to="`/address/update/${item._id}`" class="btn text-white mx-1 bg-warning">
                                    Update</RouterLink>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

        </section>
    </NuxtLayout>
</template>

<script lang="ts" setup>
import { API_URL } from '~/common/Api';
import { onBeforeMount, onMounted, reactive } from 'vue';
import { authStorage } from '~/storage/AuthStorage';
import type { IAddress } from '~/model/interface/IAddress';
import type { IResponse } from '~/model/interface/IResponse';

const auth = authStorage()

const addressDatas = reactive({
    address: [] as IAddress[],
    status: {} as IResponse
})



onMounted(async () => await getAddress())


const getAddress = async () => {
    try {
        const res = await $fetch((API_URL + "v1/address/all"), { credentials: "include" })

        const response = res as IResponse

        if (response.data) {
            addressDatas.address = response.data as IAddress[]
        }
    } catch (e) {
        console.error(e)
    }

}

const deleteAddress = async (addressId: string) => {
    try {
        const res = await $fetch((API_URL + `v1/address/${addressId}`), { credentials: "include", method: "DELETE", headers: { 'Content-Type': "application/json" } })

        addressDatas.status = res as IResponse

        await getAddress()
    } catch (e) {
        console.error(e)
    }
}

</script>
