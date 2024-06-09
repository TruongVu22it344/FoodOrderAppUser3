package com.breens.orderfoodapp.feature_tasks.viewmodel // ktlint-disable package-name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breens.orderfoodapp.common.Result
import com.breens.orderfoodapp.data.repositories.Repository
import com.breens.orderfoodapp.feature_tasks.events.TasksScreenUiEvent
import com.breens.orderfoodapp.feature_tasks.side_effects.TaskScreenSideEffects
import com.breens.orderfoodapp.feature_tasks.state.TasksScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(private val taskRepository: Repository) : ViewModel() {

    private val _state: MutableStateFlow<TasksScreenUiState> =
        MutableStateFlow(TasksScreenUiState())
    val state: StateFlow<TasksScreenUiState> = _state.asStateFlow()

    private val _effect: Channel<TaskScreenSideEffects> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        sendEvent(TasksScreenUiEvent.GetTasks(query = ""))
    }


    fun sendEvent(event: TasksScreenUiEvent) {
        reduce(oldState = _state.value, event = event)
    }

    private fun setEffect(builder: () -> TaskScreenSideEffects) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    private fun setState(newState: TasksScreenUiState) {
        _state.value = newState
    }




    private fun reduce(oldState: TasksScreenUiState, event: TasksScreenUiEvent) {
        when (event) {
            is TasksScreenUiEvent.GetTasks -> {
                getTasks(oldState = oldState, query = event.query)
            }
           is TasksScreenUiEvent.OnChangeTaskImage-> {
                onChangeTaskImage(oldState = oldState, image = event.image)
            }
            is TasksScreenUiEvent.OnChangeTaskBody -> {
                onChangeTaskBody(oldState = oldState, body = event.body)
            }
            is TasksScreenUiEvent.OnChangeTaskPrice-> {
                onChangeTaskPrice(oldState = oldState, price = event.price)
            }
            is TasksScreenUiEvent.OnChangeTaskTitle -> {
                onChangeTaskTitle(oldState = oldState, title = event.title)
            }


        }
    }

    private fun getTasks(oldState: TasksScreenUiState,query: String) {
        viewModelScope.launch {
            setState(oldState.copy(isLoading = true))

            when (val result = taskRepository.getAllTasks(query)) {
                is Result.Failure -> {
                    setState(oldState.copy(isLoading = false))

                    val errorMessage =
                        result.exception.message ?: "An error occurred when getting your task"
                    setEffect { TaskScreenSideEffects.ShowSnackBarMessage(message = errorMessage) }
                }

                is Result.Success -> {
                    val tasks = result.data
                    setState(oldState.copy(isLoading = false, tasks = tasks))
                }
            }
        }
    }

    private fun onChangeTaskImage(oldState: TasksScreenUiState, image: String) {
        setState(oldState.copy(imgUrl = image))
    }

    private fun onChangeTaskBody(oldState: TasksScreenUiState, body: String) {
        setState(oldState.copy(currentTextFieldBody = body))
    }

    private fun onChangeTaskTitle(oldState: TasksScreenUiState, title: String) {
        setState(oldState.copy(currentTextFieldTitle = title))
    }
    private fun onChangeTaskPrice(oldState: TasksScreenUiState, price: Int) {
        setState(oldState.copy(currentTextFieldPrice = price))
    }

}
