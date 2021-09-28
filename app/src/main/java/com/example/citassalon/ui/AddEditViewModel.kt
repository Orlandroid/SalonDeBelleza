package com.example.citassalon.ui


import android.widget.CompoundButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citassalon.data.room.StaffRepository
import kotlinx.coroutines.launch

class AddEditViewModel(private val staffRepository: StaffRepository): ViewModel() {


    private var _staffDone = MutableLiveData<Boolean>(false)
    val staffDone = _staffDone
    var isWorking = false

    var staffName: String? = null

    fun setStaffName(s: CharSequence, start:Int, before: Int, count:Int){
        staffName = s.toString()
    }

    fun setIsWorking(button: CompoundButton,value: Boolean){
        isWorking = value
    }

    fun newStaff() = viewModelScope.launch{
        if ( !staffName.isNullOrBlank()){
            //val staff = StaffName(staffName = staffName!!, isWorking = isWorking)

            //staffRepository.addStaff(staff)

            _staffDone.value = true
        }
    }



}

