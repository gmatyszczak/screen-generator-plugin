package model

enum class AnchorPosition(val displayName: String) {
    TOP("Top"),
    BOTTOM("Bottom");

    override fun toString() = displayName
}
