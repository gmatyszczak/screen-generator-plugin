package ui.newscreen

import model.Module

data class NewScreenState(
    val packageName: String,
    val modules: List<Module>,
    val selectedModule: Module?
)