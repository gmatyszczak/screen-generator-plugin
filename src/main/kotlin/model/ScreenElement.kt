package model

import util.toSnakeCase
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
            template.replaceVariables(screenName, packageName, androidComponent, androidComponentBaseClass)

    fun fileName(screenName: String, packageName: String, androidComponent: String, androidComponentBaseClass: String) =
            fileNameTemplate.replaceVariables(screenName, packageName, androidComponent, androidComponentBaseClass).run {
                if (fileType == FileType.LAYOUT_XML)
                    toLowerCase()
                else
                    this
            }

    private fun String.replaceVariables(screenName: String, packageName: String, androidComponent: String, androidComponentBaseClass: String) =
            replace(VARIABLE_NAME, screenName)
                    .replace(VARIABLE_NAME_SNAKE_CASE, screenName.toSnakeCase())
                    .replace(VARIABLE_SCREEN_ELEMENT, name)
                    .replace(VARIABLE_PACKAGE_NAME, packageName)
                    .replace(VARIABLE_ANDROID_COMPONENT_SHORT_NAME, androidComponent)
                    .replace(VARIABLE_ANDROID_COMPONENT_FULL_NAME, androidComponentBaseClass)
                    .replace(VARIABLE_ANDROID_COMPONENT_LONG_NAME, androidComponentBaseClass.substring(androidComponentBaseClass.lastIndexOf(".") + 1))

    companion object {
        fun getDefault() = ScreenElement(UNNAMED_ELEMENT, FileType.KOTLIN.defaultTemplate, FileType.KOTLIN, FileType.KOTLIN.defaultFileName)
    }
}