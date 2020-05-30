package model

import java.io.Serializable
import java.util.*

private const val UNNAMED_CATEGORY = "UnnamedCategory"

data class Category(
    var id: Int,
    var name: String = "",
    var screenElements: MutableList<ScreenElement>
) : Serializable {

    override fun toString() = name

    companion object {
        fun getDefault() = Category(Random().nextInt(), UNNAMED_CATEGORY, defaultScreenElements())
    }
}