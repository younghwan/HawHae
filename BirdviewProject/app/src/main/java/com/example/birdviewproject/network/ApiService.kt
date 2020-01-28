package com.example.birdviewproject.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ApiService {

    private val service: HwahaeService by lazy {
        //init해주는것


        val retrofit = Retrofit.Builder()
            .baseUrl("https://6uqljnm1pb.execute-api.ap-northeast-2.amazonaws.com/prod/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return@lazy retrofit.create(HwahaeService::class.java)
    }

    companion object{
        val service = ApiService().service
    }




}