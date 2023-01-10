package group4.sensimate.data.repository

import group4.sensimate.R
import group4.sensimate.data.model.Answer
import group4.sensimate.data.model.PossibleAnswer
import group4.sensimate.data.model.Question
import group4.sensimate.data.model.Survey
import group4.sensimate.presentation.survey.SurveyResult

private val surveyQuestions = mutableListOf(
    Question(1,1,"What time would you like the event to have been hosted?", answer= PossibleAnswer.SingleChoice( listOf<String>( "Morning","Evening","Afternoon"))),
    Question(1,2,"Did the event look good?", answer = PossibleAnswer.MultipleChoice( listOf<String>( "Could be better", "Middle","Great"))),
    Question(1,3,"Did you enjoy the food/drink?", answer = PossibleAnswer.Slider(
        range = 1f..10f, steps = 3, startText = "Strongly dislike",
        endText = "Strongly like",
        neutralText = "Neutral")
    ),
    Question(1,4,"Would you attend the event again?", answer= PossibleAnswer.SingleChoice( listOf<String>( "yes","no"))),
    Question(1,5,"Did you enjoy the event?",answer = PossibleAnswer.SingleChoice(listOf("yes","no"))),

    ).toList()

private val survey = Survey(
    id = 1,
    questions = surveyQuestions
)


object SurveyData : SurveyRepository {

    override fun getSurvey() = survey

    @Suppress("UNUSED_PARAMETER")
    override fun getSurveyResult(answers: List<Answer<*>>): SurveyResult {
        return SurveyResult(
            library = "Done",
            result = R.string.survey_result,
            description = R.string.survey_result_description
        )
    }
}

interface SurveyRepository {
    fun getSurvey(): Survey

    fun getSurveyResult(answers: List<Answer<*>>): SurveyResult
}