package group4.sensimate


import androidx.activity.compose.setContent
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import group4.sensimate.data.repository.UserRepository
import group4.sensimate.presentation.SensiMateActivity
import group4.sensimate.presentation.event.EventPreview
import group4.sensimate.presentation.survey.CreateSurveyScreen
import group4.sensimate.presentation.survey.CreateSurveyScreenPreview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.Mockito.mock

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class questionIncrease {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<SensiMateActivity>()

    @Test
    fun initially_1(){


        val text = composeTestRule.activity.getString(R.string.question_count, 1)
        composeTestRule.activity.setContent { CreateSurveyScreenPreview()}
        composeTestRule.onNodeWithText(text).assertExists()


    }

    @Test
    fun addButton_incrementsQuestions(){
        composeTestRule.activity.setContent { CreateSurveyScreenPreview()}
        composeTestRule.onNodeWithText("Add").performClick()
        val text = composeTestRule.activity.getString(R.string.question_count, 2)
        composeTestRule.onNodeWithText(text).assertExists()
    }



}