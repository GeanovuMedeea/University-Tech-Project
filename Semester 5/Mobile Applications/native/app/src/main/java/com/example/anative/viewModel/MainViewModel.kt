package com.example.anative.viewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.anative.domain.Item
import com.example.anative.repository.Repository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import logd

class MainViewModel : ViewModel() {

    private val repository = Repository()
    private val itemList = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> get() = itemList

    init {
        logd("MainViewModel created")
        itemList.value = repository.getItems()
    }

    fun addItem(item: Item) {
        repository.addItem(item)
        itemList.value = repository.getItems()
    }

    fun deleteItem(itemId: Int)
    {
        repository.deleteItem(itemId)
        itemList.value = repository.getItems()
    }

    fun updateItem(item: Item)
    {
        repository.updateItem(item)
        itemList.value = repository.getItems()
    }

    fun getItemById(itemId: Int): Item? {
        return repository.getItemById(itemId)
    }

    fun getItemsCount(): Int {
        return repository.getItemsCount()
    }

    fun getNextId(): Int {
        return repository.getNextId()
    }
}
