package model

import java.io.Serializable

private const val DEFAULT_BASE_ACTIVITY_CLASS = "androidx.appcompat.app.AppCompatActivity"
private const val DEFAULT_BASE_FRAGMENT_CLASS = "androidx.fragment.app.Fragment"
private const val DEFAULT_MVP_TEMPLATE = "package $VARIABLE_PACKAGE_NAME\n\nimport $VARIABLE_ANDROID_COMPONENT_FULL_NAME\n\nclass $VARIABLE_NAME$VARIABLE_ANDROID_COMPONENT_SHORT_NAME : $VARIABLE_ANDROID_COMPONENT_LONG_NAME"
private const val DEFAULT_VIEW_TEMPLATE = "package $VARIABLE_PACKAGE_NAME\n\ninterface $VARIABLE_NAME$VARIABLE_SCREEN_ELEMENT"

data class Settings(val screenElements: List<ScreenElement>,
                    val activityBaseClass: String,
                    val fragmentBaseClass: String) : Serializable {

    companion object {
        fun getDefault() = Settings(
                listOf(
                        ScreenElement("MVP", DEFAULT_MVP_TEMPLATE, FileType.KOTLIN, "$VARIABLE_NAME$VARIABLE_ANDROID_COMPONENT_SHORT_NAME"),
                        ScreenElement("Presenter", FileType.KOTLIN.defaultTemplate, FileType.KOTLIN, FileType.KOTLIN.defaultFileName),
                        ScreenElement("View", DEFAULT_VIEW_TEMPLATE, FileType.KOTLIN, FileType.KOTLIN.defaultFileName),
                        ScreenElement("layout", FileType.LAYOUT_XML.defaultTemplate, FileType.LAYOUT_XML, FileType.LAYOUT_XML.defaultFileName)
                ),
                DEFAULT_BASE_ACTIVITY_CLASS,
                DEFAULT_BASE_FRAGMENT_CLASS
        )
    }
}