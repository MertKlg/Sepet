<template>
    <NuxtLayout>
        <section class="min-vh-100 mt-4">
            <ToastMessages :messages="status" @close-toast="closeToast" />
            <div class="container">

                <div v-if="orderDetail == null || undefined">
                    <h1>Sorry we did not found order</h1>
                </div>

                <div class="row" v-else>
                    <div class="col">
                        <div class="d-flex align-items-center py-2 px-5"
                            :class="(index == 0) ? 'rounded-top border' : (index == (orderDetail.products.length - 1)) ? 'border rounded-bottom' : 'border-end border-bottom border-start'"
                            v-for="(product, index) in orderDetail.products" :key="product.product._id">
                            <img :src=" API_URL +'storage/' + product.product.image" decoding="async"
                                style="height: 100px; width:100px; object-fit: contain;" />

                            <div class="text-center w-100">
                                <h6 class="fw-bold">{{ product.product.name }}</h6>
                                <div>{{ product.count }} x {{ product.product.price }}$</div>
                            </div>
                        </div>
                    </div>

                    <div class="col">
                        <h4>Details</h4>
                        
                        <div class="text-wrap">

                            <h6>Address:</h6>
                            <span>{{ orderDetail?.address.neighbourhood }} {{ orderDetail?.address.fullAddressText }} {{
                                orderDetail?.address.town }}</span>

                            <h6>Status: <span class="fw-bold" style="color: var(--primary-color)">{{ orderDetail?.status
                                    }}</span></h6>

                            <h6>Total : {{ orderDetail.total }}$</h6>
                        </div>

                        <div class="mt-3" v-if="orderDetail?.status == 'ORDER_ORDERED'">

                            <h6>Change address</h6>
                            <span>If your order status 'ordered' you can change address</span>
                            <div v-for="userAddress in address" :key="userAddress._id">
                                <div class="border rounded p-2 my-1" style="cursor: pointer;"
                                    @click="takeAddress.address = userAddress._id"
                                    :class="(takeAddress.address == userAddress._id) ? 'border-success' : ''">
                                    <h5>{{ userAddress.name }}</h5>
                                    <span>{{ userAddress.neighbourhood }} {{ userAddress.fullAddressText }} {{
                                        userAddress.town }}</span>
                                </div>
                            </div>

                            <button class="btn w-100 text-white mt-1" style="background-color: var(--tertitary-color);"
                                @click="updateOrder(orderDetail._id, { address: takeAddress.address })">Update
                                Order</button>
                            <button class="btn bg-danger text-white mt-1 w-100"
                                @click="updateOrder(orderDetail._id, { orderStatus: 'ORDER_CANCELED' })">Cancel order</button>

                        </div>
                    </div>
                </div>
            </div>
        </section>
    </NuxtLayout>
</template>

<script setup lang="ts">
import { API_URL } from '~/common/Api';
import type { IAddress } from '~/model/interface/IAddress';
import type { IOrder } from '~/model/interface/IOrder';
import { IResponse } from '~/model/interface/IResponse';

const status = ref<IResponse[]>([])

const params = useRoute()
const orderDetail = ref<IOrder>()

const address = ref<IAddress[]>([])

const takeAddress = reactive({ address: "" })

onMounted(async () => {
    await nextTick()
    await getOrder(params.params.id)
    await getAddress()
})

const closeToast = (response: IResponse) => {
    status.value.splice(response, 1)
}


const getOrder = async (orderId: string | null) => {
    if (!orderId) return

    const url = (API_URL + `v1/order/detail/${orderId}`)

    const { data, error } = await useAsyncData("orderDetail", () => $fetch(url, { credentials: "include", method: "get" }))

    if (error.value) {
        status.value.push(error.value.data as IResponse)
        return
    }

    const response = data.value as IResponse
    if (response.data) {
        orderDetail.value = response.data[0] as IOrder
    }
}

const getAddress = async () => {
    const url = (API_URL + `v1/address/all`)
    const { data, error } = await useAsyncData("orderDetail", () => $fetch(url, { credentials: "include", method: "get" }))

    if (error.value) {
        status.value.push(error.value.data as IResponse)
        return
    }

    const response = data.value as IResponse
    if (response.data) {
        address.value = response.data as IAddress[]
    }
}

const updateOrder = async (order: string | null, body: Object | null) => {
    if (!order || !body) return

    const api = (API_URL + `v1/order/update/${order}`)
    const jsonBody = JSON.stringify(body)

    const {data,error} = await useAsyncData("updateOrder", () => $fetch(api, {credentials : "include", method : "put",headers : {"Content-Type" : "application/json"} ,body : jsonBody}))

    if (error.value) {
        
        status.value.push(error.value.data as IResponse)
        return
    }

    status.value.push(data.value as IResponse)
}

</script>

<style></style>