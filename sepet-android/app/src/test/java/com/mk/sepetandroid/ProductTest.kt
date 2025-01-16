package com.mk.sepetandroid

import com.mk.sepetandroid.common.Constants
import com.mk.sepetandroid.data.remote.ProductApi
import com.mk.sepetandroid.data.remote.dto.product.ProductDto
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductTest {

    private lateinit var retroft : ProductApi

    @Before
    fun getRetrofit() {
        retroft =  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.URL)
            .build()
            .create(ProductApi::class.java)
    }


    @Test
    fun getTestProducts(){


        val ids = listOf<ProductDto>(
            ProductDto(
                id = "668d366ce730a24f0903a610"
            ),
            ProductDto(
                id = "668d382db4a2b974cca873d8"
            ),
            ProductDto(
                id = "668d3c2a0b234cd1449e4e02"
            ),
            ProductDto(
                id = "668d3c580b234cd1449e4e06"
            ),
        )

        val ids2 = listOf<String>(
            "668d366ce730a24f0903a610",
            "668d382db4a2b974cca873d8",
            "668d3c2a0b234cd1449e4e02",
            "668d3c580b234cd1449e4e06"
        )





    }
}