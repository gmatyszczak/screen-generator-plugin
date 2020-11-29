package ui.newscreen

import model.Category
import model.Module

data class NewScreenState(
    val packageName: String = "",
    val modules: List<Module> = emptyList(),
    val selectedModule: Module? = null,
    val categories: List<Category> = emptyList(),
    val selectedCategory: Category? = null
)