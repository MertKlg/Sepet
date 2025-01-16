<template>
    <NuxtLayout>
        <section class="my-5 min-vh-100">
            <ToastMessages :messages="status" @close-toast="closeToast" />
            <div class="container">
                <h1>Orders</h1>

                <table class="table table-hover text-center">
                    <thead>
                        <tr>
                            <th>Products</th>
                            <th>User</th>
                            <th>Status</th>
                            <th>Order date</th>
                            <th>Total</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody v-for="order in orders" :key="order._id">
                        <tr>
                            <td class="d-flex">
                                <div v-for="(product, index) in order.products"  :key="product.count">
                                    <img :src=" API_URL + 'storage/' + product.product.image" decoding="async" style="width: 40px; height: 40px; object-fit: contain;" v-if="index < 3"/>
                                </div>
                            </td>

                            <td>
                                {{ order.user.username }}
                            </td>

                            <td>
                                {{ order.status }}
                            </td>

                            <td>
                                {{ order.orderDate }}
                            </td>

                            <td>
                                {{ order.total }}$
                            </td>
                            <td>
                                <RouterLink :to="'/orders/detail/' + order._id" class="btn text-white" style="background-color: var(--tertitary-color);">Details</RouterLink>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </section>
    </NuxtLayout>
</template>

<script setup lang="ts">
import { API_URL } from '~/common/Api';
import type { IOrder } from '~/model/interface/IOrder';
import type { IProduct } from '~/model/interface/IProduct';
import { IResponse } from '~/model/interface/IResponse';
import { onMounted, nextTick } from 'vue';

const status = ref<IResponse[]>([])
const orders = ref<IOrder[]>([])

const closeToast = (product: IProduct) => {
    status.value.splice(product, 1)
}


onMounted(async () => {
    await nextTick()
    await getOrders()
})

const getOrders = async () => {
    const { data, error } = await useAsyncData("getOrders", () => $fetch((API_URL + "v1/order/all"), { credentials: "include", method: "get" }))

    if (error.value) {
        status.value.push(error.value.data as IResponse)
        return
    }
    const response = data.value as IResponse

    if (response.data) {
        orders.value = response.data as IOrder[]
    }
}



</script>

<style></style>