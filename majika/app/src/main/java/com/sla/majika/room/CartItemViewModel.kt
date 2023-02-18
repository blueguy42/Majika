package com.sla.majika.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.util.Currency

class CartItemViewModel(private val repository: CartItemRepository) : ViewModel() {

    val allItems: LiveData<List<CartItem>> = repository.allCartItems.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */

    fun getAll() : List<CartItem>{
        return repository.getAll()
    }

    fun getHargaTotal() : List<CurrencyPrice>{
        return repository.getHargaTotal()
    }

    fun insert(item: CartItem) = viewModelScope.launch {
        repository.insert(item)
    }

    fun update(item: CartItem) = viewModelScope.launch {
        repository.update(item)
    }

    fun delete(item: CartItem) = viewModelScope.launch {
        repository.delete(item)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
}

class CartItemViewModelFactory(private val repository: CartItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}