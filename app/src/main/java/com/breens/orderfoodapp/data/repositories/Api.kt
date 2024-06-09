package com.breens.orderfoodapp.data.repositories

import com.breens.orderfoodapp.feature_tasks.state.Data
import retrofit2.http.GET

interface Api {
    @GET("s/AKfycbwtw9IwjSHrOazXqkEmBtd5Hrik1sIkdaOgUzi2uNMIuM8_0IfamSLFo4auFDnaF_tzPA/exec")
    suspend fun getProductsList(): Data

    companion object {
        const val BASE_URL = "https://script.google.com/macros/"
    }
}