package com.mk.sepetandroid

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mk.sepetandroid.common.Constants
import com.mk.sepetandroid.data.mapper.address.toModel
import com.mk.sepetandroid.data.remote.ProductApi
import com.mk.sepetandroid.data.remote.AuthApi
import com.mk.sepetandroid.data.remote.CategoryApi
import com.mk.sepetandroid.data.remote.OrderApi
import com.mk.sepetandroid.domain.model.cart.CartModel
import com.mk.sepetandroid.domain.model.response.ResponseModel
import com.mk.sepetandroid.domain.repository.ICartRepo
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiTests {

    private val ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2Njg2ZGQ3YTJhMjk2ZmUzOWE0M2Q1MTYiLCJpYXQiOjE3MzMyMjM1ODIsImV4cCI6MTczMzIyNzE4Mn0.J_ggNZWlqO5-LoOXygbi42sV_tqDNdLe0xECMiksEH0"
    private lateinit var api: Retrofit
    private lateinit var gson : Gson

    @Before
    fun loadRetrofit(){
        api = Retrofit.Builder()
            .baseUrl(Constants.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        gson = Gson()
    }


    @Test
    fun callCart(){
        val gson = Gson()
        val type = object : TypeToken<ResponseModel<List<CartModel>>>() {}.type
        runBlocking{
            val res = api.create(ICartRepo::class.java).getCart(ACCESS_TOKEN)
            val convert = gson.fromJson<ResponseModel<List<CartModel>>>(res.toString(), type)
            println(convert)
        }
    }

    @Test
    fun callProducts(){
        val res = api.create(ProductApi::class.java)

        runBlocking{
            res.getProducts().let { println(it) }
        }
    }

    @Test
    fun callProfile(){
        val ser = api.create(AuthApi::class.java)


    }

    @Test
    fun callOrders(){
        val res = api.create(OrderApi::class.java)

        runBlocking {
            try{


                res.getOrderDetail(ACCESS_TOKEN, "6731f5b45b3a2f0243ae2226").let { println(it) }
            }catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }

    @Test
    fun callCategories(){
        val res = api.create(CategoryApi::class.java)

        runBlocking {
            try{
                res.getCategories().let { println(it) }
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }



}