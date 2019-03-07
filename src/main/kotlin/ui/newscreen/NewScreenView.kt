package ui.newscreen

interface NewScreenView {

    fun close()
    fun showPackage(packageName: String)
    fun showModules(modules: List<String>)
    fun selectModule(module: String)
}
