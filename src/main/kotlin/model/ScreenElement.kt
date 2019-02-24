package model

import java.io.Serializable

private const val UNNAMED_ELEMENT = "UnnamedElement"

data class ScreenElement(var name: String, var template: String, var fileType: FileType) : Serializable {

    override fun toString() = name

    fun body(screenName: String, packageName: String, androidComponent: String, androidComponentBaseClass: String) =
            template.replace("%name%", screenName)
                    .replace("%screenElement%", name)
                    .replace("%packageName%", packageName)
                    .replace("%androidComponentShortName%", androidComponent)
                    .replace("%androidComponentFullName%", androidComponentBaseClass)
                    .replace("%androidComponentLongName%", androidComponentBaseClass.substring(androidComponentBaseClass.lastIndexOf(".") + 1))

    companion object {
        fun getDefault() = ScreenElement(UNNAMED_ELEMENT, FileType.KOTLIN.defaultTemplate, FileType.KOTLIN)
    }
}