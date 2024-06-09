package com.breens.orderfoodapp.feature_tasks.state

import android.graphics.Bitmap
import com.breens.orderfoodapp.data.model.Banner
import com.breens.orderfoodapp.data.model.Card
import com.breens.orderfoodapp.data.model.Cart
import com.breens.orderfoodapp.data.model.Cate
import com.breens.orderfoodapp.data.model.Chat
import com.breens.orderfoodapp.data.model.DataX
import com.breens.orderfoodapp.data.model.Evaluate
import com.breens.orderfoodapp.data.model.Order
import com.breens.orderfoodapp.data.model.Task
import com.breens.orderfoodapp.data.model.User

data class SignInScreenUiState(
    val accounts: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val currentFirstname: String = "",
    val currentLastName: String = "",
    var currentEmail: String = "",
    var emailSaved: String = "",
    var currentPassword: String = "",
    var passwordSaved: String = "",
)
data class TasksScreenUiState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val errorMessage: String? = null,
    val taskToBeUpdated: Task? = null,
    val isShowAddTaskDialog: Boolean = false,
    val isShowUpdateTaskDialog: Boolean = false,
    val currentTextFieldTitle: String = "",
    val currentTextFieldBody: String = "",
    val currentTextFieldPrice: Int = 0,
    var imgUrl: String = "",
    var bitmap: Bitmap? = null,
    var selectedOptionSale : Int = options[0],
)
data class CardsScreenUiState(
    val isLoading: Boolean = false,
    val cards: List<Card> = emptyList(),
    val errorMessage: String? = null,
    val cardToBeUpdated: Card? = null,
    val isShowAddCardDialog: Boolean = false,
    val isShowUpdateCardDialog: Boolean = false,
    val currentTextFieldTitle: String = "",
    val currentTextFieldBody: String = "",
    val currentTextFieldPrice: Int = 0,
    val currentTextFieldViews: Int = 0,
    var currentTextFieldFavorite: Int = 0,
    val currentTextFieldSale: Int = 0,
    val cate: String = "",
    var imgUrl: String = "",
    var bitmap: Bitmap? = null,

    var selectedOption : Int = options[0],
    val cartProducts: List<Card> = emptyList(),

    )

data class BannerScreenUiState(
    val isLoadingBanner: Boolean = false,
    val banners: List<Banner> = emptyList(),
    val errorMessage: String? = null,
    val isShowAddBannerDialog: Boolean = false,
    val currentTextFieldTitleBanner: String = "",
    var imgUrlBanner: String = "",
    var bitmapBanner: Bitmap? = null
)
data class CatesScreenUiState(
    val isLoadingCate: Boolean = false,
    val cates: List<Cate> = emptyList(),
    val errorMessage: String? = null,
    val isShowAddCateDialog: Boolean = false,
    val currentTextFieldTitleCate: String = "",
    var imgUrlCate: String = "",
    var bitmapCate: Bitmap? = null,
)
data class CartsScreenUiState(
    val isLoading: Boolean = false,
    val carts: List<Cart> = emptyList(),
    val errorMessage: String? = null,
    val currentTextFieldTitle: String = "",
    val currentTextFieldBody: String = "",
    val currentTextFieldPrice: Int = 0,
    val currentTextFieldQuantity: Int = 0,
    var imgUrl: String = "",
    var bitmap: Bitmap? = null,
    var selectedOptionSale : Int = options[0],
)
data class OrderScreenUiState(
    val isLoading: Boolean = false,
    val orders: List<Order> = emptyList(),
    val statusToBeUpdated: Order? = null,
    val errorMessage: String? = null,
    val currentCode: String = "",
    val currentUserID: String = "",
    val currentTitle: String = "",
    val currentAddressOrder: String = "",
    val currentQuantityOrder:Int = 0,
    val currentPriceOrder: Int = 0,
    val total: Int = 0,
    val currentPaymentOrder: String = "",
    var imgUrlOrder: String = "",
    var bitmapOrder: Bitmap? = null,
    val currentStatus: String = "",
    var selectedOptionPayment : String = optionsPayment[0],
    var selectedOptionAddress : String = optionsAddress[0],
    var selectedOptionSale : Int = options[0],
    var valueChange : Int = 1,
    var valueCart : Int = 0,
    var value : Int = 1,
    val isShowDialog: Boolean = false,

)
val options = listOf(0,10000 ,15000, 20000)
val optionsAddress = listOf("FPT Plaza2, đường Trần Quốc Vượng, phường Hòa Hải", "Kí túc xá VKU, đường Nam kì Khởi Nghĩa, phường Hòa Hải")
val optionsPayment = listOf("Thanh toán khi nhận hàng" ,"Thanh toán qua ví điện tử","Thanh toán qua ZaloPay")
data class ChatScreenUiState(
    val isLoading: Boolean = false,
    val messages: List<Chat> = emptyList(),
    val errorMessage: String? = null,
    val currentMessage: String = "",
    val currentSenderID: String = "",
    val currentReceiveID: String = "",
    val direction: Boolean = false,
    var imgUrl: String = "",
    var bitmap: Bitmap? = null,
    )
data class Data(
    val `data`: List<DataX> = emptyList(),
    val error: Boolean = false,
    val limit: Int = 0,
    val skip: Int = 0,
    val total: Int = 0,
    val isShowDialog: Boolean = false,
)
data class EvaluateScreenUiState(
    val isLoading: Boolean = false,
    val evaluates: List<Evaluate> = emptyList(),
    val errorMessage: String? = null,
    val currentUserID: String = "",
    val currentFoodID: String = "",
    val currentTextFieldBody: String = "",
    val currentNumberStar: Int = 0,
    val evaluateToBeUpdated: Evaluate? = null,
)

