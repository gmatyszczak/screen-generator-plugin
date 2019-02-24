package model

import java.io.Serializable

const val VARIABLE_NAME = "%name%"
const val VARIABLE_NAME_SNAKE_CASE = "%nameSnakeCase%"
const val VARIABLE_SCREEN_ELEMENT = "%screenElement%"
const val VARIABLE_PACKAGE_NAME = "%packageName%"
const val VARIABLE_ANDROID_COMPONENT_SHORT_NAME = "%androidComponentShortName%"
const val VARIABLE_ANDROID_COMPONENT_FULL_NAME = "%androidComponentFullName%"
const val VARIABLE_ANDROID_COMPONENT_LONG_NAME = "%androidComponentLongName%"

private const val UNNAMED_ELEMENT = "UnnamedElement"

data class ScreenElement(var name: String, var template: String, var fileType: FileType, var fileNameTemplate: String) : Serializable {

    override fun toString() = name

    fun body(screenName: String, packageName: String, androidComponent: String, androidComponentBaseClass: String) =
            template.replace(VARIABLE_NAME, screenName)
                    .replace(VARIABLE_SCREEN_ELEMENT, name)
                    .replace(VARIABLE_PACKAGE_NAME, packageName)
                    .replace(VARIABLE_ANDROID_COMPONENT_SHORT_NAME, androidComponent)
                    .replace(VARIABLE_ANDROID_COMPONENT_FULL_NAME, androidComponentBaseClass)
                    .replace(VARIABLE_ANDROID_COMPONENT_LONG_NAME, androidComponentBaseClass.substring(androidComponentBaseClass.lastIndexOf(".") + 1))

    companion object {
        fun getDefault() = ScreenElement(UNNAMED_ELEMENT, FileType.KOTLIN.defaultTemplate, FileType.KOTLIN, FileType.KOTLIN.defaultFileName)
    }
}