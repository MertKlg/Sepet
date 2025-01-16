<script setup lang="ts">
import { ref,onMounted, reactive,nextTick } from "vue";
import { API_URL } from "~/common/Api";
import type { ICategory } from "~/model/interface/ICategory";
import type { IProduct } from "~/model/interface/IProduct";
import type { IResponse } from '~/model/interface/IResponse';
import { productStorage } from "~/storage/ProductStorage";

const productStore = productStorage();

var currentUrl: URLSearchParams | null = null

const status = ref<IResponse[]>([])
const addToCartPeriods = ref(false)


const category = reactive({
  categories: [] as ICategory[],
  error: {}
})


const filters = reactive({
  category: [] as string[],
  minPrice: "",
  maxPrice: "",
  min : 0,
  max : 0
})

const getCategories = async () => {
  try {
    const { data, error } = await useAsyncData("categories", () => $fetch("http://localhost:8080/v1/category"))

    if (error.value) {
      console.error("i had some error")
      return
    }

    category.categories.push(...data.value as ICategory[])

  } catch (e) {
    console.error(e)
  }
}

onMounted(async () => {
  await nextTick()
  await getCategories()

  currentUrl = new URLSearchParams(window.location.search)

  if (currentUrl) {
    filters.category = currentUrl.get("category") != null && currentUrl.get("category") != undefined ? currentUrl.get("category")!!.split(",") : [] as string[]
    filters.minPrice = currentUrl.get("minPrice") != null && currentUrl.get("minPrice") != undefined ? String(currentUrl.get("minPrice")) : ""
    filters.maxPrice = currentUrl.get("maxPrice") != null && currentUrl.get("maxPrice") != undefined ? String(currentUrl.get("maxPrice")) : ""
    filters.min = currentUrl.get("min") != null && currentUrl.get("min") != undefined ? Number.parseInt(String(currentUrl.get("min"))) : 0
    filters.max = currentUrl.get("max") != null && currentUrl.get("max") != undefined ? Number.parseInt(String(currentUrl.get("max"))) : 10

  }

  await productStore.getProducts(`?${currentUrl.toString()}`);
})


const filterProducts = async () => {
  
  Object.entries(filters).forEach(([key, value]) => {
    if (value != "" || (value instanceof Array).valueOf.length > 0) {
      currentUrl?.set(key, value.toString().trim())
    } else {
      currentUrl?.delete(key)
    }
  })

  var filteredUrl = `?${currentUrl?.toString()}`

  window.history.pushState({}, '', `${window.location.pathname}${filteredUrl}`);
  await productStore.getProducts(filteredUrl)
}

const clearFilters = async () => {
  window.history.pushState({}, '', `${window.location.pathname}`);
  filters.category = []
  filters.minPrice = ""
  filters.maxPrice = ""
  filters.min = 0
  filters.max = 0

  await productStore.getProducts()
}



const addToCart = async (product: IProduct) => {
  try {

    addToCartPeriods.value = !addToCartPeriods.value

    const { data, error } = await useAsyncData("addToCart", () => $fetch((API_URL + `v1/cart/add`), { credentials: "include", method: "POST", body: { product: product?._id, count: 1 } }))

    if (error.value) {
      status.value.push(error.value.data as IResponse)
      return
    }

    status.value.push(data.value as IResponse)

    setTimeout(() => addToCartPeriods.value = !addToCartPeriods.value, 1000)

  } catch (e) {
    console.error(e)
  }
}

const removeStatus = (product: IProduct) => {
  status.value.splice(product, 1)
}

</script>

<template>
  <NuxtLayout>
    <section class="my-5 min-vh-100">
      <ToastMessages :messages="status" @close-toast="removeStatus" />

      <div class="container">
        <div class="row m-0">
          <div class="col-12 col-lg-3">
            <h4 class="fw-bold border-bottom">Filters</h4>
            <div class="filters">
              <h6>Categories</h6>
              <div class="col">
                <div v-for="item in category.categories" :key="item._id">
                  <div class="d-flex align-items-center">
                    <input :id="item._id" type="checkbox" :value="item._id" v-model="filters.category"
                      @change="filterProducts" @click="filters.min = 0, filters.max = 0">
                    <label :for="item._id" class="ps-1">{{ item.name }}</label>
                  </div>
                </div>
              </div>

              <div class="col mt-3">
                <h6>Pricing</h6>
                <div class="d-flex align-items-center">
                  <input type="number" class="w-25 form-control" placeholder="Min" v-model="filters.minPrice" min="0">
                  <span class="px-2">-</span>
                  <input type="number" class="w-25 form-control" placeholder="Max" v-model="filters.maxPrice" min="0">

                  <button style="background-color: var(--tertitary-color) !important;"
                    class="ms-3 btn rounded text-white" @click="filterProducts">Search</button>
                </div>
              </div>

              <div class="col mt-3">
                <button style="background-color: var(--tertitary-color) !important;"
                  class="btn rounded text-white w-100" @click="clearFilters">Clear filters</button>
              </div>
            </div>
          </div>
          <div class="col-12 col-lg-9">
            <div class="d-grid" style="gap: 10px !important;"
              v-if="productStore.productsState.response.products.length > 0">
              <div class="card" v-for="item in productStore.productsState.response.products" :key="item._id">
                <div class="card-img-top text-center">
                  <img class="img-fluid mt-3" style="height: 120px; width: 120px; object-fit: contain;"
                    :src="'http://localhost:8080/storage/' + item.image" alt="image" decoding="async" />
                </div>

                <div class="card-body">
                  <h5 class="card-title">{{ item.name }}</h5>
                  <p class="card-text fw-light">{{ item.description }}</p>
                  <p class="card-text fw-bold" style="color: var(--tertitary-color);" @click="filterProducts">{{
                    item.price
                  }}$</p>

                  <button class="w-100 btn text-white" :disabled="addToCartPeriods"
                    style="background-color: var(--tertitary-color);" @click="addToCart(item)"
                    aria-describedby="add cart">Add to cart</button>
                </div>
              </div>
            </div>

            <div v-else>
              <h1>No products found</h1>
            </div>

            <div class="d-flex flex-row mt-5">
              <button class="btn text-white mx-2" style="background-color: var(--tertitary-color);" v-for="minMaxValue in productStore.productsState.response.minMax" :key="minMaxValue.min" @click="filters.min = minMaxValue.min, filters.max = minMaxValue.max, filterProducts()">
                {{ minMaxValue.value }}
              </button>
            </div>

          </div>
        </div>
      </div>

    </section>
  </NuxtLayout>
</template>

<style scoped>
.d-grid {
  grid-template-columns: repeat(4, auto) !important;
}

@media only screen and (max-width: 768px) {
  .d-grid {
    grid-template-columns: repeat(2, auto) !important;
  }
}

@media only screen and (max-width: 425px) {
  .d-grid {
    grid-template-columns: repeat(1, auto) !important;
  }
}
</style>