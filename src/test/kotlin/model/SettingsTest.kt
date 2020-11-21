package model

import org.junit.Assert.assertEquals
import org.junit.Test

class SettingsTest {

    @Test
    fun `on get default`() {
        val elementActivity = ScreenElement(
            "Activity",
            DEFAULT_ACTIVITY_TEMPLATE,
            FileType.KOTLIN,
            "${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value}",
            AndroidComponent.ACTIVITY
        )
        val elementFragment = ScreenElement(
            "Fragment",
            DEFAULT_FRAGMENT_TEMPLATE,
            FileType.KOTLIN,
            "${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value}",
            AndroidComponent.FRAGMENT
        )
        val elementViewModel =
            ScreenElement("ViewModel", DEFAULT_VIEW_MODEL_TEMPLATE, FileType.KOTLIN, FileType.KOTLIN.defaultFileName)
        val elementViewModelTest = ScreenElement(
            "ViewModelTest",
            DEFAULT_VIEW_MODEL_TEST_TEMPLATE,
            FileType.KOTLIN,
            FileType.KOTLIN.defaultFileName,
            sourceSet = "test"
        )
        val elementRepository = ScreenElement(
            "Repository",
            DEFAULT_REPOSITORY_TEMPLATE,
            FileType.KOTLIN,
            FileType.KOTLIN.defaultFileName,
            subdirectory = "repository"
        )
        val elementLayout = ScreenElement(
            "layout",
            FileType.LAYOUT_XML.defaultTemplate,
            FileType.LAYOUT_XML,
            FileType.LAYOUT_XML.defaultFileName
        )
        assertEquals(
            Settings(
                mutableListOf(
                    elementActivity,
                    elementFragment,
                    elementViewModel,
                    elementViewModelTest,
                    elementRepository,
                    elementLayout
                )
            ), Settings()
        )
    }
}