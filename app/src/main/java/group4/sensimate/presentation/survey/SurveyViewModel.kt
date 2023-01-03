package group4.sensimate.presentation.survey

import android.content.Context
import android.os.Environment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.*
import group4.sensimate.data.model.Question
import group4.sensimate.data.repository.SurveyData
import group4.sensimate.data.repository.SurveyRepository
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class SurveyViewModel (
    private val surveyRepository: SurveyRepository,
): ViewModel()
{
    private val _surveyState = MutableLiveData<SurveyState>()
    val surveyState: LiveData<SurveyState> get() = _surveyState
    private lateinit var surveyInitialState: SurveyState

    init {
        viewModelScope.launch {
            val survey = surveyRepository.getSurvey()

            val questions:List<QuestionState> = survey.questions.mapIndexed { index, question ->
                val showPrevious = index > 0
                val showDone = index == survey.questions.size - 1
                QuestionState(
                    question = question,
                    questionIndex = index,
                    totalQuestionsCount = survey.questions.size,
                    showPrevious = showPrevious,
                    showDone = showDone
                )
            }

            surveyInitialState = SurveyState.Questions(survey.id, questions)
            _surveyState.value = surveyInitialState
        }
    }


    fun computeResult(surveyQuestions: SurveyState.Questions, filePath:File) {
        FileOutputStream(filePath).apply { exportResult(surveyQuestions) }
        val answers = surveyQuestions.questionsState.mapNotNull { it.answer }
        val result = surveyRepository.getSurveyResult(answers)
        _surveyState.value = SurveyState.Result(surveyQuestions.surveyId, result)
    }


    private fun OutputStream.exportResult(question: SurveyState.Questions) {
        val writer = bufferedWriter()

        writer.write(""""SurveyId","Id","question","answer"""")
        writer.newLine()
        writer.write("${question.surveyId}")
        writer.newLine()
        question.questionsState.forEach{
            writer.write("${it.questionIndex},${it.question},\"${it.answer}")
            writer.newLine()
        }
        writer.flush()
    }

    private fun getLatestQuestionId(): Int? {
        val latestState = _surveyState.value
        if (latestState != null && latestState is SurveyState.Questions) {
            return latestState.questionsState[latestState.currentQuestionIndex].question.id
        }
        return null
    }

}



class SurveyViewModelFactory() : ViewModelProvider.Factory
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SurveyViewModel::class.java)) {
            return SurveyViewModel(SurveyData) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}