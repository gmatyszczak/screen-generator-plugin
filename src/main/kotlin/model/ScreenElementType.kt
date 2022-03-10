package model

enum class ScreenElementType(val displayName: String) {
    NEW_FILE("New File"),
    FILE_MODIFICATION("File Modification");

    override fun toString() = displayName
}
