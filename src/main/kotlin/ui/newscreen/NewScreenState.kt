package ui.newscreen

import model.Category
import model.Module

data class NewScreenState(
    val packageName: String,
    val modules: List<Module>,
    val selectedModule: Module?,
    val categories: List<Category>
)