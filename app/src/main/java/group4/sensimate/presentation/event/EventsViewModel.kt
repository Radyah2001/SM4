package group4.sensimate.presentation.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import group4.sensimate.data.model.Event
import group4.sensimate.data.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EventsViewModel(
    private val eventRepository: EventRepository = EventRepository()
): ViewModel(){

    private var _eventsState = MutableStateFlow(listOf(Event()))
    val eventsState: StateFlow<List<Event>> = _eventsState.asStateFlow()

    init {
        getEvents()
    }

    fun getEvents(){
        val respond = eventRepository.getEvents()
        if(respond.isNotEmpty()){
            _eventsState.value= respond
        }
    }

    fun createEvent(): Boolean{
        return true
    }

    var name by  mutableStateOf("")
        private set
    var location by mutableStateOf("")
        private set
    var date by  mutableStateOf("")
        private set
    var time by mutableStateOf("")
        private set
    var survey by mutableStateOf("")
        private set
    var link by mutableStateOf("")
        private set

    fun nameChange(newValue: String){
        name= newValue
    }

    fun locationChange(newValue: String){
        location= newValue
    }

    fun dateChange(newValue: String){
        date= newValue
    }

    fun timeChange(newValue: String){
        time= newValue
    }

    fun surveyChange(newValue: String){
        survey= newValue
    }

    fun linkChange(newValue: String){
        link= newValue
    }
}