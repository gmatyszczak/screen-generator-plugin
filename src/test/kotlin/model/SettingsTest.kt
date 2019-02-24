package model

import org.junit.Assert.assertEquals
import org.junit.Test

class SettingsTest {

    @Test
    fun `on get default`() {
        val mvpElement = ScreenElement("MVP", "package $VARIABLE_PACKAGE_NAME\n\nimport $VARIABLE_ANDROID_COMPONENT_FULL_NAME\n\nclass $VARIABLE_NAME$VARIABLE_ANDROID_COMPONENT_SHORT_NAME : $VARIABLE_ANDROID_COMPONENT_LONG_NAME", FileType.KOTLIN, "$VARIABLE_NAME$VARIABLE_ANDROID_COMPONENT_SHORT_NAME")
        val presenterElement = ScreenElement("Presenter", FileType.KOTLIN.defaultTemplate, FileType.KOTLIN, FileType.KOTLIN.defaultFileName)
        val viewElement = ScreenElement("View", "package $VARIABLE_PACKAGE_NAME\n\ninterface $VARIABLE_NAME$VARIABLE_SCREEN_ELEMENT", FileType.KOTLIN, FileType.KOTLIN.defaultFileName)
        val layoutElement = ScreenElement("layout", FileType.LAYOUT_XML.defaultTemplate, FileType.LAYOUT_XML, FileType.LAYOUT_XML.defaultFileName)
        assertEquals(Settings(
                listOf(
                        mvpElement,
                        presenterElement,
                        viewElement,
                        layoutElement
                ),
                "androidx.appcompat.app.AppCompatActivity",
                "androidx.fragment.app.Fragment"
        ), Settings.getDefault())
    }
}