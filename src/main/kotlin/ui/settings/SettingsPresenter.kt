package ui.settings

import data.repository.SettingsRepository
import model.AndroidComponent
import model.FileType
import model.ScreenElement
import model.Settings
import util.swap

const val SAMPLE_SCREEN_NAME = "Sample"
const val SAMPLE_PACKAGE_NAME = "com.sample"
const val SAMPLE_ANDROID_COMPONENT = "Activity"

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

    fun onViewCreated() = view.setScreenElementDetailsEnabled(false)

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
            handleFileTypeSelection(selectedElement, false)
            view.showTemplate(selectedElement.template)
            updateSampleCode(selectedElement)
            view.addTextChangeListeners()
            view.setScreenElementDetailsEnabled(true)
        } else {
            currentSelectedScreenElement = null
            view.removeTextChangeListeners()
            view.showName("")
            view.showTemplate("")
            view.showSampleCode("")
            view.showFileNameTemplate("")
            view.showFileNameSample("")
            view.setScreenElementDetailsEnabled(false)
        }
    }

    fun onNameChange(name: String) {
        currentSelectedScreenElement?.let {
            it.name = name
            view.updateScreenElement(screenElements.indexOf(it), it)
            updateSampleCode(it)
            updateSampleFileName(it)
            isModified = true
        }
    }

    private fun updateSampleFileName(screenElement: ScreenElement) =
            view.showFileNameSample(screenElement.fileName(SAMPLE_SCREEN_NAME, SAMPLE_PACKAGE_NAME, SAMPLE_ANDROID_COMPONENT, currentActivityBaseClass))

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
            updateSampleCode(it)
            isModified = true
        }
    }

    private fun updateSampleCode(screenElement: ScreenElement) =
            view.showSampleCode(screenElement.body(SAMPLE_SCREEN_NAME, SAMPLE_PACKAGE_NAME, SAMPLE_ANDROID_COMPONENT, currentActivityBaseClass))

    fun onActivityBaseClassChange(text: String) {
        currentActivityBaseClass = text
        currentSelectedScreenElement?.let {
            updateSampleCode(it)
            updateSampleFileName(it)
        }
        isModified = true
    }

    fun onFragmentBaseClassChange(text: String) {
        currentFragmentBaseClass = text
        currentSelectedScreenElement?.let {
            updateSampleCode(it)
            updateSampleFileName(it)
        }
        isModified = true
    }

    fun onFileTypeSelect(fileType: FileType) {
        currentSelectedScreenElement?.let {
            it.fileType = fileType
            it.fileNameTemplate = fileType.defaultFileName
            handleFileTypeSelection(it, true)
            view.showTemplate(fileType.defaultTemplate)
            isModified = true
        }
    }

    private fun handleFileTypeSelection(screenElement: ScreenElement, addListener: Boolean) {
        view.showCodeTextFields(screenElement.fileType)
        when (screenElement.fileType) {
            FileType.KOTLIN -> view.swapToKotlinTemplateListener(addListener)
            FileType.LAYOUT_XML -> view.swapToXmlTemplateListener(addListener)
        }
        view.showFileNameTemplate(screenElement.fileNameTemplate)
        view.showFileNameSample(screenElement.fileName(SAMPLE_SCREEN_NAME, SAMPLE_PACKAGE_NAME, AndroidComponent.ACTIVITY.displayName, currentActivityBaseClass))
    }

    fun onFileNameChange(fileName: String) {
        currentSelectedScreenElement?.let {
            it.fileNameTemplate = fileName
            updateSampleFileName(it)
            isModified = true
        }
    }

    fun onHelpClick() = view.showHelp()
}