package com.breens.orderfoodapp.feature_tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breens.orderfoodapp.data.ProductsRepository
import com.breens.orderfoodapp.data.model.DataX
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
class PayViewModel(
    private val productsRepository: ProductsRepository
): ViewModel() {


    private val _products = MutableStateFlow<List<DataX>>(emptyList())
    val products: StateFlow<List<DataX>> = _products.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            while (true) {
                delay(5000) // Thực hiện cập nhật mỗi 5 giây
                updateProducts()
            }
        }

    }
    private suspend fun updateProducts() {
        productsRepository.getProductsList().collectLatest { result ->
            when(result) {
                is com.breens.orderfoodapp.data.Result.Error -> {
                    _showErrorToastChannel.send(true)
                }
                is com.breens.orderfoodapp.data.Result.Success -> {
                    result.data?.let { products ->
                        _products.update { products }

                    }
                }
            }
        }
    }

}