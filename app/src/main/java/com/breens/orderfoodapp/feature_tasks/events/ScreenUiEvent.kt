package com.breens.orderfoodapp.feature_tasks.events

import com.breens.orderfoodapp.data.model.Card
import com.breens.orderfoodapp.data.model.Evaluate
import com.breens.orderfoodapp.data.model.Order
sealed class CartsScreenUiEvent {
    object GetCarts : CartsScreenUiEvent()
    data class DeleteNote(val foodId: String) : CartsScreenUiEvent()
    data class OnChangeTaskImage(val image: String) : CartsScreenUiEvent()
    data class OnChangeTaskTitle(val title: String) : CartsScreenUiEvent()
    data class OnChangeTaskBody(val body: String) : CartsScreenUiEvent()
    data class OnChangeTaskQuantity(val quantity: Int) : CartsScreenUiEvent()
    data class OnChangeTaskPrice(val price: Int) : CartsScreenUiEvent()
}
sealed class TasksScreenUiEvent {
    /*object GetTasks : TasksScreenUiEvent()*/
    data class GetTasks(val query: String) : TasksScreenUiEvent()

    data class OnChangeTaskImage(val image: String) : TasksScreenUiEvent()
    data class OnChangeTaskTitle(val title: String) : TasksScreenUiEvent()
    data class OnChangeTaskBody(val body: String) : TasksScreenUiEvent()
    data class OnChangeTaskPrice(val price: Int) : TasksScreenUiEvent()


}
sealed class CardsScreenUiEvent {
    object AddCart : CardsScreenUiEvent()

    object GetCards : CardsScreenUiEvent()
    object UpdateNote : CardsScreenUiEvent()
    data class OnChangeCate(val cate: String) : CardsScreenUiEvent()
    data class OnChangeCardImage(val image: String) : CardsScreenUiEvent()
    data class OnChangeCardTitle(val title: String) : CardsScreenUiEvent()
    data class OnChangeCardBody(val body: String) : CardsScreenUiEvent()
    data class OnChangeCardPrice(val price: Int) : CardsScreenUiEvent()
    data class OnChangeCardFavorite(val favorite: Int) : CardsScreenUiEvent()
    data class OnChangeCardViews(val views: Int) : CardsScreenUiEvent()
    data class OnChangeCardSales(val sale: Int) : CardsScreenUiEvent()
    data class OnChangeUpdateCardDialogState(val show: Boolean) : CardsScreenUiEvent()
    data class SetCardToBeUpdated(val cardToBeUpdated: Card) : CardsScreenUiEvent()
}
sealed class BannerScreenUiEvent {
    object GetBanner : BannerScreenUiEvent()
    data class OnChangeBannerImage(val imageBanner: String) : BannerScreenUiEvent()
    data class OnChangeBannerTitle(val titleBanner: String) : BannerScreenUiEvent()

}
sealed class CatesScreenUiEvent {
    object GetCates : CatesScreenUiEvent()
    data class OnChangeCateImage(val imageCate: String) : CatesScreenUiEvent()
    data class OnChangeCateTitle(val titleCate: String) : CatesScreenUiEvent()

}
sealed class OrderScreenUiEvent {
    object GetOrder : OrderScreenUiEvent()
    data class AddOrder(val userID: String, val code: String,val address: String, val imageOrder: String,val titleOrder: String,val price: Int,val quantity: Int,val paymentMethods: String, val total: Int) : OrderScreenUiEvent()
    data class OnChangeOrderAddress(val address: String) : OrderScreenUiEvent()
    data class OnChangeOrderQuantity(val quantity: Int) : OrderScreenUiEvent()
    data class OnChangeOrderPayment(val paymentMethods: String) : OrderScreenUiEvent()
    data class OnChangeOrderTitle(val title: String) : OrderScreenUiEvent()
    data class OnChangeOrderImageOrder(val imageOrder: String) : OrderScreenUiEvent()
    data class OnChangeOrderPrice(val price: Int) : OrderScreenUiEvent()
    data class OnChangeOrderTotal(val total: Int) : OrderScreenUiEvent()
    data class OnChangeOrderStatus(val status: String) : OrderScreenUiEvent()
    data class OnChangeFoodCode(val code: String) : OrderScreenUiEvent()
    data class OnChangeUserID(val userID: String) : OrderScreenUiEvent()
    object UpdateNote : OrderScreenUiEvent()
    data class SetStatusToBeUpdated(val statusToBeUpdated: Order) : OrderScreenUiEvent()
    data class OnChangeDialogState(val show: Boolean) : OrderScreenUiEvent()
}
sealed class SignInScreenUiEvent {
    object GetAccount : SignInScreenUiEvent()
    data class RegisterUser(val firstname: String, val lastname: String, val email: String,val password: String) : SignInScreenUiEvent()
    object LoginUser : SignInScreenUiEvent()
    data class ForgetPassword(val email: String) : SignInScreenUiEvent()

    object LogoutUser : SignInScreenUiEvent()
    data class OnChangeFirstname(val firstname: String) : SignInScreenUiEvent()
    data class OnChangeLastname(val lastname: String) : SignInScreenUiEvent()
    data class OnChangeEmail(val email: String) : SignInScreenUiEvent()
    data class OnChangePassword(val password: String) : SignInScreenUiEvent()

}
sealed class ChatScreenUiEvent {
    object GetMessage: ChatScreenUiEvent()
    data class AddMessage(val senderID: String, val message: String, val direction: Boolean ) : ChatScreenUiEvent()
    data class OnChangeSenderID(val senderID: String) : ChatScreenUiEvent()
    data class OnChangeMessage(val message: String) : ChatScreenUiEvent()
    data class OnChangeReceiveID(val receiveID: String) : ChatScreenUiEvent()
    data class OnChangeDirection(val direction: Boolean) : ChatScreenUiEvent()
}
sealed class EvaluatesScreenUiEvent {
    object GetEvaluates : EvaluatesScreenUiEvent()
    data class AddEvaluate(val userID: String,val foodID: String, val body: String, val numberStar: Int) : EvaluatesScreenUiEvent()
    data class DeleteNote(val ID: String) : EvaluatesScreenUiEvent()
    data class OnChangeUserID(val userID: String) : EvaluatesScreenUiEvent()
    data class OnChangeFoodID(val foodID: String) : EvaluatesScreenUiEvent()
    data class OnChangeBody(val body: String) : EvaluatesScreenUiEvent()
    data class OnChangeNumberStar(val numberStar: Int) : EvaluatesScreenUiEvent()

    data class SetEvaluateToBeUpdated(val evaluateToBeUpdated: Evaluate) : EvaluatesScreenUiEvent()

}

