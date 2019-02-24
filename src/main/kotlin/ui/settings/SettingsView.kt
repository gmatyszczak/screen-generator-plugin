package ui.settings

import model.FileType
import model.ScreenElement

interface SettingsView {
    fun setUpListeners()
    fun addScreenElement(screenElement: ScreenElement)
    fun selectScreenElement(index: Int)
    fun showName(name: String)
    fun addTextChangeListeners()
    fun removeTextChangeListeners()
    fun updateScreenElement(index: Int, screenElement: ScreenElement)
    fun removeScreenElement(index: Int)
    fun showScreenElements(screenElements: List<ScreenElement>)
    fun clearScreenElements()
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
}