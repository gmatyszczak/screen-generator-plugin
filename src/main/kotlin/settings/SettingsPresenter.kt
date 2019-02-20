package settings

const val UNNAMED_ELEMENT = "Unnamed Element"

class SettingsPresenter(private val view: SettingsView) {

    val screenElements = mutableListOf<ScreenElement>()
    var currentSelectedScreenElement: ScreenElement? = null
    var isModified = false
    lateinit var initialSettings: Settings

    fun onLoadView(settings: Settings) {
        initialSettings = settings
        copyScreenElementsFromInitialSettings()
        view.setUpListeners()
        view.showScreenElements(screenElements)
    }

    private fun copyScreenElementsFromInitialSettings() {
        screenElements.clear()
        initialSettings.screenElements.mapTo(screenElements) { it.copy() }
    }

    fun onAddClick() {
        val newScreenElement = ScreenElement(UNNAMED_ELEMENT)
        screenElements.add(newScreenElement)
        view.addScreenElement(newScreenElement)
        view.selectScreenElement(screenElements.size - 1)
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
        initialSettings = Settings(screenElements.toList())
        copyScreenElementsFromInitialSettings()
        view.updateComponent(initialSettings)
        isModified = false
    }

    fun onResetSettings() {
        copyScreenElementsFromInitialSettings()
        view.clearScreenElements()
        view.showScreenElements(screenElements)
        isModified = false
    }

    fun onMoveDownClick(index: Int) = onMoveClick(index, index + 1)

    fun onMoveUpClick(index: Int) = onMoveClick(index, index - 1)

    private fun onMoveClick(index: Int, destinationIndex: Int) {
        screenElements.swap(index, destinationIndex)
        view.updateScreenElement(index, screenElements[index])
        view.updateScreenElement(destinationIndex, screenElements[destinationIndex])
        view.selectScreenElement(destinationIndex)
        isModified = true
    }

    private fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
        val temp = this[index1]
        this[index1] = this[index2]
        this[index2] = temp
    }
}