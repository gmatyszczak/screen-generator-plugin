package model

private const val KOTLIN_DEFAULT_TEMPLATE = "package $VARIABLE_PACKAGE_NAME\n\nclass $VARIABLE_NAME$VARIABLE_SCREEN_ELEMENT"
private const val LAYOUT_XML_DEFAULT_TEMPLATE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
        "<FrameLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
        "    android:layout_width=\"match_parent\"\n" +
        "    android:layout_height=\"match_parent\">\n" +
        "\n" +
        "</FrameLayout>"
private const val KOTLIN_DEFAULT_FILE_NAME = "$VARIABLE_NAME$VARIABLE_SCREEN_ELEMENT"
private const val LAYOUT_XML_DEFAULT_FILE_NAME = "${VARIABLE_ANDROID_COMPONENT_SHORT_NAME}_$VARIABLE_NAME_SNAKE_CASE"


enum class FileType(val displayName: String, val extension: String, val defaultTemplate: String, val defaultFileName: String) {
    KOTLIN("Kotlin", "kt", KOTLIN_DEFAULT_TEMPLATE, KOTLIN_DEFAULT_FILE_NAME),
    LAYOUT_XML("Layout XML", "xml", LAYOUT_XML_DEFAULT_TEMPLATE, LAYOUT_XML_DEFAULT_FILE_NAME);

    override fun toString() = displayName
}