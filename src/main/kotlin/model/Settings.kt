package model

import java.io.Serializable

private const val DEFAULT_BASE_ACTIVITY_CLASS = "androidx.appcompat.app.AppCompatActivity"
private const val DEFAULT_BASE_FRAGMENT_CLASS = "androidx.fragment.app.Fragment"
private val DEFAULT_MVP_TEMPLATE =
        "package ${Variable.PACKAGE_NAME.value}\n\nimport ${Variable.ANDROID_COMPONENT_FULL_CLASS_NAME.value}\n\nclass ${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value} : ${Variable.ANDROID_COMPONENT_CLASS_NAME.value}"
private val DEFAULT_VIEW_TEMPLATE = "package ${Variable.PACKAGE_NAME.value}\n\ninterface ${Variable.NAME.value}${Variable.SCREEN_ELEMENT.value}"

public fun defaultScreenElements() = mutableListOf(
        ScreenElement("MVP", DEFAULT_MVP_TEMPLATE, FileType.KOTLIN, "${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value}"),
        ScreenElement("Presenter", FileType.KOTLIN.defaultTemplate, FileType.KOTLIN, FileType.KOTLIN.defaultFileName),
        ScreenElement("View", DEFAULT_VIEW_TEMPLATE, FileType.KOTLIN, FileType.KOTLIN.defaultFileName),
        ScreenElement("layout", FileType.LAYOUT_XML.defaultTemplate, FileType.LAYOUT_XML, FileType.LAYOUT_XML.defaultFileName)
)

private fun defaultCategories() = mutableListOf(
        Category(1, "Activity", defaultScreenElements()),
        Category(2, "Fragment", defaultScreenElements()),
        Category(3, "Adapter", defaultScreenElements())
)

data class Settings(
        var categories: MutableList<Category> = defaultCategories(),
        var currentlySelectedCategory: Category = categories[0],
        var activityBaseClass: String = DEFAULT_BASE_ACTIVITY_CLASS,
        var fragmentBaseClass: String = DEFAULT_BASE_FRAGMENT_CLASS
) : Serializable