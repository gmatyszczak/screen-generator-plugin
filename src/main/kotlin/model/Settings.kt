package model

import java.io.Serializable

private const val DEFAULT_BASE_ACTIVITY_CLASS = "androidx.appcompat.app.AppCompatActivity"
private const val DEFAULT_BASE_FRAGMENT_CLASS = "androidx.fragment.app.Fragment"
private const val DEFAULT_MVP_TEMPLATE = "package %packageName%\n\nimport %androidComponentFullName%\n\nclass %name%%androidComponentShortName% : %androidComponentLongName%"
private const val DEFAULT_VIEW_TEMPLATE = "package %packageName%\n\ninterface %name%%screenElement%"

data class Settings(val screenElements: List<ScreenElement>,
                    val activityBaseClass: String,
                    val fragmentBaseClass: String) : Serializable {

    companion object {
        fun getDefault() = Settings(
                listOf(
                        ScreenElement("MVP", DEFAULT_MVP_TEMPLATE, FileType.KOTLIN),
                        ScreenElement("Presenter", FileType.KOTLIN.defaultTemplate, FileType.KOTLIN),
                        ScreenElement("View", DEFAULT_VIEW_TEMPLATE, FileType.KOTLIN),
                        ScreenElement("layout", FileType.LAYOUT_XML.defaultTemplate, FileType.LAYOUT_XML)
                ),
                DEFAULT_BASE_ACTIVITY_CLASS,
                DEFAULT_BASE_FRAGMENT_CLASS
        )
    }
}