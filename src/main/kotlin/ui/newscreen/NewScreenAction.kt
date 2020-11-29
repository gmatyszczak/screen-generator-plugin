package ui.newscreen

import model.Category
import model.Module

sealed class NewScreenAction {
    data class OkClicked(
        val packageName: String,
        val screenName: String,
        val androidComponentIndex: Int,
        val module: Module,
        val category: Category
    ) : NewScreenAction()
    data class CategoryIndexChanged(val index: Int): NewScreenAction()
}