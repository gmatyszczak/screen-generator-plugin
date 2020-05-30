package ui.settings

import model.Category
import model.FileType
import model.ScreenElement

interface SettingsView {
    fun setUpListeners()

    fun addCategory(category: Category)
    fun selectCategory(index: Int)
    fun updateCategory(index: Int, category: Category)
    fun removeCategory(index: Int)
    fun showCategories(categories: List<Category>)
    fun clearCategories()
    fun showCategoryName(name: String)

    fun addScreenElement(screenElement: ScreenElement)
    fun selectScreenElement(index: Int)
    fun updateScreenElement(index: Int, screenElement: ScreenElement)
    fun removeScreenElement(index: Int)
    fun showScreenElements(screenElements: List<ScreenElement>)
    fun clearScreenElements()
    fun showScreenElementName(name: String)

    fun addTextChangeListeners()
    fun removeTextChangeListeners()
    fun showSampleCode(text: String)
    fun showTemplate(template: String)
    fun showActivityBaseClass(text: String)
    fun showFragmentBaseClass(text: String)
    fun addBaseClassTextChangeListeners()
    fun removeBaseClassTextChangeListeners()
    fun showFileType(fileType: FileType)
    fun showCodeTextFields(fileType: FileType)
    fun swapToKotlinTemplateListener(addListener: Boolean)
    fun swapToXmlTemplateListener(addListener: Boolean)
    fun showFileNameTemplate(text: String)
    fun showFileNameSample(text: String)
    fun showHelp()
    fun setScreenElementDetailsEnabled(isEnabled: Boolean)
}