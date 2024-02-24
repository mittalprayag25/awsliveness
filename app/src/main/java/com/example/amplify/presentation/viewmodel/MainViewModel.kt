package com.example.amplify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raq.motori.android.customerapp.base.ApiState
import com.raq.motori.android.customerapp.home.domain.model.Product
import com.raq.motori.android.customerapp.home.domain.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Manoj Sain on 20/02/24.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ApiRepository
):ViewModel() {

    private val _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    fun getCameraAccess(){
        _viewState.update {
            it.copy(true)
        }
    }

    /**
     *  fetch api
     */

    fun fetchData(){
        viewModelScope.launch {

            repository.getProducts().collect{ apiState ->
                when(apiState){
                    is ApiState.Loading -> _viewState.update {
                        it.copy(isLoading = true)
                    }
                    is ApiState.Success ->_viewState.update {
                        it.copy(data = apiState.data)
                    }
                    is ApiState.Error ->_viewState.update {
                        it.copy(error = apiState.errorMessage)
                    }
                }

            }

            // dismissing loader
            _viewState.update {
                it.copy(isLoading = false)
            }

        }
    }


    /**
     * Todo: 
     *  post data api
     */

    fun postData(product: Product){
        viewModelScope.launch {

            repository.postData(product).collect{ apiState ->
                when(apiState){
                    is ApiState.Loading -> _viewState.update {
                        it.copy(isLoading = true)
                    }
                    is ApiState.Success ->_viewState.update {
                        it.copy(data = apiState.data)
                    }
                    is ApiState.Error ->_viewState.update {
                        it.copy(error = apiState.errorMessage)
                    }
                }

            }

            // dismissing loader
            _viewState.update {
                it.copy(isLoading = false)
            }

        }
    }

}

data class ViewState(
    val isLoading:Boolean = false,
    val data:List<Product> = emptyList(), //Todo:  change as per you data response
    val postDataResponse:List<Product> = emptyList(), //Todo:  change as per you data response
    val error:String? = null,
    val isCameraAccessGranted:Boolean = false
)