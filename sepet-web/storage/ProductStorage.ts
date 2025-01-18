import { API_URL } from "~/common/Api"
import type { IFilters } from "~/model/interface/IFilters"
import type { IProduct } from "~/model/interface/IProduct"
import type { IResponse } from "~/model/interface/IResponse"

export const productStorage = defineStore("productStorage", () => {

    const productsState = reactive({
        response : {
            products : [] as IProduct[],
            minMax : [{
                min : 0 as number,
                max : 0 as number,
                value : 0 as number
            }],
        },
        filters : {} as IFilters,
        error : {}
    })


    async function getProducts(query : string = "") {
        try{
            const {data,error} = await useAsyncData("products", () => $fetch(`${API_URL}v1/product/all/${query}`))
            
            if(error.value){
                console.log("i catch some errors")
                return
            }

            if(data.value){
                const response = data.value as IResponse
                

                if(response.data){
                    productsState.response.products = []

                    if(response.data.length > 0){
                        productsState.response = JSON.parse(JSON.stringify(response.data[0]))
                    }
                    //productsState.products.push(...json["products"] as IProduct[])
                    //productsState.filters = json["filters"] as IFilters
                }

            }else{
                console.error("Data not founded");
            }

        }catch(e){
            console.error(e)
        }
    }

    return {productsState, getProducts}
})