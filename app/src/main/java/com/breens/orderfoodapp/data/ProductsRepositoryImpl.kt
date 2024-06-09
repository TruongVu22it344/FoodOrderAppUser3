package com.breens.orderfoodapp.data

import com.breens.orderfoodapp.data.model.DataX
import com.breens.orderfoodapp.data.repositories.Api
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

/**
 * @author Android Devs Academy (Ahmed Guedmioui)
 */
class ProductsRepositoryImpl(
    private val api: Api
): ProductsRepository {

    override suspend fun getProductsList(): Flow<Result<List<DataX>>> {
        return flow {
            val productsFromApi = try {
                api.getProductsList()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            }

            // Sắp xếp danh sách theo ngày giảm dần
            val sortedProducts = productsFromApi.data.sortedByDescending { it.ngayDienRa }

            emit(Result.Success(sortedProducts))
        }
    }
}

















