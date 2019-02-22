package model

import java.io.Serializable

private const val UNNAMED_ELEMENT = "UnnamedElement"
private const val TEMPLATE = "package %packageName%\n\nclass %name%%screenElement%"

data class ScreenElement(var name: String, var template: String, var fileType: FileType) : Serializable {

    override fun toString() = name

    fun body(screenName: String, packageName: String) =
            template.replace("%name%", screenName)
                    .replace("%screenElement%", name)
                    .replace("%packageName%", packageName)

    companion object {
        fun getDefault() = ScreenElement(UNNAMED_ELEMENT, TEMPLATE, FileType.KOTLIN)
    }
}