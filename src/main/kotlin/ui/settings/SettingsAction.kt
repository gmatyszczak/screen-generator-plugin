package ui.settings

import model.Category
import model.ScreenElement

sealed class SettingsAction {

    object ApplySettings : SettingsAction()
    object ResetSettings : SettingsAction()
    object AddScreenElement : SettingsAction()
    data class RemoveScreenElement(val index: Int) : SettingsAction()
    data class MoveDownScreenElement(val index: Int) : SettingsAction()
    data class MoveUpScreenElement(val index: Int) : SettingsAction()
    data class SelectScreenElement(val index: Int) : SettingsAction()
    data class ChangeName(val text: String) : SettingsAction()
    data class ChangeFileName(val text: String) : SettingsAction()
    data class ChangeTemplate(val text: String) : SettingsAction()
    data class ChangeFileType(val index: Int) : SettingsAction()
    data class ChangeAndroidComponent(val index: Int) : SettingsAction()
    data class SelectCategory(val index: Int) : SettingsAction()
    data class RemoveCategory(val index: Int) : SettingsAction()
    data class MoveUpCategory(val index: Int) : SettingsAction()
    data class MoveDownCategory(val index: Int) : SettingsAction()
    data class ChangeSubdirectory(val text: String) : SettingsAction()
    data class ChangeSourceSet(val text: String) : SettingsAction()
    data class ChangeCategoryName(val text: String) : SettingsAction()
    data class SelectCustomVariable(val index: Int) : SettingsAction()
    data class RemoveCustomVariable(val index: Int) : SettingsAction()
    data class MoveDownCustomVariable(val index: Int) : SettingsAction()
    data class MoveUpCustomVariable(val index: Int) : SettingsAction()
    data class ChangeCustomVariableName(val text: String) : SettingsAction()
    data class UpdateScreenElement(val element: ScreenElement) : SettingsAction()
    data class UpdateCategory(val category: Category) : SettingsAction()

    object ClickHelp : SettingsAction()
    object AddCategory : SettingsAction()
    object AddCustomVariable : SettingsAction()
}
