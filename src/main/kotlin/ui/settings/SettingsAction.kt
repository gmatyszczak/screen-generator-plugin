package ui.settings

sealed class SettingsAction {

    object ApplySettings : SettingsAction()
    object ResetSettings : SettingsAction()
    object AddScreenElement : SettingsAction()
    data class RemoveScreenElement(val index: Int) : SettingsAction()
    data class MoveDownScreenElement(val index: Int) : SettingsAction()
    data class MoveUpScreenElement(val index: Int) : SettingsAction()
    data class SelectScreenElement(val index: Int) : SettingsAction()
    data class ChangeName(val text: String): SettingsAction()
    data class ChangeFileName(val text: String): SettingsAction()
    data class ChangeTemplate(val text: String): SettingsAction()
    data class ChangeFileType(val index: Int): SettingsAction()
    data class ChangeAndroidComponent(val index: Int) : SettingsAction()
    data class SelectCategory(val index: Int) : SettingsAction()
    data class RemoveCategory(val index: Int) : SettingsAction()
    data class MoveUpCategory(val index: Int) : SettingsAction()
    data class MoveDownCategory(val index: Int) : SettingsAction()
    data class ChangeSubdirectory(val text: String) : SettingsAction()
    data class ChangeSourceSet(val text: String) : SettingsAction()

    object ClickHelp: SettingsAction()
    object AddCategory : SettingsAction()
}