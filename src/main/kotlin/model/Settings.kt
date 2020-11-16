package model

import java.io.Serializable

private val DEFAULT_MVP_TEMPLATE = "package ${Variable.PACKAGE_NAME.value}\n\nimport androidx.appcompat.app.AppCompatActivity\n\nclass ${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value} : AppCompatActivity"
private val DEFAULT_VIEW_TEMPLATE = "package ${Variable.PACKAGE_NAME.value}\n\ninterface ${Variable.NAME.value}${Variable.SCREEN_ELEMENT.value}"

private fun defaultScreenElements() = mutableListOf(
        ScreenElement("MVP", DEFAULT_MVP_TEMPLATE, FileType.KOTLIN, "${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value}"),
        ScreenElement("Presenter", FileType.KOTLIN.defaultTemplate, FileType.KOTLIN, FileType.KOTLIN.defaultFileName),
        ScreenElement("View", DEFAULT_VIEW_TEMPLATE, FileType.KOTLIN, FileType.KOTLIN.defaultFileName),
        ScreenElement("layout", FileType.LAYOUT_XML.defaultTemplate, FileType.LAYOUT_XML, FileType.LAYOUT_XML.defaultFileName)
)

private fun defaultCategories() = mutableListOf(
    Category(0, "Default")
)

data class Settings(
    var screenElements: MutableList<ScreenElement> = defaultScreenElements(),
    var categories: MutableList<Category> = defaultCategories()
) : Serializable