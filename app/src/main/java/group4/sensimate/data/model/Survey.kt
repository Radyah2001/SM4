package group4.sensimate.data.model

import androidx.compose.runtime.Immutable

@Immutable
data class Survey(
    val id : Int,
    val questions: List<Question>
)

data class Question(
    val SurveyId: Int,
    val id: Int,
    val text: String,
    val answer: PossibleAnswer
)

sealed class PossibleAnswer {
    data class SingleChoice(val options: List<String>) : PossibleAnswer()
    data class MultipleChoice(val options: List<String>) : PossibleAnswer()
    data class Slider(
        val range: ClosedFloatingPointRange<Float>,
        val steps: Int,
        val startText: String,
        val endText: String,
        val neutralText: String,
        val defaultValue: Float = 5.5f
    ) : PossibleAnswer()
}
sealed class Answer<T : PossibleAnswer> {
    data class SingleChoice(val answer: String) : Answer<PossibleAnswer.SingleChoice>()
    data class MultipleChoice(val answersStringRes: Set<String>) : Answer<PossibleAnswer.MultipleChoice>()
    data class Slider(val answerValue: Float) : Answer<PossibleAnswer.Slider>()
}

fun Answer.MultipleChoice.withAnswerSelected(
    answer: String,
    selected: Boolean
): Answer.MultipleChoice {
    val newStringRes = answersStringRes.toMutableSet()
    if (!selected) {
        newStringRes.remove(answer)
    } else {
        newStringRes.add(answer)
    }
    return Answer.MultipleChoice(newStringRes)
}

