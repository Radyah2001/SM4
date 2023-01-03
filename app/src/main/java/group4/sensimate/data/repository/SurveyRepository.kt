package group4.sensimate.data.repository

import group4.sensimate.R
import group4.sensimate.data.model.Answer
import group4.sensimate.data.model.PossibleAnswer
import group4.sensimate.data.model.Question
import group4.sensimate.data.model.Survey
import group4.sensimate.presentation.survey.SurveyResult

private val surveyQuestions = mutableListOf(
    Question(1,1,"who do like most?", answer= PossibleAnswer.SingleChoice( listOf<String>( "parent","friends","sibling"))),
    Question(1,2,"which sport do you like?", answer = PossibleAnswer.MultipleChoice( listOf<String>( "soccer", "tenis","chess"))),
    Question(1,3,"how do you feel about ux course?", answer = PossibleAnswer.Slider(
        range = 1f..10f, steps = 3, startText = "strongly dislike",
        endText = "strongly like",
        neutralText = "neutral")
    ),
    Question(1,4,"would you attend to the food events?", answer= PossibleAnswer.SingleChoice( listOf<String>( "yes","no"))),

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