package ui.newscreen.reducer

import data.file.FileCreator
import data.file.WriteActionDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.AndroidComponent
import model.Category
import model.CustomVariable
import model.Module
import ui.newscreen.NewScreenEffect
import ui.newscreen.NewScreenState
import javax.inject.Inject

interface OkClickedReducer {
    operator fun invoke(
        packageName: String,
        screenName: String,
        androidComponentIndex: Int,
        module: Module,
        category: Category,
        customVariablesMap: Map<CustomVariable, String>
    )
}

class OkClickedReducerImpl @Inject constructor(
    private val state: MutableStateFlow<NewScreenState>,
    effect: MutableSharedFlow<NewScreenEffect>,
    scope: CoroutineScope,
    private val fileCreator: FileCreator,
    private val writeActionDispatcher: WriteActionDispatcher,
) : BaseReducer(state, effect, scope), OkClickedReducer {

    override fun invoke(
        packageName: String,
        screenName: String,
        androidComponentIndex: Int,
        module: Module,
        category: Category,
        customVariablesMap: Map<CustomVariable, String>
    ) {
        writeActionDispatcher.dispatch {
            fileCreator.createScreenFiles(
                packageName,
                screenName,
                AndroidComponent.values()[androidComponentIndex],
                module,
                category,
                customVariablesMap
            )
        }
        pushEffect(NewScreenEffect.Close)
    }
}