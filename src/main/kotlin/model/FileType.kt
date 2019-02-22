package model

enum class FileType(val displayName: String) {
    KOTLIN("Kotlin"),
    LAYOUT_XML("Layout XML");

    override fun toString() = displayName
}