package com.breens.orderfoodapp.feature_tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breens.orderfoodapp.data.repositories.Repository
import com.breens.orderfoodapp.feature_tasks.events.CatesScreenUiEvent
import com.breens.orderfoodapp.feature_tasks.side_effects.CateScreenSideEffects
import com.breens.orderfoodapp.feature_tasks.state.CatesScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CateViewModel @Inject constructor(private val cateRepository: Repository) : ViewModel() {

    private val _stateCate: MutableStateFlow<CatesScreenUiState> = MutableStateFlow(
        CatesScreenUiState()
    )
    val stateCate: StateFlow<CatesScreenUiState> = _stateCate.asStateFlow()

    private val _effectCate: Channel<CateScreenSideEffects> = Channel()
    val effectCate = _effectCate.receiveAsFlow()

    init {
        sendEvent(CatesScreenUiEvent.GetCates)
    }


    fun sendEvent(event: CatesScreenUiEvent) {
        reduce(oldStateCate = _stateCate.value, event = event)
    }

    private fun setEffect(builder: () -> CateScreenSideEffects) {
        val effectValue = builder()
        viewModelScope.launch { _effectCate.send(effectValue) }
    }

    private fun setState(newState: CatesScreenUiState) {
        _stateCate.value = newState
    }




    private fun reduce(oldStateCate: CatesScreenUiState, event: CatesScreenUiEvent) {
        when (event) {
            CatesScreenUiEvent.GetCates -> {
                getCates(oldStateCate = oldStateCate)
            }

            is CatesScreenUiEvent.OnChangeCateImage-> {
                onChangeCateImage(oldStateCate = oldStateCate, imageCate = event.imageCate)
            }

            is CatesScreenUiEvent.OnChangeCateTitle -> {
                onChangeCateTitle(oldStateCate = oldStateCate, titleCate = event.titleCate)
            }



        }
    }


    private fun getCates(oldStateCate: CatesScreenUiState) {
        viewModelScope.launch {
            setState(oldStateCate.copy(isLoadingCate = true))

            when (val resultCate = cateRepository.getAllCates()) {
                is com.breens.orderfoodapp.common.Result.Failure -> {
                    setState(oldStateCate.copy(isLoadingCate = false))

                    val errorMessage =
                        resultCate.exception.message ?: "An error occurred when getting your banner"
                    setEffect { CateScreenSideEffects.ShowSnackBarMessage(messageCate = errorMessage) }
                }

                is com.breens.orderfoodapp.common.Result.Success -> {

                    val cates = resultCate.data
                    setState(
                        oldStateCate.copy(
                            isLoadingCate = false,
                            cates = cates
                        )
                    )
                }
            }
        }
    }


    private fun onChangeCateImage(
        oldStateCate: CatesScreenUiState,
        imageCate: String
    ) {
        setState(oldStateCate.copy(imgUrlCate = imageCate))
    }

    private fun onChangeCateTitle(
        oldStateCate: CatesScreenUiState,
        titleCate: String
    ) {
        setState(oldStateCate.copy(currentTextFieldTitleCate = titleCate))
    }


}
