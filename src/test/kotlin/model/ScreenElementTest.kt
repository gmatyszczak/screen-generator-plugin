package model

import org.junit.Assert.assertEquals
import org.junit.Test

class ScreenElementTest {

    private val template = "package ${Variable.PACKAGE_NAME.value}\n\nimport androidx.appcompat.app.AppCompatActivity\n\nclass ${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value} : AppCompatActivity"
    private val kotlinScreenElement = ScreenElement("Presenter", template, FileType.KOTLIN, FileType.KOTLIN.defaultFileName)
    private val xmlScreenElement = ScreenElement("Presenter", template, FileType.LAYOUT_XML, FileType.LAYOUT_XML.defaultFileName)

    @Test
    fun `on body`() {
        assertEquals(
                "package com.test\n\nimport androidx.appcompat.app.AppCompatActivity\n\nclass TestActivity : AppCompatActivity",
                kotlinScreenElement.body("Test", "com.test", "Activity")
        )
    }

    @Test
    fun `when contains name lower case variable on body`() {
        val screenElement = ScreenElement("Presenter", Variable.NAME_LOWER_CASE.value, FileType.KOTLIN, FileType.KOTLIN.defaultFileName)
        assertEquals(
                "testScreen",
                screenElement.body("TestScreen", "com.test", "Activity")
        )
    }

    @Test
    fun `when contains android component name lower case variable on body`() {
        val screenElement = ScreenElement("Presenter", Variable.ANDROID_COMPONENT_NAME_LOWER_CASE.value, FileType.KOTLIN, FileType.KOTLIN.defaultFileName)
        assertEquals(
                "activity",
                screenElement.body("TestScreen", "com.test", "Activity")
        )
    }

    @Test
    fun `when file type is kotlin on file name`() {
        assertEquals(
                "TestPresenter",
                kotlinScreenElement.fileName("Test", "com.test", "Activity")
        )
    }

    @Test
    fun `when file type is xml on file name`() {
        assertEquals(
                "activity_test",
                xmlScreenElement.fileName("Test", "com.test", "Activity")
        )
    }

    @Test
    fun `on get default`() {
        assertEquals(
                ScreenElement("UnnamedElement", FileType.KOTLIN.defaultTemplate, FileType.KOTLIN, FileType.KOTLIN.defaultFileName),
                ScreenElement.getDefault())
    }
}