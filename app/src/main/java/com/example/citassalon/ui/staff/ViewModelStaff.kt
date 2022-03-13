package com.example.citassalon.ui.staff


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.models.rickandmorty.Character
import com.example.citassalon.data.repository.Repository
import com.example.citassalon.data.state.ApiState
import com.example.citassalon.util.NetworkHelper
import com.example.citassalon.util.getRandomPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelStaff @Inject constructor(
    private val repository: Repository,
    private val networkHelper: NetworkHelper
) :
    ViewModel() {

    private val _staff = MutableLiveData<ApiState<List<Character>>>()
    val staff: MutableLiveData<ApiState<List<Character>>>
        get() = _staff

    init {
        getSttafs()
    }



    fun getSttafs() {
        viewModelScope.launch(Dispatchers.IO) {
            _staff.postValue(ApiState.Loading(null))
            if (!networkHelper.isNetworkConnected()) {
                _staff.postValue(ApiState.ErrorNetwork())
                return@launch
            }
            val response = repository.getCharacters(page = getRandomPage(1,42))
            _staff.postValue(ApiState.Success(response.results))
        }
    }

}