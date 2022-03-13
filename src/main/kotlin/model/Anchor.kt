package model

enum class Anchor(val displayName: String) {
    FILE("File"),
    CLASS("Class"),
    OBJECT("Object"),
    FUNCTION("Function"),
    TAG("Tag");

    override fun toString() = displayName
}
