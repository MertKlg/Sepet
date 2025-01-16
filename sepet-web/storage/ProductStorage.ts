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
            const {data,error} = await useAsyncData("products", () => $fetch(`http://localhost:8080/v1/product${query}`))
            
            if(error.value){
                console.log("i catch some errors")
                return
            }

            if(data.value){
                const response = data.value as IResponse
                

                if(response.data){
                    productsState.response.products = []
                    

                    productsState.response = JSON.parse(JSON.stringify(response.data))
                    
                    
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