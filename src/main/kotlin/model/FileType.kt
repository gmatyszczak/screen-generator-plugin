package model

private const val KOTLIN_DEFAULT_TEMPLATE = "package %packageName%\n\nclass %name%%screenElement%"
private const val LAYOUT_XML_DEFAULT_TEMPLATE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
        "<FrameLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
        "    android:layout_width=\"match_parent\"\n" +
        "    android:layout_height=\"match_parent\">\n" +
        "\n" +
        "</FrameLayout>"


enum class FileType(val displayName: String, val extension: String, val defaultTemplate: String) {
    KOTLIN("Kotlin", "kt", KOTLIN_DEFAULT_TEMPLATE),
    LAYOUT_XML("Layout XML", "xml", LAYOUT_XML_DEFAULT_TEMPLATE);

    override fun toString() = displayName
}