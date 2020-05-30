package ui.settings

import data.repository.SettingsRepository
import model.Category
import model.FileType
import model.ScreenElement
import model.Settings
import util.swap

const val SAMPLE_SCREEN_NAME = "Sample"
const val SAMPLE_PACKAGE_NAME = "com.sample"
const val SAMPLE_ANDROID_COMPONENT = "Activity"

class SettingsPresenter(
        private val view: SettingsView,
        private val settingsRepository: SettingsRepository
) {

    val categories = mutableListOf<Category>()
    var currentSelectedCategory: Category? = null
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
        view.showCategories(categories)
        if (currentSelectedCategory == null) {
            currentSelectedCategory = categories[0]
        }
        currentSelectedCategory?.let {
            view.showCategoryName(it.name)
            view.showScreenElements(it.screenElements)
        }

        view.showActivityBaseClass(currentActivityBaseClass)
        view.showFragmentBaseClass(currentFragmentBaseClass)
        view.addBaseClassTextChangeListeners()
    }

    fun onViewCreated() = view.setScreenElementDetailsEnabled(false)

    private fun resetToInitialSettings() {
        categories.clear()
        initialSettings.categories.mapTo(categories) { it.copy() }
        currentSelectedCategory = initialSettings.currentlySelectedCategory
        currentActivityBaseClass = initialSettings.activityBaseClass
        currentFragmentBaseClass = initialSettings.fragmentBaseClass
    }

    fun onCategoryAddClick() {
        val newCategory = Category.getDefault()
        categories.add(newCategory)
        view.addCategory(newCategory)
        view.selectCategory(categories.size - 1)
        isModified = true
    }

    fun onCategoryDeleteClick(index: Int) {
        categories.removeAt(index)
        view.removeCategory(index)
        isModified = true
    }

    fun onCategorySelect(index: Int) {
        if (index in 0 until categories.size) {
            val selectedCategory = categories[index]
            currentSelectedCategory = selectedCategory
            view.removeTextChangeListeners()
            view.showCategoryName(selectedCategory.name)
            view.showScreenElements(selectedCategory.screenElements)
            view.addTextChangeListeners()
        } else {
            view.showCategoryName("")
            currentSelectedCategory = null
        }
    }

    fun onCategoryMoveDownClick(index: Int) = onCategoryMoveClick(index, index + 1)

    fun onCategoryMoveUpClick(index: Int) = onCategoryMoveClick(index, index - 1)

    private fun onCategoryMoveClick(index: Int, destinationIndex: Int) {
        categories.swap(index, destinationIndex)
        view.updateCategory(index, categories[index])
        view.updateCategory(destinationIndex, categories[destinationIndex])
        view.selectCategory(destinationIndex)
        isModified = true
    }

    fun onScreenElementAddClick() {
        val newScreenElement = ScreenElement.getDefault()
        currentSelectedCategory?.let {
            it.screenElements.add(newScreenElement)
            view.addScreenElement(newScreenElement)
            view.selectScreenElement(it.screenElements.size - 1)
            isModified = true
        }
    }

    fun onScreenElementDeleteClick(index: Int) {
        currentSelectedCategory?.let {
            it.screenElements.removeAt(index)
            view.removeScreenElement(index)
            isModified = true
        }
    }

    fun onScreenElementSelect(index: Int) {
        currentSelectedCategory?.let {
            if (index in 0 until it.screenElements.size) {
                val selectedElement = it.screenElements[index]
                currentSelectedScreenElement = selectedElement
                view.removeTextChangeListeners()
                view.showScreenElementName(selectedElement.name)
                view.showFileType(selectedElement.fileType)
                handleFileTypeSelection(selectedElement, false)
                view.showTemplate(selectedElement.template)
                updateSampleCode(selectedElement)
                view.addTextChangeListeners()
                view.setScreenElementDetailsEnabled(true)
            } else {
                clearScreenElementData()
            }
        } ?: kotlin.run {
            clearScreenElementData()
        }
    }

    private fun clearScreenElementData() {
        currentSelectedScreenElement = null
        view.removeTextChangeListeners()
        view.showScreenElementName("")
        view.showTemplate("")
        view.showSampleCode("")
        view.showFileNameTemplate("")
        view.showFileNameSample("")
        view.setScreenElementDetailsEnabled(false)
    }

    fun onCategoryNameChange(name: String) {
        currentSelectedCategory?.let {
            it.name = name
            view.updateCategory(categories.indexOf(it), it)
            isModified = true
        }
    }

    fun onScreenElementNameChange(name: String) {
        currentSelectedCategory?.let { selectedCategory ->
            currentSelectedScreenElement?.let {
                it.name = name
                view.updateScreenElement(selectedCategory.screenElements.indexOf(it), it)
                updateSampleCode(it)
                updateSampleFileName(it)
                isModified = true
            }
        }
    }

    private fun updateSampleFileName(screenElement: ScreenElement) {
        val fileName = screenElement.fileName(SAMPLE_SCREEN_NAME, SAMPLE_PACKAGE_NAME, SAMPLE_ANDROID_COMPONENT, currentActivityBaseClass)
        val fileExtension = screenElement.fileType.extension
        view.showFileNameSample("$fileName.$fileExtension")
    }

    fun onApplySettings() {
        initialSettings = Settings(categories.toMutableList(), categories[0], currentActivityBaseClass, currentFragmentBaseClass)
        resetToInitialSettings()
        settingsRepository.update(initialSettings)
        isModified = false
    }

    fun onResetSettings() {
        resetToInitialSettings()
        view.clearCategories()
        view.clearScreenElements()
        view.showCategories(categories)
        currentSelectedCategory?.let {
            view.showScreenElements(it.screenElements)
        }
        view.removeBaseClassTextChangeListeners()
        view.showActivityBaseClass(currentActivityBaseClass)
        view.showFragmentBaseClass(currentFragmentBaseClass)
        view.addBaseClassTextChangeListeners()
        isModified = false
    }

    fun onScreenElementMoveDownClick(index: Int) = onScreenElementMoveClick(index, index + 1)

    fun onScreenElementMoveUpClick(index: Int) = onScreenElementMoveClick(index, index - 1)

    private fun onScreenElementMoveClick(index: Int, destinationIndex: Int) {
        currentSelectedCategory?.let {
            it.screenElements.swap(index, destinationIndex)
            view.updateScreenElement(index, it.screenElements[index])
            view.updateScreenElement(destinationIndex, it.screenElements[destinationIndex])
            view.selectScreenElement(destinationIndex)
            isModified = true
        }
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
        updateSampleFileName(screenElement)
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