package com.breens.orderfoodapp.data

import com.breens.orderfoodapp.feature_tasks.state.Data
import retrofit2.http.GET

/**
 * @author Android Devs Academy (Ahmed Guedmioui)
 */
interface Api {

    @GET("s/AKfycbwtw9IwjSHrOazXqkEmBtd5Hrik1sIkdaOgUzi2uNMIuM8_0IfamSLFo4auFDnaF_tzPA/exec")
    suspend fun getProductsList(): Data

    companion object {
        const val BASE_URL = "https://script.google.com/macros/"
    }


//    @GET("products/{type}")
//    suspend fun getProductsList(
//        @Path("type") type: String,
//        @Query("page") page: Int,
//        @Query("api_key") apiKey: String
//    ): Products

}