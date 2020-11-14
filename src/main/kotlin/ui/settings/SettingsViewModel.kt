package ui.settings

import model.ScreenElement
import ui.settings.reducer.*
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
    private val changeActivityReducer: ChangeActivityReducer,
    private val changeFragmentReducer: ChangeFragmentReducer,
    private val clickHelpReducer: ClickHelpReducer
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
        is SettingsAction.ChangeActivity -> changeActivityReducer(action.text)
        is SettingsAction.ChangeFragment -> changeFragmentReducer(action.text)
        is SettingsAction.ClickHelp -> clickHelpReducer()
    }
}

fun ScreenElement.renderSampleFileName(activity: String) =
    "${
        fileName(
            SAMPLE_SCREEN_NAME,
            SAMPLE_PACKAGE_NAME,
            SAMPLE_ANDROID_COMPONENT,
            activity
        )
    }.${fileType.extension}"

fun ScreenElement.renderSampleCode(activity: String) =
    body(
        SAMPLE_SCREEN_NAME,
        SAMPLE_PACKAGE_NAME,
        SAMPLE_ANDROID_COMPONENT,
        activity
    )
