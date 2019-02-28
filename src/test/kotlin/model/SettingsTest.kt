package model

import org.junit.Assert.assertEquals
import org.junit.Test

class SettingsTest {

    @Test
    fun `on get default`() {
        val mvpElement = ScreenElement("MVP", "package ${Variable.PACKAGE_NAME.value}\n\nimport ${Variable.ANDROID_COMPONENT_FULL_CLASS_NAME.value}\n\nclass ${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value} : ${Variable.ANDROID_COMPONENT_CLASS_NAME.value}", FileType.KOTLIN, "${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value}")
        val presenterElement = ScreenElement("Presenter", FileType.KOTLIN.defaultTemplate, FileType.KOTLIN, FileType.KOTLIN.defaultFileName)
        val viewElement = ScreenElement("View", "package ${Variable.PACKAGE_NAME.value}\n\ninterface ${Variable.NAME.value}${Variable.SCREEN_ELEMENT.value}", FileType.KOTLIN, FileType.KOTLIN.defaultFileName)
        val layoutElement = ScreenElement("layout", FileType.LAYOUT_XML.defaultTemplate, FileType.LAYOUT_XML, FileType.LAYOUT_XML.defaultFileName)
        assertEquals(Settings(
                mutableListOf(
                        mvpElement,
                        presenterElement,
                        viewElement,
                        layoutElement
                ),
                "androidx.appcompat.app.AppCompatActivity",
                "androidx.fragment.app.Fragment"
        ), Settings())
    }
}