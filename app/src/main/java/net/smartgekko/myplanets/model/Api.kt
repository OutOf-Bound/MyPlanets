package net.smartgekko.myplanets.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("apod")
    fun getPodDataFromApi(
        @Query("date") page: String,
        @Query("api_key") apiKey: String
    ): Call<GetPodDataResponse>
}