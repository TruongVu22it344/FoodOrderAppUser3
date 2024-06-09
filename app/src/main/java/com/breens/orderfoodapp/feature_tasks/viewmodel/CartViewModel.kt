package com.breens.orderfoodapp.feature_tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breens.orderfoodapp.data.repositories.Repository
import com.breens.orderfoodapp.feature_tasks.events.CartsScreenUiEvent
import com.breens.orderfoodapp.feature_tasks.side_effects.CartScreenSideEffects
import com.breens.orderfoodapp.feature_tasks.state.CartsScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartRepository: Repository) : ViewModel() {

    private val _state: MutableStateFlow<CartsScreenUiState> = MutableStateFlow(CartsScreenUiState())
    val state: StateFlow<CartsScreenUiState> = _state.asStateFlow()

    private val _effect: Channel<CartScreenSideEffects> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        sendEvent(CartsScreenUiEvent.GetCarts)
    }


    fun sendEvent(event: CartsScreenUiEvent) {
        reduce(oldState = _state.value, event = event)
    }

    private fun setEffect(builder: () -> CartScreenSideEffects) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    private fun setState(newState: CartsScreenUiState) {
        _state.value = newState
    }




    private fun reduce(oldState: CartsScreenUiState, event: CartsScreenUiEvent) {
        when (event) {

            is CartsScreenUiEvent.DeleteNote -> {
                deleteNote(oldState = oldState, foodId = event.foodId)
            }

            CartsScreenUiEvent.GetCarts -> {
                getCarts(oldState = oldState)
            }



            is CartsScreenUiEvent.OnChangeTaskImage-> {
                onChangeTaskImage(oldState = oldState, image = event.image)
            }
            is CartsScreenUiEvent.OnChangeTaskBody -> {
                onChangeTaskBody(oldState = oldState, body = event.body)
            }

            is CartsScreenUiEvent.OnChangeTaskTitle -> {
                onChangeTaskTitle(oldState = oldState, title = event.title)
            }
            is CartsScreenUiEvent.OnChangeTaskPrice -> {
                onChangeTaskPrice(oldState = oldState, price = event.price)
            }
            is CartsScreenUiEvent.OnChangeTaskQuantity -> {
                onChangeTaskQuantity(oldState = oldState, quantity = event.quantity)
            }


        }
    }

    /*private fun addCart(image: String,title: String, body: String,price: Int, oldState: CartsScreenUiState) {
        viewModelScope.launch {
            setState(oldState.copy(isLoading = true))

            when (val result = cartRepository.addCart(image = image, title = title, body = body, price = price)) {
                is com.breens.orderfood.common.Result.Failure -> {
                    setState(oldState.copy(isLoading = false))

                    val errorMessage =
                        result.exception.message ?: "An error occurred when adding task"
                    setEffect { CartScreenSideEffects.ShowSnackBarMessage(message = errorMessage) }
                }

                is com.breens.orderfood.common.Result.Success -> {
                    setState(
                        oldState.copy(
                            isLoading = false,
                            bitmap = null,
                            currentTextFieldTitle = "",
                            currentTextFieldBody = "",
                            currentTextFieldPrice = 0,
                        ),
                    )


                    sendEvent(CartsScreenUiEvent.GetCarts)

                    setEffect { CartScreenSideEffects.ShowSnackBarMessage(message = "Task added successfully") }
                }
            }
        }
    }*/

    private fun getCarts(oldState: CartsScreenUiState) {
        viewModelScope.launch {
            setState(oldState.copy(isLoading = true))

            when (val result = cartRepository.getAllCarts()) {
                is com.breens.orderfoodapp.common.Result.Failure -> {
                    setState(oldState.copy(isLoading = false))

                    val errorMessage =
                        result.exception.message ?: "An error occurred when getting your task"
                    setEffect { CartScreenSideEffects.ShowSnackBarMessage(message = errorMessage) }
                }

                is com.breens.orderfoodapp.common.Result.Success -> {
                    val carts = result.data
                    setState(oldState.copy(isLoading = false, carts = carts))
                }
            }
        }
    }

    private fun deleteNote(oldState: CartsScreenUiState, foodId: String) {
        viewModelScope.launch {
            setState(oldState.copy(isLoading = true))

            when (val result = cartRepository.deleteCart(foodId = foodId)) {
                is com.breens.orderfoodapp.common.Result.Failure -> {
                    setState(oldState.copy(isLoading = false))

                    val errorMessage =
                        result.exception.message ?: "An error occurred when deleting task"
                    setEffect { CartScreenSideEffects.ShowSnackBarMessage(message = errorMessage) }
                }

                is com.breens.orderfoodapp.common.Result.Success -> {
                    setState(oldState.copy(isLoading = false))

                    setEffect { CartScreenSideEffects.ShowSnackBarMessage(message = "Deleted successfully") }

                    sendEvent(CartsScreenUiEvent.GetCarts)
                }
            }
        }
    }



    private fun onChangeTaskImage(oldState: CartsScreenUiState, image: String) {
        setState(oldState.copy(imgUrl = image))
    }

    private fun onChangeTaskBody(oldState: CartsScreenUiState, body: String) {
        setState(oldState.copy(currentTextFieldBody = body))
    }

    private fun onChangeTaskTitle(oldState: CartsScreenUiState, title: String) {
        setState(oldState.copy(currentTextFieldTitle = title))
    }

    private fun onChangeTaskPrice(oldState: CartsScreenUiState, price: Int) {
        setState(oldState.copy(currentTextFieldPrice = price))
    }
    private fun onChangeTaskQuantity(oldState: CartsScreenUiState, quantity: Int) {
        setState(oldState.copy(currentTextFieldQuantity = quantity))
    }


}