package ui.settings.dagger

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.core.Reducer
import ui.settings.SettingsAction
import ui.settings.SettingsAction.AddCategory
import ui.settings.SettingsAction.AddCustomVariable
import ui.settings.SettingsAction.AddScreenElement
import ui.settings.SettingsAction.ApplySettings
import ui.settings.SettingsAction.ChangeAnchor
import ui.settings.SettingsAction.ChangeAnchorName
import ui.settings.SettingsAction.ChangeAnchorPosition
import ui.settings.SettingsAction.ChangeAndroidComponent
import ui.settings.SettingsAction.ChangeCategoryName
import ui.settings.SettingsAction.ChangeCustomVariableName
import ui.settings.SettingsAction.ChangeFileName
import ui.settings.SettingsAction.ChangeFileType
import ui.settings.SettingsAction.ChangeName
import ui.settings.SettingsAction.ChangeScreenElementType
import ui.settings.SettingsAction.ChangeSourceSet
import ui.settings.SettingsAction.ChangeSubdirectory
import ui.settings.SettingsAction.ChangeTemplate
import ui.settings.SettingsAction.ClickHelp
import ui.settings.SettingsAction.MoveDownCategory
import ui.settings.SettingsAction.MoveDownCustomVariable
import ui.settings.SettingsAction.MoveDownScreenElement
import ui.settings.SettingsAction.MoveUpCategory
import ui.settings.SettingsAction.MoveUpCustomVariable
import ui.settings.SettingsAction.MoveUpScreenElement
import ui.settings.SettingsAction.RemoveCategory
import ui.settings.SettingsAction.RemoveCustomVariable
import ui.settings.SettingsAction.RemoveScreenElement
import ui.settings.SettingsAction.ResetSettings
import ui.settings.SettingsAction.SelectCategory
import ui.settings.SettingsAction.SelectCustomVariable
import ui.settings.SettingsAction.SelectScreenElement
import ui.settings.SettingsAction.UpdateCategory
import ui.settings.SettingsAction.UpdateScreenElement
import ui.settings.SettingsEffect
import ui.settings.SettingsState
import ui.settings.reducer.AddCategoryReducer
import ui.settings.reducer.AddCustomVariableReducer
import ui.settings.reducer.AddScreenElementReducer
import ui.settings.reducer.ApplySettingsReducer
import ui.settings.reducer.ChangeAnchorNameReducer
import ui.settings.reducer.ChangeAnchorPositionReducer
import ui.settings.reducer.ChangeAnchorReducer
import ui.settings.reducer.ChangeAndroidComponentReducer
import ui.settings.reducer.ChangeCategoryNameReducer
import ui.settings.reducer.ChangeCustomVariableNameReducer
import ui.settings.reducer.ChangeFileNameReducer
import ui.settings.reducer.ChangeFileTypeReducer
import ui.settings.reducer.ChangeNameReducer
import ui.settings.reducer.ChangeScreenElementTypeReducer
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
import ui.settings.reducer.UpdateCategoryReducer
import ui.settings.reducer.UpdateScreenElementReducer
import javax.inject.Singleton

@Module
abstract class SettingsModule {

    @Binds
    @IntoMap
    @SettingsActionKey(SelectScreenElement::class)
    abstract fun bindSelectScreenElementReducer(reducer: SelectScreenElementReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(UpdateScreenElement::class)
    abstract fun bindUpdateScreenElementReducer(reducer: UpdateScreenElementReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(ApplySettings::class)
    abstract fun bindApplySettingsReducer(reducer: ApplySettingsReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(AddScreenElement::class)
    abstract fun bindAddScreenElementReducer(reducer: AddScreenElementReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(RemoveScreenElement::class)
    abstract fun bindRemoveScreenElementReducer(reducer: RemoveScreenElementReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(MoveDownScreenElement::class)
    abstract fun bindMoveDownScreenElementReducer(reducer: MoveDownScreenElementReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(MoveUpScreenElement::class)
    abstract fun bindMoveUpScreenElementReducer(reducer: MoveUpScreenElementReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(ChangeName::class)
    abstract fun bindChangeNameReducer(reducer: ChangeNameReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(ChangeFileName::class)
    abstract fun bindChangeFileNameReducer(reducer: ChangeFileNameReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(ChangeTemplate::class)
    abstract fun bindChangeTemplateReducer(reducer: ChangeTemplateReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(ChangeFileType::class)
    abstract fun bindChangeFileTypeReducer(reducer: ChangeFileTypeReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(ClickHelp::class)
    abstract fun bindClickHelpReducer(reducer: ClickHelpReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(ResetSettings::class)
    abstract fun bindResetSettingsReducer(reducer: ResetSettingsReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(ChangeAndroidComponent::class)
    abstract fun bindChangeAndroidComponentReducer(reducer: ChangeAndroidComponentReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(AddCategory::class)
    abstract fun bindAddCategoryReducer(reducer: AddCategoryReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(SelectCategory::class)
    abstract fun bindSelectCategoryReducer(reducer: SelectCategoryReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(RemoveCategory::class)
    abstract fun bindRemoveCategoryReducer(reducer: RemoveCategoryReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(MoveUpCategory::class)
    abstract fun bindMoveUpCategoryReducer(reducer: MoveUpCategoryReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(MoveDownCategory::class)
    abstract fun bindMoveDownCategoryReducer(reducer: MoveDownCategoryReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(ChangeSubdirectory::class)
    abstract fun bindChangeSubdirectoryReducer(reducer: ChangeSubdirectoryReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(ChangeSourceSet::class)
    abstract fun bindChangeSourceSetReducer(reducer: ChangeSourceSetReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(ChangeCategoryName::class)
    abstract fun bindChangeCategoryNameReducer(reducer: ChangeCategoryNameReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(UpdateCategory::class)
    abstract fun bindUpdateCategoryReducer(reducer: UpdateCategoryReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(AddCustomVariable::class)
    abstract fun bindAddCustomVariableReducer(reducer: AddCustomVariableReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(SelectCustomVariable::class)
    abstract fun bindSelectCustomVariableReducer(reducer: SelectCustomVariableReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(RemoveCustomVariable::class)
    abstract fun bindRemoveCustomVariableReducer(reducer: RemoveCustomVariableReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(MoveDownCustomVariable::class)
    abstract fun bindMoveDownCustomVariableReducer(reducer: MoveDownCustomVariableReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(MoveUpCustomVariable::class)
    abstract fun bindMoveUpCustomVariableReducer(reducer: MoveUpCustomVariableReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(ChangeCustomVariableName::class)
    abstract fun bindChangeCustomVariableNameReducer(reducer: ChangeCustomVariableNameReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(ChangeScreenElementType::class)
    abstract fun bindChangeScreenElementTypeReducer(reducer: ChangeScreenElementTypeReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(ChangeAnchor::class)
    abstract fun bindChangeAnchorReducer(reducer: ChangeAnchorReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(ChangeAnchorPosition::class)
    abstract fun bindChangeAnchorPositionReducer(reducer: ChangeAnchorPositionReducer): Reducer

    @Binds
    @IntoMap
    @SettingsActionKey(ChangeAnchorName::class)
    abstract fun bindChangeAnchorNameReducer(reducer: ChangeAnchorNameReducer): Reducer

    companion object {

        @Provides
        @Singleton
        fun provideState() = MutableStateFlow(SettingsState())

        @Provides
        @Singleton
        fun provideEffect() = MutableSharedFlow<SettingsEffect>()

        @Provides
        @Singleton
        fun provideAction() = MutableSharedFlow<SettingsAction>()
    }
}
