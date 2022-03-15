package ui.newscreen.reducer

import data.file.ScreenElementCreator
import data.file.WriteActionDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import model.AndroidComponent
import ui.core.Reducer
import ui.newscreen.NewScreenAction.OkClicked
import ui.newscreen.NewScreenEffect
import javax.inject.Inject

class OkClickedReducer @Inject constructor(
    private val effect: MutableSharedFlow<NewScreenEffect>,
    private val screenElementCreator: ScreenElementCreator,
    private val writeActionDispatcher: WriteActionDispatcher,
) : Reducer.Suspend<OkClicked> {

    override suspend fun invoke(action: OkClicked) {
        writeActionDispatcher.dispatch {
            screenElementCreator.create(
                action.packageName,
                action.screenName,
                AndroidComponent.values()[action.androidComponentIndex],
                action.module,
                action.category,
                action.customVariablesMap,
            )
        }
        effect.emit(NewScreenEffect.Close)
    }
}
