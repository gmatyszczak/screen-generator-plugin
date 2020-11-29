package ui.newscreen.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.newscreen.NewScreenEffect
import ui.newscreen.NewScreenState
import javax.inject.Inject

interface CategoryIndexChangedReducer {

    operator fun invoke(index: Int)
}

class CategoryIndexChangedReducerImpl @Inject constructor(
    private val state: MutableStateFlow<NewScreenState>,
    effect: MutableSharedFlow<NewScreenEffect>,
    scope: CoroutineScope,
) : BaseReducer(state, effect, scope), CategoryIndexChangedReducer {

    override fun invoke(index: Int) = pushState {
        copy(
            selectedCategory = categories[index]
        )
    }
}
