package ui.settings

import model.ScreenElement

data class SettingsState(
    val isModified: Boolean = false,
    val screenElements: List<ScreenElement> = emptyList(),
    val selectedElementIndex: Int? = null,
    val fileNameRendered: String = "",
    val sampleCode: String = ""
) {

    val selectedElement: ScreenElement?
        get() = selectedElementIndex?.let { screenElements[it] }
}