package ui.settings

import data.repository.SettingsRepository
import model.FileType
import model.ScreenElement
import model.Settings

const val SAMPLE_SCREEN_NAME = "Sample"
const val SAMPLE_PACKAGE_NAME = "com.sample"

class SettingsPresenter(private val view: SettingsView,
                        private val settingsRepository: SettingsRepository) {

    val screenElements = mutableListOf<ScreenElement>()
    var currentSelectedScreenElement: ScreenElement? = null
    var isModified = false
    lateinit var initialSettings: Settings
    lateinit var currentActivityBaseClass: String
    lateinit var currentFragmentBaseClass: String

    fun onLoadView() {
        initialSettings = settingsRepository.loadSettings()
        resetToInitialSettings()
        view.setUpListeners()
        view.showScreenElements(screenElements)
        view.showActivityBaseClass(currentActivityBaseClass)
        view.showFragmentBaseClass(currentFragmentBaseClass)
        view.addBaseClassTextChangeListeners()
    }

    private fun resetToInitialSettings() {
        screenElements.clear()
        initialSettings.screenElements.mapTo(screenElements) { it.copy() }
        currentActivityBaseClass = initialSettings.activityBaseClass
        currentFragmentBaseClass = initialSettings.fragmentBaseClass
    }

    fun onAddClick() {
        val newScreenElement = ScreenElement.getDefault()
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
            currentSelectedScreenElement = selectedElement
            view.removeTextChangeListeners()
            view.showName(selectedElement.name)
            view.showFileType(selectedElement.fileType)
            handleFileTypeSelection(selectedElement.fileType, false)
            view.showTemplate(selectedElement.template)
            view.showSampleCode(selectedElement.body(SAMPLE_SCREEN_NAME, SAMPLE_PACKAGE_NAME))
            view.addTextChangeListeners()
        } else {
            currentSelectedScreenElement = null
            view.removeTextChangeListeners()
            view.showName("")
            view.showTemplate("")
            view.showSampleCode("")
        }
    }

    fun onNameChange(name: String) {
        currentSelectedScreenElement?.let {
            it.name = name
            view.updateScreenElement(screenElements.indexOf(it), it)
            view.showSampleCode(it.body(SAMPLE_SCREEN_NAME, SAMPLE_PACKAGE_NAME))
            isModified = true
        }
    }

    fun onApplySettings() {
        initialSettings = Settings(screenElements.toList(), currentActivityBaseClass, currentFragmentBaseClass)
        resetToInitialSettings()
        settingsRepository.update(initialSettings)
        isModified = false
    }

    fun onResetSettings() {
        resetToInitialSettings()
        view.clearScreenElements()
        view.showScreenElements(screenElements)
        view.removeBaseClassTextChangeListeners()
        view.showActivityBaseClass(currentActivityBaseClass)
        view.showFragmentBaseClass(currentFragmentBaseClass)
        view.addBaseClassTextChangeListeners()
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

    fun onTemplateChange(text: String) {
        currentSelectedScreenElement?.let {
            it.template = text
            view.showSampleCode(it.body(SAMPLE_SCREEN_NAME, SAMPLE_PACKAGE_NAME))
            isModified = true
        }
    }

    fun onActivityBaseClassChange(text: String) {
        currentActivityBaseClass = text
        isModified = true
    }

    fun onFragmentBaseClassChange(text: String) {
        currentFragmentBaseClass = text
        isModified = true
    }

    private fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
        val temp = this[index1]
        this[index1] = this[index2]
        this[index2] = temp
    }

    fun onFileTypeSelect(fileType: FileType?) {
        currentSelectedScreenElement?.let {
            if (fileType != null) {
                it.fileType = fileType
                handleFileTypeSelection(fileType, true)
                view.showTemplate(fileType.defaultTemplate)
                isModified = true
            }
        }
    }

    private fun handleFileTypeSelection(fileType: FileType, swapListener: Boolean) {
        when (fileType) {
            FileType.KOTLIN -> {
                view.hideXmlTextFields()
                view.showKotlinTextFields()
                if (swapListener) view.swapToKotlinTemplateListener()
            }
            FileType.LAYOUT_XML -> {
                view.hideKotlinTextFields()
                view.showXmlTextFields()
                if (swapListener) view.swapToXmlTemplateListener()
            }
        }
    }
}