package ui.newscreen

import model.Module

interface NewScreenView {

    fun close()
    fun showPackage(packageName: String)
    fun showModules(modules: List<Module>)
    fun selectModule(module: Module)
}
