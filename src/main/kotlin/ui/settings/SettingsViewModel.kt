package ui.settings

import model.ScreenElement
import ui.settings.reducer.AddCategoryReducer
import ui.settings.reducer.AddCustomVariableReducer
import ui.settings.reducer.AddScreenElementReducer
import ui.settings.reducer.ApplySettingsReducer
import ui.settings.reducer.ChangeAndroidComponentReducer
import ui.settings.reducer.ChangeCategoryNameReducer
import ui.settings.reducer.ChangeCustomVariableNameReducer
import ui.settings.reducer.ChangeFileNameReducer
import ui.settings.reducer.ChangeFileTypeReducer
import ui.settings.reducer.ChangeNameReducer
import ui.settings.reducer.ChangeSourceSetReducer
import ui.settings.reducer.ChangeSubdirectoryReducer
import ui.settings.reducer.ChangeTemplateReducer
import ui.settings.reducer.ClickHelpReducer
import ui.settings.reducer.MoveDownCategoryReducer
import ui.settings.reducer.MoveDownCustomVariableReducer
import ui.settings.reducer.MoveDownScreenElementReducer
import ui.settings.reducer.MoveUpCategoryReducer
import ui.settings.reducer.MoveUpCustomVariableReducer
import ui.settings.reducer.MoveUpScreenElementReducer
import ui.settings.reducer.RemoveCategoryReducer
import ui.settings.reducer.RemoveCustomVariableReducer
import ui.settings.reducer.RemoveScreenElementReducer
import ui.settings.reducer.ResetSettingsReducer
import ui.settings.reducer.SelectCategoryReducer
import ui.settings.reducer.SelectCustomVariableReducer
import ui.settings.reducer.SelectScreenElementReducer
import javax.inject.Inject

const val SAMPLE_SCREEN_NAME = "Sample"
const val SAMPLE_PACKAGE_NAME = "com.sample"
const val SAMPLE_ANDROID_COMPONENT = "Activity"

class SettingsViewModel @Inject constructor(
    private val applySettingsReducer: ApplySettingsReducer,
    private val resetSettingsReducer: ResetSettingsReducer,
    private val addScreenElementReducer: AddScreenElementReducer,
    private val removeScreenElementReducer: RemoveScreenElementReducer,
    private val moveDownScreenElementReducer: MoveDownScreenElementReducer,
    private val moveUpScreenElementReducer: MoveUpScreenElementReducer,
    private val selectScreenElementReducer: SelectScreenElementReducer,
    private val changeNameReducer: ChangeNameReducer,
    private val changeFileNameReducer: ChangeFileNameReducer,
    private val changeTemplateReducer: ChangeTemplateReducer,
    private val changeFileTypeReducer: ChangeFileTypeReducer,
    private val clickHelpReducer: ClickHelpReducer,
    private val changeAndroidComponentReducer: ChangeAndroidComponentReducer,
    private val addCategoryReducer: AddCategoryReducer,
    private val selectCategoryReducer: SelectCategoryReducer,
    private val removeCategoryReducer: RemoveCategoryReducer,
    private val moveUpCategoryReducer: MoveUpCategoryReducer,
    private val moveDownCategoryReducer: MoveDownCategoryReducer,
    private val changeSubdirectoryReducer: ChangeSubdirectoryReducer,
    private val changeSourceSetReducer: ChangeSourceSetReducer,
    private val changeCategoryNameReducer: ChangeCategoryNameReducer,
    private val addCustomVariableReducer: AddCustomVariableReducer,
    private val selectCustomVariableReducer: SelectCustomVariableReducer,
    private val removeCustomVariableReducer: RemoveCustomVariableReducer,
    private val moveDownCustomVariableReducer: MoveDownCustomVariableReducer,
    private val moveUpCustomVariableReducer: MoveUpCustomVariableReducer,
    private val changeCustomVariableNameReducer: ChangeCustomVariableNameReducer,
) {

    init {
        resetSettingsReducer()
    }

    fun reduce(action: SettingsAction) = when (action) {
        is SettingsAction.ApplySettings -> applySettingsReducer()
        is SettingsAction.ResetSettings -> resetSettingsReducer()
        is SettingsAction.AddScreenElement -> addScreenElementReducer()
        is SettingsAction.RemoveScreenElement -> removeScreenElementReducer(action.index)
        is SettingsAction.MoveDownScreenElement -> moveDownScreenElementReducer(action.index)
        is SettingsAction.MoveUpScreenElement -> moveUpScreenElementReducer(action.index)
        is SettingsAction.SelectScreenElement -> selectScreenElementReducer(action.index)
        is SettingsAction.ChangeName -> changeNameReducer(action.text)
        is SettingsAction.ChangeFileName -> changeFileNameReducer(action.text)
        is SettingsAction.ChangeTemplate -> changeTemplateReducer(action.text)
        is SettingsAction.ChangeFileType -> changeFileTypeReducer(action.index)
        is SettingsAction.ClickHelp -> clickHelpReducer()
        is SettingsAction.ChangeAndroidComponent -> changeAndroidComponentReducer(action.index)
        is SettingsAction.AddCategory -> addCategoryReducer()
        is SettingsAction.SelectCategory -> selectCategoryReducer(action.index)
        is SettingsAction.RemoveCategory -> removeCategoryReducer(action.index)
        is SettingsAction.MoveUpCategory -> moveUpCategoryReducer(action.index)
        is SettingsAction.MoveDownCategory -> moveDownCategoryReducer(action.index)
        is SettingsAction.ChangeSubdirectory -> changeSubdirectoryReducer(action.text)
        is SettingsAction.ChangeSourceSet -> changeSourceSetReducer(action.text)
        is SettingsAction.ChangeCategoryName -> changeCategoryNameReducer(action.text)
        is SettingsAction.AddCustomVariable -> addCustomVariableReducer()
        is SettingsAction.SelectCustomVariable -> selectCustomVariableReducer(action.index)
        is SettingsAction.RemoveCustomVariable -> removeCustomVariableReducer(action.index)
        is SettingsAction.MoveDownCustomVariable -> moveDownCustomVariableReducer(action.index)
        is SettingsAction.MoveUpCustomVariable -> moveUpCustomVariableReducer(action.index)
        is SettingsAction.ChangeCustomVariableName -> changeCustomVariableNameReducer(action.text)
    }
}

fun ScreenElement.renderSampleFileName() =
    "${
    fileName(
        SAMPLE_SCREEN_NAME,
        SAMPLE_PACKAGE_NAME,
        SAMPLE_ANDROID_COMPONENT,
        emptyMap()
    )
    }.${fileType.extension}"

fun ScreenElement.renderSampleCode() =
    body(
        SAMPLE_SCREEN_NAME,
        SAMPLE_PACKAGE_NAME,
        SAMPLE_ANDROID_COMPONENT,
        emptyMap()
    )
