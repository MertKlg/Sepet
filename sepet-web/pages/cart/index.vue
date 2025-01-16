<template>
    <NuxtLayout>
        <section class="mt-4 min-vh-100">

            <ToastMessages :messages="status" @close-toast="removeStatus" />

            <div class="container position-relative z-1">
                <div v-if="!cart">
                    <h1>You has no cart or cannot login </h1>
                </div>

                <div class="row" v-else>
                    <h1>Your cart</h1>

                    <div class="col">
                        <div v-if="cart.products.length <= 0">
                            <h6>No cart founded</h6>
                        </div>

                        <div class="row align-items-center border rounded my-1 py-5" v-for="item in cart?.products"
                            v-else :key="item.product._id">

                            <div class="col d-flex justify-content-center">
                                <img :src="'http://localhost:8080/storage/' + item.product.image" decoding="async"
                                    class="img-fluid" style="width: 100px;" />
                            </div>
                            <div class="col">
                                <div class="d-flex w-100 justify-content-between">
                                    <h4 class="fw-bold">{{ item.product.name }}</h4>
                                    <button @click="updateCount(item,0)" class="btn rounded p-0" style="background-color: var(--tertitary-color);">
                                        <span class="material-symbols-outlined text-white p-1">
                                            delete
                                        </span>
                                    </button>
                                </div>
                                <span>{{ item.product.description }}</span>
                                <div>
                                    <button @click="updateCount(item, (item.count - 1))"
                                        class="text-white rounded-start border-0"
                                        style="background-color: var(--tertitary-color);">-</button>

                                    <input class="text-center border-0 border-top border-bottom" type="text"
                                        pattern="[0-9]*" aria-label="Ürün adedi" :value="item.count"
                                        style="width: 40px;">

                                    <button @click="updateCount(item, (item.count + 1))"
                                        class="text-white rounded-end border-0"
                                        style="background-color: var(--tertitary-color);">+</button>

                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col" v-if="cart.products.length > 0">
                        <h1 class="mb-3">Take order</h1>
                        <div class="fw-bold mb-3" style="color: var(--tertitary-color);">Total : {{ cart?.total }}$
                        </div>

                        <h5 class="mb-3">Address</h5>
                        
                        <div class="mb-3" v-for="address in addresses" :key="address._id"  >
                            <div class="border rounded p-2" @click="takeOrderForm.address = address._id" style="cursor: pointer;" :class="(takeOrderForm.address == address._id) ? 'border-success' : ''">
                                <h5>{{ address.name }}</h5>
                                <span>{{ address.neighbourhood }} {{ address.fullAddressText }} {{ address.town }}</span>
                            </div>
                        </div>

                        <button @click="takeOrder" class="btn w-100 text-white" style="background-color: var(--tertitary-color);">Take
                            order</button>
                    </div>
                </div>
            </div>
        </section>
    </NuxtLayout>
</template>

<script lang="ts" setup>
import { API_URL } from '~/common/Api';
import type { ICart, ICartOfProduct } from '~/model/interface/ICart';
import type { IResponse } from '~/model/interface/IResponse';
import { onMounted, nextTick, ref } from 'vue';
import type { IProduct } from '~/model/interface/IProduct';
import type { IAddress } from '~/model/interface/IAddress';


const cart = ref<ICart>()
const status = ref<IResponse[]>([])
const addresses = ref<IAddress[]>([])

const takeOrderForm = reactive({address : ""})



onMounted(async () => {
    await nextTick()
    await getUserCart()
    await getUserAddress()
})

const getUserCart = async () => {
    const { data, error } = await useAsyncData("cart", () => $fetch((API_URL + "v1/cart/"), { credentials: "include" }))
    if (error.value) {
        status.value.push(error.value.data as IResponse)
        return
    }

    const response = data.value as IResponse
    cart.value = response.data[0] as ICart
}


const removeStatus = (product: IProduct) => {
    status.value.splice(product, 1)
}


const updateCount = async (product: ICartOfProduct, count: number) => {
    if (!cart.value) return

    const findProduct: number = cart.value.products.findIndex(e => e == product)
    if (findProduct === -1) return


    const findedProduct = cart.value.products[findProduct]
    if (!findedProduct) return

    findedProduct.count = count

    await updateProduct(findedProduct)
}


const updateProduct = async (product: ICartOfProduct) => {
    const { data, error } = await useAsyncData("updateProduct", () => $fetch((API_URL + "v1/cart/add"), { credentials: "include", method: "post", body: { product: product.product._id, count: product.count } }))

    if (error.value) {
        status.value.push(error.value.data as IResponse)
        return
    }

    status.value.push(data.value as IResponse)

    await getUserCart()
}


const getUserAddress = async () => {
    const { data, error } = await useAsyncData("getAdresses", () => $fetch((API_URL + "v1/address/"), { credentials: "include", method: "get"}))
    if (error.value) {
        status.value.push(error.value.data as IResponse)
        return
    }

    const response = data.value as IResponse
    addresses.value = response.data as IAddress[]
} 

const takeOrder = async() => {
    const {data , error} = useAsyncData("takeOrder", () => $fetch((API_URL + `v1/order/${cart.value?._id}`), { credentials : "include", method : "post", body : {address : takeOrderForm.address}}) )
    if (error.value) {
        status.value.push(error.value.data as IResponse)
        return
    }

    status.value.push(data.value as IResponse)
}

</script>



<style></style>