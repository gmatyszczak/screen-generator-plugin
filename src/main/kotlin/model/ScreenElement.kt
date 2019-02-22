package model

import java.io.Serializable

data class ScreenElement(var name: String, var template: String) : Serializable {

    override fun toString() = name

    fun body(screenName: String, packageName: String) =
            template.replace("%name%", screenName)
                    .replace("%screenElement%", name)
                    .replace("%packageName%", packageName)
}