package settings

import java.io.Serializable

data class ScreenElement(var name: String) : Serializable {

    override fun toString() = name
}