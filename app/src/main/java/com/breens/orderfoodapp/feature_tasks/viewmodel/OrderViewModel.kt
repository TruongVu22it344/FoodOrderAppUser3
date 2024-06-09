package com.breens.orderfoodapp.feature_tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breens.orderfoodapp.common.Result
import com.breens.orderfoodapp.data.model.Order
import com.breens.orderfoodapp.data.repositories.Repository
import com.breens.orderfoodapp.feature_tasks.events.OrderScreenUiEvent
import com.breens.orderfoodapp.feature_tasks.side_effects.OrderScreenSideEffects
import com.breens.orderfoodapp.feature_tasks.state.OrderScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val orderRepository: Repository) : ViewModel() {

    private val _stateOrder: MutableStateFlow<OrderScreenUiState> =
        MutableStateFlow(OrderScreenUiState())
    val stateOrder: StateFlow<OrderScreenUiState> = _stateOrder.asStateFlow()

    private val _effectOrder: Channel<OrderScreenSideEffects> = Channel()
    val effectOrder = _effectOrder.receiveAsFlow()

    init {
        sendEvent(OrderScreenUiEvent.GetOrder)
        viewModelScope.launch {
            while (true) {
                delay(5000) // Thực hiện cập nhật mỗi 5 giây
                sendEvent(OrderScreenUiEvent.GetOrder)
            }
        }
    }


    fun sendEvent(event: OrderScreenUiEvent) {
        reduce(oldStateOrder = _stateOrder.value, event = event)
    }

    private fun setEffect(builder: () -> OrderScreenSideEffects) {
        val effectValue = builder()
        viewModelScope.launch { _effectOrder.send(effectValue) }
    }

    private fun setState(newStateOrder: OrderScreenUiState) {
        _stateOrder.value = newStateOrder
    }




    private fun reduce(oldStateOrder: OrderScreenUiState, event: OrderScreenUiEvent) {
        when (event) {
            is OrderScreenUiEvent.AddOrder -> {
                addOrder(oldStateOrder = oldStateOrder, userID = event.userID, code = event.code, address = event.address, imageOrder = event.imageOrder, titleOrder = event.titleOrder, price = event.price, quantity = event.quantity, paymentMethods = event.paymentMethods,total= event.total)
            }



            OrderScreenUiEvent.GetOrder -> {
                getOrder(oldStateOrder = oldStateOrder)
            }
            is OrderScreenUiEvent.OnChangeUserID-> {
                onChangeUserID(oldStateOrder = oldStateOrder, userID = event.userID)
            }

            is OrderScreenUiEvent.OnChangeFoodCode-> {
                onChangeFoodCode(oldStateOrder = oldStateOrder, code = event.code)
            }

            is OrderScreenUiEvent.OnChangeOrderAddress-> {
                onChangeOrderAddress(oldStateOrder = oldStateOrder, address = event.address)
            }
            is OrderScreenUiEvent.OnChangeOrderQuantity -> {
                onChangeOrderQuantity(oldStateOrder = oldStateOrder, quantity = event.quantity)
            }

            is OrderScreenUiEvent.OnChangeOrderPayment -> {
                onChangeOrderPayment(oldStateOrder = oldStateOrder, paymentMethods = event.paymentMethods)
            }
            is OrderScreenUiEvent.OnChangeOrderImageOrder-> {
                onChangeOrderImageOrder(oldStateOrder = oldStateOrder, imageOrder = event.imageOrder)
            }
            is OrderScreenUiEvent.OnChangeOrderTitle -> {
                onChangeOrderTitle(oldStateOrder = oldStateOrder, title = event.title)
            }

            is OrderScreenUiEvent.OnChangeOrderPrice -> {
                onChangeOrderPrice(oldStateOrder = oldStateOrder, price = event.price)
            }
            is OrderScreenUiEvent.OnChangeOrderTotal -> {
                onChangeOrderTotal(oldStateOrder = oldStateOrder, total = event.total)
            }
            is OrderScreenUiEvent.OnChangeOrderStatus -> {
                onChangeOrderStatus(oldStateOrder = oldStateOrder, status = event.status)
            }

            is OrderScreenUiEvent.SetStatusToBeUpdated -> {
                setStatusToBeUpdated(oldStateOrder = oldStateOrder, order = event.statusToBeUpdated)
            }

            OrderScreenUiEvent.UpdateNote -> {
                updateNote(oldStateOrder = oldStateOrder)
            }
            is OrderScreenUiEvent.OnChangeDialogState -> {
                onChangeDialog(oldStateOrder = oldStateOrder, isShown = event.show)
            }

        }
    }



    private fun addOrder(userID:String, code:String, address: String, imageOrder: String,titleOrder: String,price: Int, quantity: Int, paymentMethods: String,total: Int, oldStateOrder: OrderScreenUiState) {
        viewModelScope.launch {
            setState(oldStateOrder.copy(isLoading = true))

            when (val resultOrder = orderRepository.addOrder(userID = userID, code = code, address = address, imageOrder = imageOrder, titleOrder = titleOrder, price = price , quantity = quantity, paymentMethods = paymentMethods,total= total)) {
                is Result.Failure -> {
                    setState(oldStateOrder.copy(isLoading = false))

                    val errorMessage =
                        resultOrder.exception.message ?: "An error occurred when adding task"
                    setEffect { OrderScreenSideEffects.ShowSnackBarMessage(messageOrder = errorMessage) }
                }

                is Result.Success -> {
                    setState(
                        oldStateOrder.copy(
                            isLoading = false,
                            bitmapOrder = null,
                            currentAddressOrder = "",
                            currentQuantityOrder = 1,
                            currentPaymentOrder = ""
                        ),
                    )

                    sendEvent(OrderScreenUiEvent.GetOrder)

                    setEffect { OrderScreenSideEffects.ShowSnackBarMessage(messageOrder = "Đặt món thành công") }
                }
            }
        }
    }

    private fun getOrder(oldStateOrder: OrderScreenUiState) {
        viewModelScope.launch {

            when (val resultOrder = orderRepository.getAllOrder()) {
                is Result.Failure -> {

                    val errorMessage =
                        resultOrder.exception.message ?: "An error occurred when getting your orders"
                    setEffect { OrderScreenSideEffects.ShowSnackBarMessage(messageOrder = errorMessage) }
                }

                is Result.Success -> {
                    val orders = resultOrder.data
                    setState(oldStateOrder.copy( orders = orders))
                }
            }
        }
    }

    private fun updateNote(oldStateOrder: OrderScreenUiState) {
        viewModelScope.launch {
            setState(oldStateOrder.copy(isLoading = true))
            val userID = oldStateOrder.statusToBeUpdated?.userID ?:""
            val code = oldStateOrder.statusToBeUpdated?.code ?:""
            val imageOrder = oldStateOrder.statusToBeUpdated?.imageOrder ?:""
            val title = oldStateOrder.statusToBeUpdated?.titleOrder ?:""
            val address = oldStateOrder.statusToBeUpdated?.address ?:""
            val paymentMethods = oldStateOrder.statusToBeUpdated?.paymentMethods ?:""
            val price = oldStateOrder.statusToBeUpdated?.price ?: 0
            val quantity = oldStateOrder.statusToBeUpdated?.quantity ?: 0
            val status = oldStateOrder.currentStatus
            val total = oldStateOrder.statusToBeUpdated?.total ?: 0
            val orderToBeUpdated = oldStateOrder.statusToBeUpdated

            when (
                val result = orderRepository.updateStatus(
                    userID = userID,
                    code = code,
                    imageOrder = imageOrder,
                    titleOrder = title,
                    address = address,
                    paymentMethods = paymentMethods,
                    price = price,
                    quantity = quantity,
                    status = status,
                    total = total,
                    orderId = orderToBeUpdated?.orderId ?: "",
                )
            ) {
                is Result.Failure -> {
                    setState(oldStateOrder.copy(isLoading = false))

                    val errorMessage =
                        result.exception.message ?: "An error occurred when updating task"
                    setEffect { OrderScreenSideEffects.ShowSnackBarMessage(messageOrder = errorMessage) }
                }

                is Result.Success -> {
                    setState(
                        oldStateOrder.copy(
                            isLoading = false,
                            imgUrlOrder = "",
                        ),
                    )


                    setEffect { OrderScreenSideEffects.ShowSnackBarMessage(messageOrder = "Status updated successfully") }

                    sendEvent(OrderScreenUiEvent.GetOrder)
                }
            }
        }
    }
    private fun onChangeUserID(oldStateOrder: OrderScreenUiState, userID: String) {
        setState(oldStateOrder.copy(currentUserID = userID))
    }
    private fun onChangeFoodCode(oldStateOrder: OrderScreenUiState, code: String) {
        setState(oldStateOrder.copy(currentCode = code))
    }
    private fun onChangeOrderAddress(oldStateOrder: OrderScreenUiState, address: String) {
        setState(oldStateOrder.copy(currentAddressOrder = address))
    }

    private fun onChangeOrderPayment(oldStateOrder: OrderScreenUiState, paymentMethods: String) {
        setState(oldStateOrder.copy(currentPaymentOrder = paymentMethods))
    }

    private fun onChangeOrderQuantity(oldStateOrder: OrderScreenUiState, quantity: Int) {
        setState(oldStateOrder.copy(currentQuantityOrder = quantity))
    }
    private fun onChangeOrderTotal(oldStateOrder: OrderScreenUiState, total: Int) {
        setState(oldStateOrder.copy(total = total))
    }

    private fun onChangeOrderPrice(oldStateOrder: OrderScreenUiState, price: Int) {
        setState(oldStateOrder.copy(currentPriceOrder = price))
    }

    private fun onChangeOrderTitle(oldStateOrder: OrderScreenUiState, title: String) {
        setState(oldStateOrder.copy(currentTitle = title))
    }

    private fun onChangeOrderImageOrder(oldStateOrder: OrderScreenUiState, imageOrder: String) {
        setState(oldStateOrder.copy(imgUrlOrder = imageOrder))
    }
    private fun onChangeOrderStatus(oldStateOrder: OrderScreenUiState, status: String) {
        setState(oldStateOrder.copy(currentStatus = status))
    }
    private fun setStatusToBeUpdated(oldStateOrder: OrderScreenUiState, order: Order) {
        setState(oldStateOrder.copy(statusToBeUpdated = order))
    }
    private fun onChangeDialog(oldStateOrder: OrderScreenUiState, isShown: Boolean) {
        setState(oldStateOrder.copy(isShowDialog = isShown))
    }
}
