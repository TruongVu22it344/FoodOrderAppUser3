package com.breens.orderfoodapp.feature_tasks.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breens.orderfoodapp.common.Result
import com.breens.orderfoodapp.data.repositories.Repository


import com.breens.orderfoodapp.feature_tasks.events.BannerScreenUiEvent
import com.breens.orderfoodapp.feature_tasks.side_effects.BannerScreenSideEffects

import com.breens.orderfoodapp.feature_tasks.state.BannerScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BannerViewModel @Inject constructor(private val bannerRepository: Repository) : ViewModel() {

    private val _state1: MutableStateFlow<BannerScreenUiState> =
        MutableStateFlow(BannerScreenUiState())
    val state1: StateFlow<BannerScreenUiState> = _state1.asStateFlow()

    private val _effect1: Channel<BannerScreenSideEffects> = Channel()
    val effect1 = _effect1.receiveAsFlow()

    init {
        sendEvent(BannerScreenUiEvent.GetBanner)
    }

    fun sendEvent(event: BannerScreenUiEvent) {
        reduce(oldState1 = _state1.value, event = event)
    }

    private fun setEffect(builder: () -> BannerScreenSideEffects) {
        val effectValue = builder()
        viewModelScope.launch { _effect1.send(effectValue) }
    }

    private fun setState(newState: BannerScreenUiState) {
        _state1.value = newState
    }

    private fun reduce(oldState1: BannerScreenUiState, event: BannerScreenUiEvent) {
        when (event) {
            BannerScreenUiEvent.GetBanner -> {
                getBanner(oldState1 = oldState1)
            }

            is BannerScreenUiEvent.OnChangeBannerImage-> {
                onChangeBannerImage(oldState1 = oldState1, imageBanner = event.imageBanner)
            }


            is BannerScreenUiEvent.OnChangeBannerTitle -> {
                onChangeBannerTitle(oldState1 = oldState1, titleBanner = event.titleBanner)
            }

        }
    }

    private fun getBanner(oldState1: BannerScreenUiState) {
        viewModelScope.launch {
            setState(oldState1.copy(isLoadingBanner = true))

            when (val result1 = bannerRepository.getAllBanner()) {
                is Result.Failure -> {
                    setState(oldState1.copy(isLoadingBanner = false))

                    val errorMessage =
                        result1.exception.message ?: "An error occurred when getting your banner"
                    setEffect { BannerScreenSideEffects.ShowSnackBarMessage(messageBanner = errorMessage) }
                }

                is Result.Success -> {
                    val banners = result1.data
                    setState(oldState1.copy(isLoadingBanner = false, banners = banners))
                }
            }
        }
    }

    private fun onChangeBannerImage(oldState1: BannerScreenUiState, imageBanner: String) {
        setState(oldState1.copy(imgUrlBanner = imageBanner))
    }

    private fun onChangeBannerTitle(oldState1: BannerScreenUiState, titleBanner: String) {
        setState(oldState1.copy(currentTextFieldTitleBanner = titleBanner))
    }

}

