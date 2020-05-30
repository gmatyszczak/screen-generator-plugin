package ui.newscreen

import model.Category

interface NewScreenView {

    fun close()
    fun showCategories(categories: List<Category>)
    fun selectCategory(category: Category)
    fun showPackage(packageName: String)
    fun showModules(modules: List<String>)
    fun selectModule(module: String)
}
