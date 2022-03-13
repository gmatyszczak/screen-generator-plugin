package model

import util.toSnakeCase
import java.io.Serializable

private const val UNNAMED_ELEMENT = "UnnamedElement"
const val DEFAULT_SOURCE_SET = "main"

data class ScreenElement(
    var name: String = "",
    var template: String = "",
    var fileType: FileType = FileType.KOTLIN,
    var fileNameTemplate: String = "",
    var relatedAndroidComponent: AndroidComponent = AndroidComponent.NONE,
    var categoryId: Int = 0,
    var subdirectory: String = "",
    var sourceSet: String = DEFAULT_SOURCE_SET,
    var type: ScreenElementType = ScreenElementType.NEW_FILE,
    var anchor: Anchor = Anchor.FILE,
    var anchorPosition: AnchorPosition = AnchorPosition.TOP,
    var anchorName: String = "",
) : Serializable {

    override fun toString() = name

    fun body(
        screenName: String,
        packageName: String,
        androidComponent: String,
        customVariablesMap: Map<CustomVariable, String>
    ) =
        template
            .replaceVariables(screenName, packageName, androidComponent)
            .replaceCustomVariables(customVariablesMap)

    fun fileName(
        screenName: String,
        packageName: String,
        androidComponent: String,
        customVariablesMap: Map<CustomVariable, String>
    ) =
        fileNameTemplate
            .replaceVariables(screenName, packageName, androidComponent)
            .replaceCustomVariables(customVariablesMap)
            .run {
                if (fileType == FileType.LAYOUT_XML)
                    toLowerCase()
                else
                    this
            }

    private fun String.replaceVariables(
        screenName: String,
        packageName: String,
        androidComponent: String
    ) =
        replace(Variable.NAME.value, screenName)
            .replace(Variable.NAME_SNAKE_CASE.value, screenName.toSnakeCase())
            .replace(Variable.NAME_LOWER_CASE.value, screenName.decapitalize())
            .replace(Variable.SCREEN_ELEMENT.value, name)
            .replace(Variable.PACKAGE_NAME.value, packageName)
            .replace(Variable.ANDROID_COMPONENT_NAME.value, androidComponent)
            .replace(Variable.ANDROID_COMPONENT_NAME_LOWER_CASE.value, androidComponent.decapitalize())

    private fun String.replaceCustomVariables(variables: Map<CustomVariable, String>): String {
        var updatedString = this
        variables.forEach { (variable, text) ->
            updatedString = updatedString.replace("%${variable.name}%", text)
        }
        return updatedString
    }

    companion object {
        fun getDefault(categoryId: Int) = ScreenElement(
            UNNAMED_ELEMENT,
            FileType.KOTLIN.defaultTemplate,
            FileType.KOTLIN,
            FileType.KOTLIN.defaultFileName,
            categoryId = categoryId
        )
    }
}
