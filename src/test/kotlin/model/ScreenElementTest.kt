package model

import org.junit.Assert.assertEquals
import org.junit.Test

class ScreenElementTest {

    private val template = "package $VARIABLE_PACKAGE_NAME\n\nimport $VARIABLE_ANDROID_COMPONENT_FULL_NAME\n\nclass $VARIABLE_NAME$VARIABLE_ANDROID_COMPONENT_SHORT_NAME : $VARIABLE_ANDROID_COMPONENT_LONG_NAME"
    private val screenElement = ScreenElement("Presenter", template, FileType.LAYOUT_XML, FileType.LAYOUT_XML.defaultFileName)

    @Test
    fun `on body`() {
        assertEquals(
                "package com.test\n\nimport com.example.AppCompatActivity\n\nclass TestActivity : AppCompatActivity",
                screenElement.body("Test", "com.test", "Activity", "com.example.AppCompatActivity")
        )
    }

    @Test
    fun `on get default`() {
        assertEquals(
                ScreenElement("UnnamedElement", FileType.KOTLIN.defaultTemplate, FileType.KOTLIN, FileType.KOTLIN.defaultFileName),
                ScreenElement.getDefault())
    }
}