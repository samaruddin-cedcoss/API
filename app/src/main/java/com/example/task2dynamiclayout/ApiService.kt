package com.example.task2dynamiclayout


import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("cdn/12961/homepage/cedprev/en/1704726769.json")
    fun getData() : Call<JsonObject>
}