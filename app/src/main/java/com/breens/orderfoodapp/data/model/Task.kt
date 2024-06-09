package com.breens.orderfoodapp.data.model

import androidx.compose.ui.graphics.vector.ImageVector
data class Transaction(
    val transaction_id: String,
    val amount: Double,
    val description: String,
    val created_at: String
)

data class TransactionsResponse(
    val data: List<Transaction>
)
data class Cart(
    val foodId: String = "",
    val image: String = "",
    val title: String = "",
    val body: String = "",
    val price: Int = 0,
    val createdAt: String = "",

    )
data class Task(
    val taskId: String = "",
    val image: String = "",
    val title: String = "",
    val body: String = "",
    val price: Int = 0,
    val createdAt: String = "",

    )
data class Card(
    val cardId: String = "",
    val imageCard: String = "",
    val titleCard: String = "",
    val bodyCard: String = "",
    val priceCard: Int = 0 ,
    val favorite: Int = 0,
    val views: Int = 0,
    val sale: Int = 0,
    val createdAt: String = "",
    val cate: String = ""
)
data class Banner(
    val bannerId: String = "",
    val imageBanner: String = "",
    val titleBanner: String = "",
    val createdAt: String = ""
)
data class TabItems(
    val id: Int,
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
)
data class Order(
    val orderId: String ="",
    val address: String = "",
    val titleOrder: String="",
    val imageOrder: String = "",
    val createdAt: String = "",
    val price: Int = 0,
    val quantity: Int = 0,
    val paymentMethods: String = "",
    val total: Int = 0,
    val status: String = "",
    val code : String = "",
    val userID : String = "",
)
data class Cate(
    val cateId: String = "",
    val imageCate: String = "",
    val titleCate: String = "",
    val createdAt: String = "",
)
data class TabItemsOrder(
    val id: Int,
    val title: String,
)
data class User(
    val userID: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email:String = "",
    val role : Int = 0,
)
data class Chat(
    val chatID: String = "",
    val senderID: String = "",
    val receiveID: String = "",
    val message: String =" ",
    val direction: Boolean = false,
    val createdAt: String = "",
)
data class ChatID(
    val chatID: String = "",
)
data class Evaluate(
    val Id: String = "",
    val userID: String = "",
    val foodID: String = "",
    val numberStar: Int = 0,
    val body: String = "",
    val createdAt: String = "",
)
data class DataX(
    val giaTri: Int,
    val maGD: Int,
    val moTa: String,
    val ngayDienRa: String,
    val soTaiKhoan: String
)
