package model

import org.junit.Assert.assertEquals
import org.junit.Test

class ScreenElementTest {

    private val template = "package %packageName%\n\nimport %androidComponentFullName%\n\nclass %name%%androidComponentShortName% : %androidComponentLongName%"
    private val screenElement = ScreenElement("Presenter", template, FileType.LAYOUT_XML)

    @Test
    fun `on body`() {
        assertEquals(
                "package com.test\n\nimport com.example.AppCompatActivity\n\nclass TestActivity : AppCompatActivity",
                screenElement.body("Test", "com.test", "Activity", "com.example.AppCompatActivity")
        )
    }

    @Test
    fun `on get default`() {
        assertEquals(ScreenElement("UnnamedElement", "package %packageName%\n\nclass %name%%screenElement%", FileType.KOTLIN), ScreenElement.getDefault())
    }
}