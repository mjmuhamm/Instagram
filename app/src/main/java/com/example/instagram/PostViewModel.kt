package com.example.instagram

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository = PostRepository()) : ViewModel() {

    private val _state : MutableStateFlow<List<Post>> = MutableStateFlow(emptyList())
    val state = _state.asStateFlow()



    init {
        getInfo()
    }

    fun getInfo() {
        viewModelScope.launch {
            _state.value = repository.getPosts(50)
        }
    }

    fun updateItem(index: Int) {
        _state.update { list ->
            list.mapIndexed { i, item ->
                if (i == index) {
                    item.copy(isLiked = !item.isLiked)
                } else {
                    item
                }
            }
        }
    }
}