package ui.settings.dagger

import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import ui.settings.reducer.AddCategoryReducer
import ui.settings.reducer.AddCategoryReducerImpl
import ui.settings.reducer.AddCustomVariableReducer
import ui.settings.reducer.AddCustomVariableReducerImpl
import ui.settings.reducer.AddScreenElementReducer
import ui.settings.reducer.AddScreenElementReducerImpl
import ui.settings.reducer.ApplySettingsReducer
import ui.settings.reducer.ApplySettingsReducerImpl
import ui.settings.reducer.ChangeAndroidComponentReducer
import ui.settings.reducer.ChangeAndroidComponentReducerImpl
import ui.settings.reducer.ChangeCategoryNameReducer
import ui.settings.reducer.ChangeCategoryNameReducerImpl
import ui.settings.reducer.ChangeCustomVariableNameReducer
import ui.settings.reducer.ChangeCustomVariableNameReducerImpl
import ui.settings.reducer.ChangeFileNameReducer
import ui.settings.reducer.ChangeFileNameReducerImpl
import ui.settings.reducer.ChangeFileTypeReducer
import ui.settings.reducer.ChangeFileTypeReducerImpl
import ui.settings.reducer.ChangeNameReducer
import ui.settings.reducer.ChangeNameReducerImpl
import ui.settings.reducer.ChangeSourceSetReducer
import ui.settings.reducer.ChangeSourceSetReducerImpl
import ui.settings.reducer.ChangeSubdirectoryReducer
import ui.settings.reducer.ChangeSubdirectoryReducerImpl
import ui.settings.reducer.ChangeTemplateReducer
import ui.settings.reducer.ChangeTemplateReducerImpl
import ui.settings.reducer.ClickHelpReducer
import ui.settings.reducer.ClickHelpReducerImpl
import ui.settings.reducer.MoveDownCategoryReducer
import ui.settings.reducer.MoveDownCategoryReducerImpl
import ui.settings.reducer.MoveDownCustomVariableReducer
import ui.settings.reducer.MoveDownCustomVariableReducerImpl
import ui.settings.reducer.MoveDownScreenElementReducer
import ui.settings.reducer.MoveDownScreenElementReducerImpl
import ui.settings.reducer.MoveUpCategoryReducer
import ui.settings.reducer.MoveUpCategoryReducerImpl
import ui.settings.reducer.MoveUpCustomVariableReducer
import ui.settings.reducer.MoveUpCustomVariableReducerImpl
import ui.settings.reducer.MoveUpScreenElementReducer
import ui.settings.reducer.MoveUpScreenElementReducerImpl
import ui.settings.reducer.RemoveCategoryReducer
import ui.settings.reducer.RemoveCategoryReducerImpl
import ui.settings.reducer.RemoveCustomVariableReducer
import ui.settings.reducer.RemoveCustomVariableReducerImpl
import ui.settings.reducer.RemoveScreenElementReducer
import ui.settings.reducer.RemoveScreenElementReducerImpl
import ui.settings.reducer.ResetSettingsReducer
import ui.settings.reducer.ResetSettingsReducerImpl
import ui.settings.reducer.SelectCategoryReducer
import ui.settings.reducer.SelectCategoryReducerImpl
import ui.settings.reducer.SelectCustomVariableReducer
import ui.settings.reducer.SelectCustomVariableReducerImpl
import ui.settings.reducer.SelectScreenElementReducer
import ui.settings.reducer.SelectScreenElementReducerImpl
import ui.settings.reducer.UpdateCategoryReducer
import ui.settings.reducer.UpdateCategoryReducerImpl
import ui.settings.reducer.UpdateScreenElementReducer
import ui.settings.reducer.UpdateScreenElementReducerImpl
import javax.inject.Singleton

@Module
abstract class SettingsModule {

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

    @Binds
    abstract fun bindChangeSubdirectoryReducer(reducer: ChangeSubdirectoryReducerImpl): ChangeSubdirectoryReducer

    @Binds
    abstract fun bindChangeSourceSetReducer(reducer: ChangeSourceSetReducerImpl): ChangeSourceSetReducer

    @Binds
    abstract fun bindChangeCategoryNameReducer(reducer: ChangeCategoryNameReducerImpl): ChangeCategoryNameReducer

    @Binds
    abstract fun bindUpdateCategoryReducer(reducer: UpdateCategoryReducerImpl): UpdateCategoryReducer

    @Binds
    abstract fun bindAddCustomVariableReducer(reducer: AddCustomVariableReducerImpl): AddCustomVariableReducer

    @Binds
    abstract fun bindSelectCustomVariableReducer(reducer: SelectCustomVariableReducerImpl): SelectCustomVariableReducer

    @Binds
    abstract fun bindRemoveCustomVariableReducer(reducer: RemoveCustomVariableReducerImpl): RemoveCustomVariableReducer

    @Binds
    abstract fun bindMoveDownCustomVariableReducer(reducer: MoveDownCustomVariableReducerImpl): MoveDownCustomVariableReducer

    @Binds
    abstract fun bindMoveUpCustomVariableReducer(reducer: MoveUpCustomVariableReducerImpl): MoveUpCustomVariableReducer

    @Binds
    abstract fun bindChangeCustomVariableNameReducer(reducer: ChangeCustomVariableNameReducerImpl): ChangeCustomVariableNameReducer

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
