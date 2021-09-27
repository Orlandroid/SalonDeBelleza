package com.example.citassalon.ui.staff.stafflist

import androidx.lifecycle.*
import com.example.citassalon.data.models.room.StaffName
import com.example.citassalon.data.models.room.StaffRepository
import kotlinx.coroutines.launch

class StaffListViewModel(private val staffRepository: StaffRepository) : ViewModel() {

    val staffList = staffRepository.getStaffs()

    private var _editStaffId = MutableLiveData<Int?>()
    val eventEditStaff = _editStaffId

      /*      init {
                this.prepopulate()
            }*/

    fun removeStaff(staff: StaffName) = viewModelScope.launch{
        staffRepository.removeStaff(staff)
    }

    fun onEdit(staffId: Int){
        eventEditStaff.value = staffId
    }
    suspend fun prepopulate(){

        val staffs = listOf(
            StaffName(staffName = "Sofía Palmira", isWorking = true),
            StaffName(staffName = "Luis García", isWorking = true),
            StaffName(staffName = "Flor Parra", isWorking = true),
            StaffName(staffName = "Lorenzo Melgoza", isWorking = true)
            )
        staffRepository.populateStaffs(staffs)

    }
}