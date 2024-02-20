package com.example.amplify

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by Manoj Sain on 20/02/24.
 */
class MainViewModel:ViewModel() {

    private val _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    fun getCameraAccess(){
        _viewState.update {
            it.copy(true)
        }
    }

}

data class ViewState(
    val isCameraAccessGranted:Boolean = false
)