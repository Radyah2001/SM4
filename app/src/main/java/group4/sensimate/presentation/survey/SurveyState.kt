package group4.sensimate.presentation.survey

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import group4.sensimate.data.model.Answer
import group4.sensimate.data.model.Question

@Stable
class QuestionState(
    val question: Question,
    val questionIndex: Int,
    val totalQuestionsCount: Int,
    val showPrevious: Boolean,
    val showDone: Boolean
) {
    var enableNext by mutableStateOf(false)
    var answer by mutableStateOf<Answer<*>?>(null)
}

sealed class SurveyState {
    data class Questions(
        val surveyId: Int,
        val questionsState: List<QuestionState>
    ) : SurveyState() {
        var currentQuestionIndex by mutableStateOf(0)
    }

    data class Result(
        val surveyId: Int,
        val surveyResult: SurveyResult
    ) : SurveyState()
}

data class SurveyResult(
    val library: String,
    val result: Int,
    val description: Int
)
