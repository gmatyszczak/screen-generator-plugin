package settings

interface SettingsView {
    fun setUpListeners()
    fun addScreenElement(screenElement: ScreenElement)
    fun selectScreenElement(index: Int)
    fun showName(name: String)
    fun addNameChangeListener()
    fun removeCurrentNameChangeListener()
    fun updateScreenElement(index: Int, screenElement: ScreenElement)
    fun removeScreenElement(index: Int)
    fun updateComponent(settings: Settings)
    fun showScreenElements(screenElements: List<ScreenElement>)
    fun clearScreenElements()
}