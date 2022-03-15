package data.file

import model.Anchor
import model.AnchorPosition
import model.AndroidComponent
import model.CustomVariable
import model.FileType
import model.ScreenElement

data class Modification(
    val name: String,
    val content: String,
    val fileType: FileType,
    val anchor: Anchor,
    val anchorPosition: AnchorPosition,
    val anchorName: String,
)

fun ScreenElement.toModification(
    screenName: String,
    packageName: String,
    androidComponent: AndroidComponent,
    customVariablesMap: Map<CustomVariable, String>
) = Modification(
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
    anchor,
    anchorPosition,
    anchorName,
)