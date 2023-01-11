package group4.sensimate.presentation.survey

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import group4.sensimate.R
import group4.sensimate.data.model.Answer
import group4.sensimate.data.model.PossibleAnswer
import group4.sensimate.data.model.Question
import group4.sensimate.data.model.withAnswerSelected
import group4.sensimate.presentation.event.EventsViewModel
import group4.sensimate.presentation.navigation.graphs.EventDetailsNavGraph
import group4.sensimate.ui.components.GradientButton
import group4.sensimate.ui.components.GradientText
import group4.sensimate.ui.components.GradientTextField
import group4.sensimate.ui.theme.SensiMateTheme


    @Composable
    fun CreateSurveyScreen(navController: NavController, vm: EventsViewModel = viewModel()) {
        var textFieldCount by remember { mutableStateOf (1) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.background))
        ) {
            Column(
                horizontalAlignment= Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(R.color.background))
            ){
                SurveyTopBar(questionIndex = -1, totalQuestionsCount = textFieldCount) {

                }
                Spacer(modifier = Modifier.height(20.dp))

                GradientText(text = "Write your questions here", fontSize = 30)
                Spacer(modifier = Modifier.height(40.dp))
                repeat(textFieldCount){
                    GradientTextField(
                    text = vm.survey,
                    onChange ={vm.surveyChange(it)},
                    label = "Question",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            tint = Color.White,
                            contentDescription = "Question 1"
                        )
                    }
                )}
                Spacer(modifier = Modifier.height(10.dp))

                Button(onClick = {textFieldCount++}, colors = ButtonDefaults.buttonColors(
                    colorResource(id = R.color.violets_blue))) {Text("Add")

                }

            }

            GradientButton(text = "Create Survey", fontSize = 20, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .align(Alignment.BottomEnd),
                onClick = {navController.navigate(EventDetailsNavGraph.EventsScreen.route) }
            )

        }
    }


@Preview(showBackground = true)
@Composable
fun CreateSurveyScreenPreview() {
    SensiMateTheme {
       CreateSurveyScreen(navController = rememberNavController())
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
