package ui.settings

import model.CategoryScreenElements
import model.CustomVariable
import model.ScreenElement

data class SettingsState(
    val isModified: Boolean = false,
    val selectedElementIndex: Int? = null,
    val fileNameRendered: String = "",
    val sampleCode: String = "",
    val categories: List<CategoryScreenElements> = emptyList(),
    val selectedCategoryIndex: Int? = null,
    val selectedCustomVariableIndex: Int? = null,
) {

    val selectedElement: ScreenElement?
        get() =
            if (selectedElementIndex != null && selectedCategoryIndex != null) {
                categories[selectedCategoryIndex].screenElements[selectedElementIndex]
            } else {
                null
            }

    val selectedCategoryScreenElements: CategoryScreenElements?
        get() =
            if (selectedCategoryIndex != null) {
                categories[selectedCategoryIndex]
            } else {
                null
            }

    val selectedCustomVariable: CustomVariable?
        get() =
            if (selectedCategoryIndex != null && selectedCustomVariableIndex != null) {
                categories[selectedCategoryIndex].category.customVariables[selectedCustomVariableIndex]
            } else {
                null
            }
}