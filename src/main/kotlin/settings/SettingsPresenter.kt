package settings

const val UNNAMED_ELEMENT = "Unnamed Element"

class SettingsPresenter(private val view: SettingsView) {

    val screenElements = mutableListOf<ScreenElement>()
    var currentSelectedScreenElement: ScreenElement? = null
    var isModified = false

    fun onLoadView(settings: Settings) {
        screenElements.addAll(settings.screenElements)
        view.setUpListeners()
        view.showScreenElements(settings.screenElements)
    }

    fun onAddClick() {
        val newScreenElement = ScreenElement(UNNAMED_ELEMENT)
        screenElements.add(newScreenElement)
        view.addScreenElement(newScreenElement)
        view.selectLastScreenElement()
        isModified = true
    }

    fun onDeleteClick(index: Int) {
        screenElements.removeAt(index)
        view.removeScreenElement(index)
        isModified = true
    }

    fun onScreenElementSelect(index: Int) {
        if (index in 0 until screenElements.size) {
            val selectedElement = screenElements[index]
            view.removeCurrentNameChangeListener()
            view.showName(selectedElement.name)
            view.addNameChangeListener()
            currentSelectedScreenElement = selectedElement
        } else {
            currentSelectedScreenElement = null
            view.removeCurrentNameChangeListener()
            view.showName("")
        }
    }

    fun onNameChange(name: String) {
        currentSelectedScreenElement?.let {
            it.name = name
            view.updateScreenElement(screenElements.indexOf(it), it)
            isModified = true
        }
    }

    fun onApplySettings() {
        view.updateComponent(Settings(screenElements.toList()))
        isModified = false
    }
}