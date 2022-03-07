package ui.newscreen.dagger

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.core.Reducer
import ui.newscreen.NewScreenAction
import ui.newscreen.NewScreenAction.CategoryIndexChanged
import ui.newscreen.NewScreenAction.Init
import ui.newscreen.NewScreenAction.OkClicked
import ui.newscreen.NewScreenEffect
import ui.newscreen.NewScreenState
import ui.newscreen.reducer.CategoryIndexChangedReducer
import ui.newscreen.reducer.InitReducer
import ui.newscreen.reducer.OkClickedReducer
import javax.inject.Singleton

@Module
abstract class NewScreenModule {

    @Binds
    @IntoMap
    @NewScreenActionKey(Init::class)
    abstract fun bindInitReducer(reducer: InitReducer): Reducer

    @Binds
    @IntoMap
    @NewScreenActionKey(OkClicked::class)
    abstract fun bindOkClickedReducer(reducer: OkClickedReducer): Reducer

    @Binds
    @IntoMap
    @NewScreenActionKey(CategoryIndexChanged::class)
    abstract fun bindCategoryIndexChangedReducer(reducer: CategoryIndexChangedReducer): Reducer

    companion object {

        @Provides
        @Singleton
        fun provideState() = MutableStateFlow(NewScreenState())

        @Provides
        @Singleton
        fun provideEffect() = MutableSharedFlow<NewScreenEffect>()

        @Provides
        @Singleton
        fun provideAction() = MutableSharedFlow<NewScreenAction>()
    }
}
