package model

import model.Anchor.CLASS
import model.Anchor.FILE
import model.Anchor.FUNCTION
import model.Anchor.OBJECT
import model.Anchor.TAG

private val KOTLIN_DEFAULT_TEMPLATE =
    "package ${Variable.PACKAGE_NAME.value}\n\nclass ${Variable.NAME.value}${Variable.SCREEN_ELEMENT.value}"
private const val LAYOUT_XML_DEFAULT_TEMPLATE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
    "<FrameLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
    "    android:layout_width=\"match_parent\"\n" +
    "    android:layout_height=\"match_parent\">\n" +
    "\n" +
    "</FrameLayout>"
private val KOTLIN_DEFAULT_FILE_NAME = "${Variable.NAME.value}${Variable.SCREEN_ELEMENT.value}"
private val LAYOUT_XML_DEFAULT_FILE_NAME =
    "${Variable.ANDROID_COMPONENT_NAME_LOWER_CASE.value}_${Variable.NAME_SNAKE_CASE.value}"

enum class FileType(
    val displayName: String,
    val extension: String,
    val defaultTemplate: String,
    val defaultFileName: String,
    val anchors: List<Anchor>,
) {
    KOTLIN(
        displayName = "Kotlin",
        extension = "kt",
        defaultTemplate = KOTLIN_DEFAULT_TEMPLATE,
        defaultFileName = KOTLIN_DEFAULT_FILE_NAME,
        anchors = listOf(
            FILE,
            CLASS,
            OBJECT,
            FUNCTION,
        ),
    ),
    LAYOUT_XML(
        displayName = "Layout XML",
        extension = "xml",
        defaultTemplate = LAYOUT_XML_DEFAULT_TEMPLATE,
        defaultFileName = LAYOUT_XML_DEFAULT_FILE_NAME,
        anchors = listOf(
            FILE,
            TAG,
        ),
    );

    override fun toString() = displayName
}
