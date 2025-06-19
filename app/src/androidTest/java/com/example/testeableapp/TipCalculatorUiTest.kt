package com.example.testeableapp

import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.testeableapp.ui.Screens.TipCalculatorScreen
import org.junit.Rule
import org.junit.Test


class TipCalculatorUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_ui_elements_are_displayed() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        composeTestRule.onNodeWithText("Monto de la cuenta").assertIsDisplayed()
        composeTestRule.onNodeWithText("Porcentaje de propina: 15%").assertIsDisplayed()
        composeTestRule.onNodeWithText("Número de personas: 1").assertIsDisplayed()
    }


    @Test
    fun test_round_tip_changes_result() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        composeTestRule.onNodeWithText("Monto de la cuenta")
            .performTextInput("85")
        composeTestRule.waitUntil(timeoutMillis = 2_000) {
            composeTestRule.onAllNodesWithText("Propina: $12.75").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNode(isToggleable()).performClick()
        composeTestRule.waitUntil(timeoutMillis = 2_000) {
            composeTestRule.onAllNodesWithText("Propina: $13.00").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Propina: $13.00").assertIsDisplayed()
    }

    @Test
    fun test_after_mov_slider() {
        composeTestRule.setContent { TipCalculatorScreen() }

        val input = composeTestRule.onAllNodes(hasSetTextAction())[0]
        input.performTextInput("75")

        val nodoPropina = composeTestRule
            .onAllNodesWithText("Propina:", substring = true)
            .onFirst()
        val textoAntes = nodoPropina
            .fetchSemanticsNode()
            .config[SemanticsProperties.Text]
            .joinToString()

        val slider = composeTestRule.onNode(
            SemanticsMatcher.keyIsDefined(SemanticsActions.SetProgress)
        )
        slider.performSemanticsAction(SemanticsActions.SetProgress) { it(0.20f) }

        composeTestRule.waitForIdle()

        val textoDespues = nodoPropina
            .fetchSemanticsNode()
            .config[SemanticsProperties.Text]
            .joinToString()

        assert(textoDespues != textoAntes) {
            "La propina no cambio (antes=$textoAntes, después=$textoDespues)"
        }
    }

    @Test
    fun test_increment_and_decrement_people_count() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        composeTestRule.onNodeWithText("Número de personas: 1")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("Número de personas: 2")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("-").performClick()
        composeTestRule.onNodeWithText("Número de personas: 1")
            .assertIsDisplayed()
    }

    @Test
    fun test_round_tip_checkbox_changes_result() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        composeTestRule.onNodeWithText("Monto de la cuenta")
            .performTextInput("85")
        composeTestRule.onNodeWithText("Propina: $12.75").assertExists()
        composeTestRule.onNode(isToggleable()).performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Propina: $13.00").assertExists()
    }





}
