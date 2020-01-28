package com.example.birdviewproject.network

import com.example.birdviewproject.model.ProductDetailResp
import com.example.birdviewproject.model.ProductsResp
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface HwahaeService {
    @GET("products")
    fun getProuducts(@QueryMap map: Map<String, String> = mapOf()): Single<ProductsResp>

    @GET("products/{id}")
    fun getProductDetail(@Path("id") id : Int) : Single<ProductDetailResp>
}