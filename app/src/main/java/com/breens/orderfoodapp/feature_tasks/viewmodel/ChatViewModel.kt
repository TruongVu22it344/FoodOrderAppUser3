package com.breens.orderfoodapp.feature_tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breens.orderfoodapp.common.Result
import com.breens.orderfoodapp.data.repositories.Repository
import com.breens.orderfoodapp.feature_tasks.events.ChatScreenUiEvent
import com.breens.orderfoodapp.feature_tasks.side_effects.ChatScreenSideEffects
import com.breens.orderfoodapp.feature_tasks.state.ChatScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val chatRepository: Repository) : ViewModel() {

    private val _stateMessage: MutableStateFlow<ChatScreenUiState> =
        MutableStateFlow(ChatScreenUiState())
    val stateMessage: StateFlow<ChatScreenUiState> = _stateMessage.asStateFlow()

    private val _effectMessage: Channel<ChatScreenSideEffects> = Channel()
    val effectMessage = _effectMessage.receiveAsFlow()

    init {
        sendEvent(ChatScreenUiEvent.GetMessage)
    }


    fun sendEvent(event: ChatScreenUiEvent) {
        reduce(oldState= _stateMessage.value, event = event)
    }

    private fun setEffect(builder: () -> ChatScreenSideEffects) {
        val effectValue = builder()
        viewModelScope.launch { _effectMessage.send(effectValue) }
    }

    private fun setState(newStateOrder: ChatScreenUiState) {
        _stateMessage.value = newStateOrder
    }




    private fun reduce(oldState: ChatScreenUiState, event: ChatScreenUiEvent) {
        when (event) {
            is ChatScreenUiEvent.AddMessage -> {
                addMessage(oldState = oldState,senderID= event.senderID, message = event.message, direction = event.direction)
            }



            ChatScreenUiEvent.GetMessage -> {
                getMessage( oldState = oldState)
            }
            is ChatScreenUiEvent.OnChangeDirection-> {
                onChangeDirection(oldState = oldState, direction = event.direction)
            }

            is ChatScreenUiEvent.OnChangeSenderID-> {
                onChangeSenderID(oldState = oldState, senderID = event.senderID)
            }
            is ChatScreenUiEvent.OnChangeMessage -> {
                onChangeMessageSender(oldState = oldState, message = event.message)
            }
            is ChatScreenUiEvent.OnChangeReceiveID -> {
                onChangeReceiveID(oldState = oldState, receiveID = event.receiveID)
            }



        }
    }



    private fun addMessage( senderID: String, message: String, direction : Boolean, oldState: ChatScreenUiState) {
        viewModelScope.launch {
            setState(oldState.copy(isLoading = true))

            when (val resultOrder = chatRepository.addMessage(senderID = senderID, message = message,  direction = direction)) {
                is Result.Failure -> {
                    setState(oldState.copy(isLoading = false))

                    val errorMessage =
                        resultOrder.exception.message ?: "An error occurred when adding task"
                    setEffect { ChatScreenSideEffects.ShowSnackBarMessage(messageChat = errorMessage) }
                }

                is Result.Success -> {
                    setState(
                        oldState.copy(
                            isLoading = false,
                            bitmap = null,
                            currentMessage = ""
                        ),
                    )

                    sendEvent(ChatScreenUiEvent.GetMessage)

                    setEffect { ChatScreenSideEffects.ShowSnackBarMessage(messageChat = "Gửi tin nhắn thành công!") }
                }
            }
        }
    }

    private fun getMessage(oldState: ChatScreenUiState) {
        viewModelScope.launch {
            setState(oldState.copy(isLoading = true))
            when (val resultOrder = chatRepository.getAllMessage()) {
                is Result.Failure -> {
                    setState(oldState.copy(isLoading = false))

                    val errorMessage =
                        resultOrder.exception.message ?: "An error occurred when getting your orders"
                    setEffect { ChatScreenSideEffects.ShowSnackBarMessage(messageChat = errorMessage) }
                }

                is Result.Success -> {
                    val messages = resultOrder.data
                    setState(oldState.copy(isLoading = false, messages = messages))
                }
            }
        }
    }
    private fun onChangeDirection(oldState: ChatScreenUiState, direction: Boolean) {
        setState(oldState.copy(direction = direction))
    }
    private fun onChangeSenderID(oldState: ChatScreenUiState, senderID: String) {
        setState(oldState.copy(currentSenderID = senderID))
    }

    private fun onChangeReceiveID(oldState: ChatScreenUiState, receiveID: String) {
        setState(oldState.copy(currentReceiveID = receiveID))
    }

    private fun onChangeMessageSender(oldState: ChatScreenUiState, message: String) {
        setState(oldState.copy(currentMessage = message))
    }
}