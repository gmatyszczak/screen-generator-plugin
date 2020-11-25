package model

private const val DEFAULT_NAME = "UnnamedVariable"

data class CustomVariable(
    var name: String = ""
) {

    override fun toString() = name

    companion object {
        fun getDefault() = CustomVariable(DEFAULT_NAME)
    }
}