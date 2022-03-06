package model

private const val DEFAULT_NAME = "UnnamedCategory"

data class Category(
    var id: Int = 0,
    var name: String = "",
    var customVariables: List<CustomVariable> = emptyList()
) {

    override fun toString() = name

    companion object {
        fun getDefault(id: Int) = Category(id, DEFAULT_NAME)
    }
}
