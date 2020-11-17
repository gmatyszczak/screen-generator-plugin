package ui.settings.dagger

import dagger.Binds
import dagger.Module
import dagger.Provides
import data.repository.SettingsRepository
import data.repository.SettingsRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import ui.settings.reducer.*
import javax.inject.Singleton

@Module
abstract class SettingsModule {

    @Binds
    abstract fun bindSettingsRepository(repository: SettingsRepositoryImpl): SettingsRepository

    @Binds
    abstract fun bindSelectScreenElementReducer(reducer: SelectScreenElementReducerImpl): SelectScreenElementReducer

    @Binds
    abstract fun bindUpdateScreenElementReducer(reducer: UpdateScreenElementReducerImpl): UpdateScreenElementReducer

    @Binds
    abstract fun bindApplySettingsReducer(reducer: ApplySettingsReducerImpl): ApplySettingsReducer

    @Binds
    abstract fun bindAddScreenElementReducer(reducer: AddScreenElementReducerImpl): AddScreenElementReducer

    @Binds
    abstract fun bindRemoveScreenElementReducer(reducer: RemoveScreenElementReducerImpl): RemoveScreenElementReducer

    @Binds
    abstract fun bindMoveDownScreenElementReducer(reducer: MoveDownScreenElementReducerImpl): MoveDownScreenElementReducer

    @Binds
    abstract fun bindMoveUpScreenElementReducer(reducer: MoveUpScreenElementReducerImpl): MoveUpScreenElementReducer

    @Binds
    abstract fun bindChangeNameReducer(reducer: ChangeNameReducerImpl): ChangeNameReducer

    @Binds
    abstract fun bindChangeFileNameReducer(reducer: ChangeFileNameReducerImpl): ChangeFileNameReducer

    @Binds
    abstract fun bindChangeTemplateReducer(reducer: ChangeTemplateReducerImpl): ChangeTemplateReducer

    @Binds
    abstract fun bindChangeFileTypeReducer(reducer: ChangeFileTypeReducerImpl): ChangeFileTypeReducer

    @Binds
    abstract fun bindClickHelpReducer(reducer: ClickHelpReducerImpl): ClickHelpReducer

    @Binds
    abstract fun bindResetSettingsReducer(reducer: ResetSettingsReducerImpl): ResetSettingsReducer

    @Binds
    abstract fun bindChangeAndroidComponentReducer(reducer: ChangeAndroidComponentReducerImpl): ChangeAndroidComponentReducer

    @Binds
    abstract fun bindAddCategoryReducer(reducer: AddCategoryReducerImpl): AddCategoryReducer

    @Binds
    abstract fun bindSelectCategoryReducer(reducer: SelectCategoryReducerImpl): SelectCategoryReducer

    @Binds
    abstract fun bindRemoveCategoryReducer(reducer: RemoveCategoryReducerImpl): RemoveCategoryReducer

    @Binds
    abstract fun bindMoveUpCategoryReducer(reducer: MoveUpCategoryReducerImpl): MoveUpCategoryReducer

    @Binds
    abstract fun bindMoveDownCategoryReducer(reducer: MoveDownCategoryReducerImpl): MoveDownCategoryReducer

    companion object {

        @Provides
        @Singleton
        fun provideState() = MutableStateFlow(SettingsState())

        @Provides
        @Singleton
        fun provideEffect() = MutableSharedFlow<SettingsEffect>(replay = 0)

        @Provides
        @Singleton
        fun provideScope(): CoroutineScope = MainScope()
    }
}