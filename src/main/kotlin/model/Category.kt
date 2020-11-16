package model

data class Category(
    var id: Int = 0,
    var name: String = ""
) {

    override fun toString() = name
}