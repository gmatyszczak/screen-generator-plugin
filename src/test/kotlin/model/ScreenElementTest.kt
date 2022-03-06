package model

import org.junit.Assert.assertEquals
import org.junit.Test

class ScreenElementTest {

    private val template =
        "package ${Variable.PACKAGE_NAME.value}\n\nimport androidx.appcompat.app.AppCompatActivity\n\nclass ${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value} : AppCompatActivity"
    private val kotlinScreenElement =
        ScreenElement("Presenter", template, FileType.KOTLIN, FileType.KOTLIN.defaultFileName)
    private val xmlScreenElement =
        ScreenElement("Presenter", template, FileType.LAYOUT_XML, FileType.LAYOUT_XML.defaultFileName)

    @Test
    fun `on body`() {
        assertEquals(
            "package com.test\n\nimport androidx.appcompat.app.AppCompatActivity\n\nclass TestActivity : AppCompatActivity",
            kotlinScreenElement.body("Test", "com.test", "Activity", emptyMap())
        )
    }

    @Test
    fun `when contains name lower case variable on body`() {
        val screenElement =
            ScreenElement("Presenter", Variable.NAME_LOWER_CASE.value, FileType.KOTLIN, FileType.KOTLIN.defaultFileName)
        assertEquals(
            "testScreen",
            screenElement.body("TestScreen", "com.test", "Activity", emptyMap())
        )
    }

    @Test
    fun `when contains android component name lower case variable on body`() {
        val screenElement = ScreenElement(
            "Presenter",
            Variable.ANDROID_COMPONENT_NAME_LOWER_CASE.value,
            FileType.KOTLIN,
            FileType.KOTLIN.defaultFileName
        )
        assertEquals(
            "activity",
            screenElement.body("TestScreen", "com.test", "Activity", emptyMap())
        )
    }

    @Test
    fun `when file type is kotlin on file name`() {
        assertEquals(
            "TestPresenter",
            kotlinScreenElement.fileName("Test", "com.test", "Activity", emptyMap())
        )
    }

    @Test
    fun `when file type is xml on file name`() {
        assertEquals(
            "activity_test",
            xmlScreenElement.fileName("Test", "com.test", "Activity", emptyMap())
        )
    }

    @Test
    fun `on get default`() {
        assertEquals(
            ScreenElement(
                "UnnamedElement",
                FileType.KOTLIN.defaultTemplate,
                FileType.KOTLIN,
                FileType.KOTLIN.defaultFileName,
                AndroidComponent.NONE,
                10
            ),
            ScreenElement.getDefault(10)
        )
    }

    @Test
    fun `when custom variables not empty on body`() {
        assertEquals(
            "custom1custom2",
            ScreenElement(
                "UnnamedElement",
                "%custom1%%custom2%",
                FileType.KOTLIN,
                FileType.KOTLIN.defaultFileName,
                AndroidComponent.NONE,
                10
            ).body(
                "Test",
                "com.test",
                "Activity",
                mapOf(CustomVariable("custom1") to "custom1", CustomVariable("custom2") to "custom2")
            ),
        )
    }
}
