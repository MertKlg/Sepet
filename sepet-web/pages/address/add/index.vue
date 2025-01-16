<template>
  <NuxtLayout>
    <section class="container vh-100 mt-3">
      <div class="h-100">
        <h3>Add a new address</h3>

        <form @submit.prevent="addAddressReq">
          <div class="mb-3">
            <label for="addressNameInput" class="form-label">Address name</label>
            <input type="text" class="form-control" v-model="addressForm.name" id="addressNameInput">
          </div>

          <div class="row">
            <div class="col">
              <div class="mb-3">
                <label for="townInput" class="form-label">Town</label>
                <input type="text" class="form-control" v-model="addressForm.town" id="townInput">
              </div>
            </div>

            <div class="col">
              <div class="mb-3">
                <label for="neighbourhoodInput" class="form-label">Neighbourhood</label>
                <input type="text" class="form-control" v-model="addressForm.neighbourhood" id="neighbourhoodInput">
              </div>
            </div>
          </div>

          <div class="mb-3">
            <label for="fullAddressInput" class="form-label">Full address</label>
            <textarea type="text" class="form-control" id="fullAddressInput"
              v-model="addressForm.fullAddressText"></textarea>
          </div>

          <input type="submit" class="btn text-white " style="background-color: var(--tertitary-color);"
            value="Add new address">
        </form>

        <div v-if="status">
          <h3>{{ status.message }}</h3>
        </div>
      </div>
    </section>
  </NuxtLayout>
</template>

<script setup lang="ts">
import type { IAddress } from '~/model/interface/IAddress';
import { ref } from "vue"
import { API_URL } from '~/common/Api';
import { IResponse } from '~/model/interface/IResponse';

const addressForm = ref<IAddress>({ _id: "", fullAddressText: "", name: "", neighbourhood: "", town: "" })
const status = ref<IResponse>()


const addAddressReq = async () => {
  try {
    const jsonString = JSON.parse(JSON.stringify({
      name: addressForm.value.name,
      fullAddressText: addressForm.value.fullAddressText,
      neighbourhood: addressForm.value.neighbourhood,
      town: addressForm.value.town
    }))

    const res = await $fetch((API_URL + "v1/address/add"), { credentials: "include", method: "POST", body: jsonString })

 
    status.value = res as IResponse
  
  } catch (e) {
    console.error(e)
  }

}


</script>

<style scoped></style>