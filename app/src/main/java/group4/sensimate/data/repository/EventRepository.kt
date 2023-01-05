package group4.sensimate.data.repository

import group4.sensimate.R
import group4.sensimate.data.model.Event
import group4.sensimate.data.model.PossibleAnswer
import group4.sensimate.data.model.Question
import group4.sensimate.data.model.Survey


class EventRepository(){
    private var events= mutableListOf<Event>(
        Event(1, R.drawable.wine, "Wine", "lets celebrate", "1-1-22", "2-1-22"),
        Event(2, R.drawable.beer, "Beer", "lets celebrate", "1-1-22", "2-1-22"),
        Event(3, R.drawable.champagne, "Champagne", "lets celebrate", "1-1-22", "2-1-22"),
        Event(4, R.drawable.sandwich, "Sandwich", "lets celebrate", "1-1-22", "2-1-22"),
        Event(5, R.drawable.catering, "Catering", "lets celebrate", "1-1-22", "2-1-22"),
        Event(6, R.drawable.cake, "Cake", "lets celebrate", "1-1-22", "2-1-22")
    )
    fun getEvents(): List<Event>{
        return  events
    }

    fun createEvent(event: Event): Boolean{
        try {
            events.add(event)
            return true
        }catch (ex: Exception){
            return false
        }
    }
}




