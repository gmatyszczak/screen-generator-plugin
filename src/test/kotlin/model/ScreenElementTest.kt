package model

import org.junit.Assert.assertEquals
import org.junit.Test

class ScreenElementTest {

    private val screenElement = ScreenElement("Presenter", "package %packageName%\n\nclass %name%%screenElement%")

    @Test
    fun `on body`() {
        assertEquals("package com.test\n\nclass TestPresenter", screenElement.body("Test", "com.test"))
    }
}