package ui.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Provider
import kotlin.coroutines.CoroutineContext

abstract class ViewModel<State : Any, Effect : Any, Action : Any>(
    val state: MutableStateFlow<State>,
    val effect: MutableSharedFlow<Effect>,
    val actionFlow: MutableSharedFlow<Action>,
    private val reducers: Map<Class<out Action>, @JvmSuppressWildcards Provider<Reducer>>,
) : CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.UI + job

    init {
        observeAction()
    }

    fun clear() {
        job.cancel()
    }

    @Suppress("UNCHECKED_CAST")
    private fun observeAction() {
        actionFlow.onEach {
            when (val reducer = findReducer(it)) {
                is Reducer.Suspend<*> -> launch { (reducer as Reducer.Suspend<Action>).invoke(it) }
                is Reducer.Blocking<*> -> (reducer as Reducer.Blocking<Action>).invoke(it)
            }
        }.launchIn(this)
    }

    private fun findReducer(action: Action) =
        reducers[action::class.java]?.get() ?: error("No reducer for action: ${action::class.simpleName}")
}
