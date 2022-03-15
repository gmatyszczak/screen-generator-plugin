package data.file

import model.AndroidComponent
import model.CustomVariable
import model.FileType
import model.ScreenElement

data class File(
    val name: String,
    val content: String,
    val fileType: FileType
)

fun ScreenElement.toFile(
    screenName: String,
    packageName: String,
    androidComponent: AndroidComponent,
    customVariablesMap: Map<CustomVariable, String>
) = File(
    fileName(
        screenName,
        packageName,
        androidComponent.displayName,
        customVariablesMap
    ),
    body(
        screenName,
        packageName,
        androidComponent.displayName,
        customVariablesMap
    ),
    fileType,
)
