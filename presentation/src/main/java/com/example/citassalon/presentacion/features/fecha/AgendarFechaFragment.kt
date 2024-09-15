package com.example.citassalon.presentacion.features.fecha

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.citassalon.R
import com.example.citassalon.databinding.FragmentGenericBindingBinding
import com.example.citassalon.presentacion.features.base.BaseFragment
import com.example.citassalon.presentacion.features.extensions.navigate
import com.example.citassalon.presentacion.features.extensions.showDatePickerDialog
import com.example.citassalon.presentacion.features.flow_main.FlowMainViewModel
import com.example.citassalon.presentacion.features.theme.Background
import com.example.citassalon.presentacion.main.AlertDialogs
import com.example.citassalon.presentacion.main.AlertDialogs.Companion.WARNING_MESSAGE
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.time.Duration.Companion.hours

class AgendarFechaFragment :
    BaseFragment<FragmentGenericBindingBinding>(R.layout.fragment_generic_binding),
    DatePickerDialog.OnDateSetListener {

//    private val flowMainViewModel by navGraphViewModels<FlowMainViewModel>(R.id.main_navigation) {
//        defaultViewModelProviderFactory
//    }
//
//    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
//        showToolbar = true, toolbarTitle = getString(R.string.agendar_hora)
//    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    override fun setUpUi() {/*
        with(binding) {
            edHora.setEndIconOnClickListener {
                showTimePickerDialog()
            }
            mainView.click {
                hideKeyboard()
            }
            etFecha.setEndIconOnClickListener {
                showDatePickerDialog(getListenerOnDataSet(), this@AgendarFechaFragment, true)
            }
        }
        setValuesToView()
         */
    }

    private fun setValuesToView() {/*
        with(binding) {
            flowMainViewModel.currentStaff.let {
                Glide.with(requireActivity()).load(it.image_url).into(imgStaff)
                tvStaffName.text = it.nombre
            }
            flowMainViewModel.let {
                textSucursal.text = it.sucursal.name
                val service = it.listOfServices[0]
                txtServicio.text = service.name
                tvServicioPrecio.text = service.precio.toString()
            }
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
//                ScheduleAppointmentScreen(flowMainViewModel)
            }
        }
    }

    @Composable
    @Preview(showBackground = true)
    fun ScheduleAppointmentScreenPreview() {
//        ScheduleAppointmentScreen(flowMainViewModel)
    }

    private val dateFormat = "dd/MM/yyyy"
    private val timeFormat = "h:mm"

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    private fun Date.toStringFormat(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getInitialTime(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val min = Calendar.getInstance().get(Calendar.MINUTE)
        return "$hour:$min hrs"
    }

    @Composable
    fun ScheduleAppointmentScreen(flowMainViewModel: FlowMainViewModel) {
        var date: String by remember { mutableStateOf(getCurrentDateTime().toStringFormat(dateFormat)) }
        var myTime: String by remember { mutableStateOf(getInitialTime()) }
        flowMainViewModel.hourAppointment = myTime
        flowMainViewModel.dateAppointment = date
        ConstraintLayout(
            Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            val (cardInfoStaff, cardTime) = createRefs()
            ElevatedCard(elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.constrainAs(cardInfoStaff) {
                    top.linkTo(parent.top, 32.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    width = Dimension.fillToConstraints
                }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = flowMainViewModel.currentStaff.image_url,
                        contentDescription = null,
                        modifier = Modifier
                            .height(150.dp)
                            .size(150.dp)
                            .padding(top = 24.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        fontSize = 30.sp, text = flowMainViewModel.currentStaff.nombre
                    )
                    Text(
                        fontSize = 30.sp, text = flowMainViewModel.sucursal.name
                    )
                    Text(
                        fontSize = 30.sp, text = flowMainViewModel.listOfServices[0].name
                    )
                    Text(
                        fontSize = 30.sp,
                        text = flowMainViewModel.listOfServices[0].precio.toString(),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
            OutlinedCard(
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                modifier = Modifier.constrainAs(cardTime) {
                    top.linkTo(cardInfoStaff.bottom, 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }, border = BorderStroke(2.dp, Color.Black)
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        enabled = false,
                        value = date,
                        onValueChange = {

                        },
                        placeholder = {
                            Text(stringResource(id = R.string.add_date))
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    showDatePickerDialog(
                                        listener = { _, year, month, dayOfMonth ->
                                            val calendar = Calendar.getInstance()
                                            calendar[Calendar.YEAR] = year
                                            calendar[Calendar.MONTH] = month
                                            calendar[Calendar.DATE] = dayOfMonth
                                            val dateSelect =
                                                calendar.time.toStringFormat(dateFormat)
                                            date = dateSelect
                                            flowMainViewModel.dateAppointment = dateSelect
                                        },
                                        fragment = this@AgendarFechaFragment
                                    )
                                }
                            )
                        }
                    )
                    TextField(
                        modifier = Modifier.clickable { },
                        enabled = false,
                        value = myTime,
                        onValueChange = {

                        },
                        placeholder = { Text(stringResource(id = R.string.selecciona_la_hora_de_tu_cita)) },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    val timePicker = TimePickerFragment({ time, validTime ->
                                        if (validTime) {
                                            myTime = time
                                            flowMainViewModel.hourAppointment = time
                                            return@TimePickerFragment
                                        }
                                        showDialogRangeOfTime()
                                    })
                                    activity?.let {
                                        timePicker.show(
                                            it.supportFragmentManager,
                                            "timePicker"
                                        )
                                    }
                                }
                            )
                        }
                    )
                    Button(
                        onClick = {
//                            navigate(AgendarFechaFragmentDirections.actionAgendarFechaToAgendarConfirmacion())
                        }
                    ) {
                        Text(text = stringResource(id = R.string.next))
                    }
                }

            }
        }
    }


    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
        val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
        //binding.etFecha.editText?.setText(selectedDate)
//        flowMainViewModel.dateAppointment = selectedDate
        //goToComfirm()
    }

    private fun getListenerOnDataSet(): DatePickerDialog.OnDateSetListener {
        return this
    }


    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment({ time, validTime ->
            if (validTime) {
                onTimeSelected(time)
                //goToComfirm()
                return@TimePickerFragment
            }
            showDialogRangeOfTime()
        })
        activity?.let { timePicker.show(it.supportFragmentManager, "timePicker") }
    }

    private fun showDialogRangeOfTime() {
        val alert = AlertDialogs(
            kindOfMessage = WARNING_MESSAGE,
            messageBody = "Debes de selecionar un horario entre 9am y 6pm"
        )
        activity?.let { alert.show(it.supportFragmentManager, "Dialog") }
    }

    private fun onTimeSelected(time: String) {
        //binding.edHora.editText?.setText(time)
//        flowMainViewModel.hourAppointment = time
    }

    /*

    private fun goToComfirm() {
        if (areNotEmptyTimeOrDate()) {
            navigate(AgendarFechaFragmentDirections.actionAgendarFechaToAgendarConfirmacion())
        }
    }*/

    private fun areNotEmptyTimeOrDate(): Boolean {
        //val date = binding.etFecha.editText?.text.toString().trim()
        //val time = binding.edHora.editText?.text.toString().trim()
        //return date.isNotEmpty() and time.isNotEmpty()
        return false
    }

}