package model

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

class ScreenElementTest {

    private val template =
        "package ${Variable.PACKAGE_NAME.value}\n\nimport androidx.appcompat.app.AppCompatActivity\n\nclass ${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value} : AppCompatActivity"
    private val kotlinScreenElement =
        ScreenElement("Presenter", template, FileType.KOTLIN, FileType.KOTLIN.defaultFileName)
    private val xmlScreenElement =
        ScreenElement("Presenter", template, FileType.LAYOUT_XML, FileType.LAYOUT_XML.defaultFileName)

    @Test
    fun `on body`() {
        kotlinScreenElement.body(
            "Test",
            "com.test",
            "Activity",
            emptyMap()
        ) shouldBeEqualTo "package com.test\n\nimport androidx.appcompat.app.AppCompatActivity\n\nclass TestActivity : AppCompatActivity"
    }

    @Test
    fun `when contains name lower case variable on body`() {
        val screenElement =
            ScreenElement("Presenter", Variable.NAME_LOWER_CASE.value, FileType.KOTLIN, FileType.KOTLIN.defaultFileName)

        screenElement.body("TestScreen", "com.test", "Activity", emptyMap()) shouldBeEqualTo "testScreen"
    }

    @Test
    fun `when contains android component name lower case variable on body`() {
        val screenElement = ScreenElement(
            "Presenter",
            Variable.ANDROID_COMPONENT_NAME_LOWER_CASE.value,
            FileType.KOTLIN,
            FileType.KOTLIN.defaultFileName
        )
        screenElement.body("TestScreen", "com.test", "Activity", emptyMap()) shouldBeEqualTo "activity"
    }

    @Test
    fun `when file type is kotlin on file name`() {
        kotlinScreenElement.fileName("Test", "com.test", "Activity", emptyMap()) shouldBeEqualTo "TestPresenter"
    }

    @Test
    fun `when file type is xml on file name`() {
        xmlScreenElement.fileName("Test", "com.test", "Activity", emptyMap()) shouldBeEqualTo "activity_test"
    }

    @Test
    fun `on get default`() {
        ScreenElement.getDefault(10) shouldBeEqualTo ScreenElement(
            "UnnamedElement",
            FileType.KOTLIN.defaultTemplate,
            FileType.KOTLIN,
            FileType.KOTLIN.defaultFileName,
            AndroidComponent.NONE,
            10
        )
    }

    @Test
    fun `when custom variables not empty on body`() {
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
        ) shouldBeEqualTo "custom1custom2"
    }
}
