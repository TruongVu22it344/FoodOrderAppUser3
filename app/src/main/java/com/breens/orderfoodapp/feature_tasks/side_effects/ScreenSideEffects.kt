package com.breens.orderfoodapp.feature_tasks.side_effects

sealed class TaskScreenSideEffects {
    data class ShowSnackBarMessage(val message: String) : TaskScreenSideEffects()
}
sealed class BannerScreenSideEffects {
    data class ShowSnackBarMessage(val messageBanner: String) : BannerScreenSideEffects()
}
sealed class OrderScreenSideEffects {
    data class ShowSnackBarMessage(val messageOrder: String) : OrderScreenSideEffects()
}
sealed class CateScreenSideEffects {
    data class ShowSnackBarMessage(val messageCate: String) : CateScreenSideEffects()
}
sealed class SignInScreenSideEffects {
    data class ShowSnackBarMessage(val messageAccount: String) : SignInScreenSideEffects()
    object NavigateToHome : SignInScreenSideEffects()
}
sealed class CardScreenSideEffects {
    data class ShowSnackBarMessage(val messageCard: String) : CardScreenSideEffects()
}
sealed class ChatScreenSideEffects {
    data class ShowSnackBarMessage(val messageChat: String) : ChatScreenSideEffects()
}
sealed class CartScreenSideEffects {
    data class ShowSnackBarMessage(val message: String) : CartScreenSideEffects()
}
sealed class EvaluateScreenSideEffects {
    data class ShowSnackBarMessage(val message: String) : EvaluateScreenSideEffects()
}
