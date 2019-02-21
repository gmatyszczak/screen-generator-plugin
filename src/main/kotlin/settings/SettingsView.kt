package settings

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
}