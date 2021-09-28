package com.example.citassalon.ui.staff

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.R
import com.example.citassalon.data.models.Staff
import com.example.citassalon.data.room.StaffRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelStaff @Inject constructor(private val repository: StaffRepository) : ViewModel() {

    private val _staffs = MutableLiveData<List<Staff>>()
    val staffs: LiveData<List<Staff>>
        get() = _staffs

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val listaStaff = repository.getAllStaff()
            _staffs.postValue(listaStaff)
        }
    }

    private fun prepopulateStaff() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addManyStaff(
                listOf(
                    Staff(0, R.drawable.image_15, "Angela Bautista", 1f),
                    Staff(0, R.drawable.image_18, "Xavier Cruz", 4f),
                    Staff(0, R.drawable.image_19, "Flora Parra", 3f),
                    Staff(0, R.drawable.image_20, "Jesica Estrada", 5f),
                )
            )
        }
    }
}