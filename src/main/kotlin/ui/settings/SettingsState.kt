package ui.settings

import model.Category
import model.ScreenElement

data class SettingsState(
    val isModified: Boolean = false,
    val screenElements: List<ScreenElement> = emptyList(),
    val selectedElementIndex: Int? = null,
    val fileNameRendered: String = "",
    val sampleCode: String = "",
    val categories: List<Category> = emptyList(),
    val selectedCategoryIndex: Int? = null,
) {

    val selectedElement: ScreenElement?
        get() = selectedElementIndex?.let { screenElements[it] }
}