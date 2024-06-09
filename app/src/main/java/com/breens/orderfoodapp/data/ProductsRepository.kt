package com.breens.orderfoodapp.data


import com.breens.orderfoodapp.data.model.DataX
import kotlinx.coroutines.flow.Flow

/**
 * @author Android Devs Academy (Ahmed Guedmioui)
 */
interface ProductsRepository {
    suspend fun getProductsList(): Flow<Result<List<DataX>>>
}