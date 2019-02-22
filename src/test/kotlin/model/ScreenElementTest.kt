package model

import org.junit.Assert.assertEquals
import org.junit.Test

class ScreenElementTest {

    private val screenElement = ScreenElement("Presenter", "package %packageName%\n\nclass %name%%screenElement%", FileType.LAYOUT_XML)

    @Test
    fun `on body`() {
        assertEquals("package com.test\n\nclass TestPresenter", screenElement.body("Test", "com.test"))
    }

    @Test
    fun `on get default`() {
        assertEquals(ScreenElement("UnnamedElement", "package %packageName%\n\nclass %name%%screenElement%", FileType.KOTLIN), ScreenElement.getDefault())
    }
}