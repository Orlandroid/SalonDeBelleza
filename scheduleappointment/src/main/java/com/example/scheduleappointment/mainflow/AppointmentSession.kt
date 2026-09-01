package com.example.scheduleappointment.mainflow

import com.example.core.util.dateFormat
import com.example.core.util.getCurrentDateTime
import com.example.core.util.getInitialTime
import com.example.core.util.toStringFormat
import com.example.domain.entities.remote.migration.NegoInfo
import com.example.domain.entities.remote.migration.Service
import com.example.domain.entities.remote.migration.Staff
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton


data class AppointmentDraft(
    val branch: NegoInfo? = null,
    val staff: Staff? = null,
    val service: Service? = null,
    val date: String = getCurrentDateTime().toStringFormat(dateFormat),
    val time: String = getInitialTime()
)

@Singleton
class AppointmentSession  @Inject constructor(){

    private val _draft = MutableStateFlow(AppointmentDraft())

    val draft = _draft.asStateFlow()

    fun selectBranch(branch: NegoInfo) {
        _draft.update {
            it.copy(
                branch = branch, staff = null, service = null
            )
        }
    }

    fun selectStaff(staff: Staff) {
        _draft.update {
            it.copy(
                staff = staff, service = null
            )
        }
    }

    fun selectService(service: Service) {
        _draft.update {
            it.copy(
                service = service
            )
        }
    }

    fun selectDate(date: String) {
        _draft.update {
            it.copy(date = date)
        }
    }

    fun selectTime(time: String) {
        _draft.update {
            it.copy(time = time)
        }
    }

    fun clear() {
        _draft.value = AppointmentDraft()
    }
}