package com.example.testeableapp

import kotlin.math.ceil
import kotlin.test.Test
import kotlin.test.assertEquals

class TipCalculatorUnitTest {

    fun calculateTip(amount: Double, tipPercent: Double, roundUp: Boolean): Double {
        if (amount < 0) return 0.0
        var tip = amount * (tipPercent / 100)
        if (roundUp) {
            tip = ceil(tip)
        }
        return tip
    }

    @Test
    fun calculateTip_20Percent() {
        val result = calculateTip(100.0, 20.0, false)
        assertEquals(20.0, result, 0.01)
    }

    @Test
    fun calculateTip_15Percent_Rounded() {
        val result = calculateTip(85.0, 15.0, true)
        assertEquals(13.0, result, 0.01)
    }

    @Test
    fun calculateTip_NegativeAmount() {
        val result = calculateTip(-50.0, 20.0, false)
        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun calculateTotalPerPerson() {
        val totalAmount = 100.0
        val tip = calculateTip(totalAmount, 20.0, false)
        val people = 4
        val totalPerPerson = (totalAmount + tip) / people
        assertEquals(30.0, totalPerPerson, 0.01)
    }

    @Test
    fun calculateTip_zeroAmount_returnsZeroTip() {
        val result = calculateTip(0.0, 15.0, false)
        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun calculateTip_highPercentageWithRounding() {
        val result = calculateTip(37.0, 50.0, true)
        assertEquals(19.0, result, 0.01)
    }

}