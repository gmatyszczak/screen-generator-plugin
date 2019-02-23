package model

enum class FileType(val displayName: String, val extension: String) {
    KOTLIN("Kotlin", "kt"),
    LAYOUT_XML("Layout XML", "xml");

    override fun toString() = displayName
}