package model

import java.io.Serializable

val DEFAULT_ACTIVITY_TEMPLATE = "package ${Variable.PACKAGE_NAME.value}\n\nimport androidx.appcompat.app.AppCompatActivity\n\nclass ${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value} : AppCompatActivity"
val DEFAULT_FRAGMENT_TEMPLATE = "package ${Variable.PACKAGE_NAME.value}\n\nimport androidx.fragment.app.Fragment\n\nclass ${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value} : Fragment"
val DEFAULT_VIEW_MODEL_TEMPLATE = "package ${Variable.PACKAGE_NAME.value}\n\nimport androidx.lifecycle.ViewModel\n\nclass ${Variable.NAME.value}${Variable.SCREEN_ELEMENT.value} : ViewModel()"
val DEFAULT_VIEW_MODEL_TEST_TEMPLATE = "package ${Variable.PACKAGE_NAME.value}\n\nclass ${Variable.NAME.value}${Variable.SCREEN_ELEMENT.value}"
val DEFAULT_REPOSITORY_TEMPLATE = "package ${Variable.PACKAGE_NAME.value}.repository\n\nclass ${Variable.NAME.value}${Variable.SCREEN_ELEMENT.value}"

private fun defaultScreenElements() = mutableListOf(
        ScreenElement("Activity", DEFAULT_ACTIVITY_TEMPLATE, FileType.KOTLIN, "${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value}", AndroidComponent.ACTIVITY),
        ScreenElement("Fragment", DEFAULT_FRAGMENT_TEMPLATE, FileType.KOTLIN, "${Variable.NAME.value}${Variable.ANDROID_COMPONENT_NAME.value}", AndroidComponent.FRAGMENT),
        ScreenElement("ViewModel", DEFAULT_VIEW_MODEL_TEMPLATE, FileType.KOTLIN, FileType.KOTLIN.defaultFileName),
        ScreenElement("ViewModelTest", DEFAULT_VIEW_MODEL_TEST_TEMPLATE, FileType.KOTLIN, FileType.KOTLIN.defaultFileName, sourceSet = "test"),
        ScreenElement("Repository", DEFAULT_REPOSITORY_TEMPLATE, FileType.KOTLIN, FileType.KOTLIN.defaultFileName, subdirectory = "repository"),
        ScreenElement("layout", FileType.LAYOUT_XML.defaultTemplate, FileType.LAYOUT_XML, FileType.LAYOUT_XML.defaultFileName)
)

private fun defaultCategories() = mutableListOf(
    Category(0, "Default")
)

data class Settings(
    var screenElements: MutableList<ScreenElement> = defaultScreenElements(),
    var categories: MutableList<Category> = defaultCategories()
) : Serializable