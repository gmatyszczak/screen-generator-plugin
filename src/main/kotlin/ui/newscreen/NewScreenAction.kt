package ui.newscreen

import model.Category
import model.CustomVariable
import model.Module

sealed class NewScreenAction {
    data class OkClicked(
        val packageName: String,
        val screenName: String,
        val androidComponentIndex: Int,
        val module: Module,
        val category: Category,
        val customVariablesMap: Map<CustomVariable, String>
    ) : NewScreenAction()
    data class CategoryIndexChanged(val index: Int) : NewScreenAction()
}
