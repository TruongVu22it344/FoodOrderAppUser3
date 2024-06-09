package com.breens.orderfoodapp.data.repositories

import com.breens.orderfoodapp.common.Result
import com.breens.orderfoodapp.data.model.Banner
import com.breens.orderfoodapp.data.model.Card
import com.breens.orderfoodapp.data.model.Cart
import com.breens.orderfoodapp.data.model.Cate
import com.breens.orderfoodapp.data.model.Chat
import com.breens.orderfoodapp.data.model.Evaluate
import com.breens.orderfoodapp.data.model.Order
import com.breens.orderfoodapp.data.model.Task
import com.breens.orderfoodapp.data.model.User

interface Repository {

   // Tai khoan
    suspend fun loginUser(email: String, password: String): Result<List<User>>
    suspend fun logoutUser(): Result<Unit>
    suspend fun registerUser(firstName:String, lastName: String,email: String, password: String): Result<Unit>
    suspend fun forgetPassword(email: String): Result<Unit>

//    Card
    suspend fun getAllCards(): Result<List<Card>>
 //    suspend fun deleteCard(cardId: String): Result<Unit>
    suspend fun updateCard(image: String,title: String, body: String, price: Int, favorite: Int, views: Int, sale: Int,cate:String, cardId: String): Result<Unit>

    suspend fun getAllTasks(query: String): Result<List<Task>>

    suspend fun getAllBanner(): Result<List<Banner>>

    //   Category
    suspend fun getAllCates(): Result<List<Cate>>

    suspend fun addCart(image: String, title: String,body: String,price: Int,foodId:String): Result<Unit>

    suspend fun getAllCarts(): Result<List<Cart>>
    suspend fun deleteCart(foodId: String): Result<Unit>
    suspend fun addOrder(userID: String, code: String, address: String,imageOrder: String, titleOrder: String,price: Int, quantity: Int, paymentMethods: String, total: Int): Result<Unit>
    suspend fun getAllOrder(): Result<List<Order>>
    suspend fun updateStatus(userID: String, code: String, address: String,imageOrder: String, titleOrder: String,price:  Int , quantity:  Int, paymentMethods: String, total: Int,status: String, orderId: String): Result<Unit>

    suspend fun addMessage( senderID: String, message: String, direction : Boolean): Result<Unit>
    suspend fun getAllMessage(): Result<List<Chat>>

    suspend fun addEvaluate(userID: String,body: String,numberStar: Int,foodID:String, ID: String): Result<Unit>

    suspend fun getAllEvaluates(): Result<List<Evaluate>>
    suspend fun deleteEvaluate(ID: String): Result<Unit>
}


