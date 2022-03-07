package ui.settings

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import model.ScreenElement
import ui.core.Reducer
import ui.core.ViewModel
import ui.settings.SettingsAction.ResetSettings
import javax.inject.Inject
import javax.inject.Provider

const val SAMPLE_SCREEN_NAME = "Sample"
const val SAMPLE_PACKAGE_NAME = "com.sample"
const val SAMPLE_ANDROID_COMPONENT = "Activity"

class SettingsViewModel @Inject constructor(
    state: MutableStateFlow<SettingsState>,
    effect: MutableSharedFlow<SettingsEffect>,
    actionFlow: MutableSharedFlow<SettingsAction>,
    reducers: Map<Class<out SettingsAction>, @JvmSuppressWildcards Provider<Reducer>>,
) : ViewModel<SettingsState, SettingsEffect, SettingsAction>(state, effect, actionFlow, reducers) {

    init {
        launch { actionFlow.emit(ResetSettings) }
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
