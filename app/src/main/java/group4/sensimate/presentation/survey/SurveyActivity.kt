package group4.sensimate.presentation.survey

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.siddroid.holi.brushes.GradientMixer
import group4.sensimate.R
import group4.sensimate.data.model.*
import group4.sensimate.ui.theme.SensiMateTheme

class SurveyActivity : ComponentActivity() {
    val viewModel: SurveyViewModel by viewModels {
        SurveyViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SensiMateTheme {
                val filePath=  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/Surveys.csv")

                val state = viewModel.surveyState.observeAsState().value?:return@SensiMateTheme

                when (state) {
                    is SurveyState.Questions -> SurveyScreen(
                        questions = state,
                        onDonePressed = { viewModel.computeResult(state, filePath) },
                        onBackPressed = {
                            this.onBackPressedDispatcher.onBackPressed()
                        }
                    )
                    is SurveyState.Result -> SurveyResultScreen(
                        result = state,
                        onDonePressed = {
                            this.onBackPressedDispatcher.onBackPressed()
                        }
                    )
                }
            }
        }
    }

}



@Preview(showBackground = true)
@Composable
fun LoadSurveyPreview() {
    SensiMateTheme {
        //SurveyScreen()
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SurveyScreen(
    questions: SurveyState.Questions,
    onDonePressed: () -> Unit,
    onBackPressed: () -> Unit
){
    val questionState = remember(questions.currentQuestionIndex) {
        questions.questionsState[questions.currentQuestionIndex]
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(colorResource(R.color.background))
    ){



        Scaffold(
            backgroundColor = Color.Transparent,
            topBar = {
                SurveyTopBar(
                    questionIndex = questionState.questionIndex,
                    totalQuestionsCount = questionState.totalQuestionsCount,
                    onBackPressed = onBackPressed
                )
            },
            content = {
                QuestionContent(
                    question = questionState.question,
                    answer = questionState.answer,
                    onAnswer = { questionState.enableNext = true },
                    modifier = Modifier.background(Color.Transparent)
                )
            },
            bottomBar = {
                SurveyBottomBar(
                    questionState = questionState,
                    onPreviousPressed = { questions.currentQuestionIndex-- },
                    onNextPressed = { questions.currentQuestionIndex++ },
                    onDonePressed = onDonePressed
                )
            }
        )
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SurveyResultScreen(
    result: SurveyState.Result,
    onDonePressed: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(colorResource(R.color.background))
    ) {

        Scaffold(
            backgroundColor = Color.Transparent,
            content = {
                SurveyResult( result = result)
            },
            bottomBar = {
                OutlinedButton(
                    shape= CircleShape,
                    onClick = { onDonePressed() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 24.dp)
                ) {
                    Text(text = stringResource(id = R.string.done))
                }
            }
        )
    }
}

@Composable
private fun SurveyResult( result: SurveyState.Result )
{
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
    )
    {
        Box(
            modifier = Modifier
                .size(400.dp)
                //.clip(CircleShape)
                .align(alignment = Alignment.Center)
                .background(Color.White.copy(alpha = 0.15f))
        )
        {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                item {
                    Spacer(modifier = Modifier.height(44.dp))
                    Text(
                        text = result.surveyResult.library,
                        color = Color.White,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                    Text(
                        text = stringResource(
                            result.surveyResult.result,
                            result.surveyResult.library
                        ),
                        color = Color.White,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(20.dp)
                    )
                    Text(
                        text = stringResource(
                            result.surveyResult.description,
                            result.surveyResult.library
                        ),
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SurveyTopBar(
    questionIndex: Int,
    totalQuestionsCount: Int,
    onBackPressed: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxWidth()) {

            Row(modifier = Modifier
                .padding(vertical = 20.dp)
                .align(Alignment.Center)
            ) {
                Text( text = (questionIndex + 1).toString(), color = Color.White )
                Text( text = stringResource(R.string.question_count, totalQuestionsCount), color = Color.White )
            }

            IconButton(
                onClick = onBackPressed,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.close),
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }

        val animatedProgress by animateFloatAsState(
            targetValue = (questionIndex + 1) / totalQuestionsCount.toFloat(),
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            progress = animatedProgress,
            backgroundColor= Color.White,
            color = colorResource(id = R.color.violets_blue)
        )
    }
}

@Composable
private fun SurveyBottomBar(
    questionState: QuestionState,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit,
    onDonePressed: () -> Unit
) {
    Row( modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        if (questionState.showPrevious) {
            OutlinedButton(
                onClick = onPreviousPressed,
                shape = CircleShape,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Text(text = stringResource(id = R.string.previous))
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
        if (questionState.showDone) {
            Button(
                onClick = onDonePressed,
                shape = CircleShape,
                enabled = questionState.enableNext,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Text(text = stringResource(id = R.string.done))
            }
        } else {
            Button(
                onClick = onNextPressed,
                shape = CircleShape,
                enabled = questionState.enableNext,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)

            ) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
}



@Composable
private fun QuestionContent(
    question: Question,
    answer: Answer<*>?,
    onAnswer: (Answer<*>) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = question.id.toString() +". " + question.text,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            when(question.answer) {
                is PossibleAnswer.SingleChoice -> RadioButtonQuestion(
                    possibleAnswer = question.answer,
                    answer = answer as Answer.SingleChoice?,
                    onAnswerSelected = { answer -> onAnswer(Answer.SingleChoice(answer)) },
                    modifier = Modifier.fillParentMaxWidth()
                )

                is PossibleAnswer.MultipleChoice -> CheckBoxQuestion(
                    possibleAnswer = question.answer,
                    answer = answer as Answer.MultipleChoice?,
                    onAnswerSelected = { newAnswer, selected ->
                        // create the answer if it doesn't exist or update it based on the user's selection
                        if (answer == null) {
                            onAnswer(Answer.MultipleChoice(setOf(newAnswer)))
                        } else {
                            onAnswer(answer.withAnswerSelected(newAnswer, selected))
                        }
                    },
                    modifier = Modifier.fillParentMaxWidth()
                )


                is PossibleAnswer.Slider -> SliderQuestion(
                    possibleAnswer = question.answer,
                    answer = answer as Answer.Slider?,
                    onAnswerSelected = { onAnswer(Answer.Slider(it)) },
                    modifier = Modifier.fillParentMaxWidth()
                )

            }
        }
    }
}

/* survey can have the following question types:
    - RadioButtonQuestion for singlechoice questions
    -CheckboxQuestion for multichoicequestion
    -sliderquestion for sliderquestions
 */
//------------------------------------------------------------------------
@Composable
private fun RadioButtonQuestion(
    possibleAnswer: PossibleAnswer.SingleChoice,
    answer: Answer.SingleChoice?,
    onAnswerSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = possibleAnswer.options.associateBy { it }

    val radioOptions = options.keys.toList()

    val selected = answer?.answer

    val (selectedOption, onOptionSelected) = remember(answer) { mutableStateOf(selected) }

    Column(modifier = modifier) {
        radioOptions.forEach { text ->
            val onClickHandle = {
                onOptionSelected(text)
                options[text]?.let { onAnswerSelected(it) }
                Unit
            }
            val optionSelected = text == selectedOption

            Surface(
                color = Color.White.copy(alpha = 0.7f),
                border = BorderStroke(
                    width = 1.dp,
                    color = colorResource(R.color.violets_blue)
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = optionSelected,
                            onClick = onClickHandle
                        )
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = text)

                    RadioButton(
                        selected = optionSelected,
                        onClick = onClickHandle,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(R.color.violets_blue )
                        )
                    )
                }
            }

        }
    }
}

@Composable
private fun CheckBoxQuestion(
    possibleAnswer: PossibleAnswer.MultipleChoice,
    answer: Answer.MultipleChoice?,
    onAnswerSelected: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = possibleAnswer.options.associateBy { it }
    Column(modifier = modifier) {
        for (option in options) {
            var checkedState by remember(answer) {
                val selectedOption = answer?.answersStringRes?.contains(option.value)
                mutableStateOf(selectedOption ?: false)
            }

            Surface(
                color = Color.White.copy(alpha = 0.7f),
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(
                    width = 1.dp,
                    color = colorResource(R.color.violets_blue)
                ),
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                        .clickable(
                            onClick = {
                                checkedState = !checkedState
                                onAnswerSelected(option.value, checkedState)
                            }
                        )
                ) {
                    Text(text = option.key)

                    Checkbox(
                        checked = checkedState,
                        onCheckedChange = { selected ->
                            checkedState = selected
                            onAnswerSelected(option.value, selected)
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = colorResource(R.color.violets_blue ),
                            checkmarkColor = colorResource(R.color.light_carmine_pink)
                        )
                    )
                }
            }

        }
    }
}

@Composable
private fun SliderQuestion(
    possibleAnswer: PossibleAnswer.Slider,
    answer: Answer.Slider?,
    onAnswerSelected: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var sliderPosition by remember {
        mutableStateOf(answer?.answerValue ?: possibleAnswer.defaultValue)
    }
    Row(modifier = modifier) {
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                onAnswerSelected(it)
            },
            valueRange = possibleAnswer.range,
            colors = SliderDefaults.colors(thumbColor = colorResource(id = R.color.violets_blue)
            , inactiveTickColor = colorResource(id = R.color.yellow),
            activeTrackColor = colorResource(id = R.color.violets_blue)),
            steps = possibleAnswer.steps,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)
        )
    }
    Row {
        Text(
            text = possibleAnswer.startText,
            textAlign = TextAlign.Start,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.8f)
        )
        Text(
            text = possibleAnswer.neutralText,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.8f)
        )
        Text(
            text = possibleAnswer.endText,
            textAlign = TextAlign.End,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.8f)
        )
    }
}




