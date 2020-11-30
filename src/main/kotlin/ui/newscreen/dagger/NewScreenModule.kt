package ui.newscreen.dagger

import dagger.Binds
import dagger.Module
import dagger.Provides
import data.file.*
import data.repository.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.newscreen.NewScreenEffect
import ui.newscreen.NewScreenState
import ui.newscreen.reducer.*
import javax.inject.Singleton

@Module
abstract class NewScreenModule {

    @Binds
    abstract fun bindProjectStructure(projectStructure: ProjectStructureImpl): ProjectStructure

    @Binds
    abstract fun bindSourceRootRepository(repository: SourceRootRepositoryImpl): SourceRootRepository

    @Binds
    abstract fun bindSettingsRepository(repository: SettingsRepositoryImpl): SettingsRepository

    @Binds
    abstract fun bindPackageExtractor(extractor: PackageExtractorImpl): PackageExtractor

    @Binds
    abstract fun bindWriteActionDispatcher(dispatcher: WriteActionDispatcherImpl): WriteActionDispatcher

    @Binds
    abstract fun bindModuleRepository(repository: ModuleRepositoryImpl): ModuleRepository

    @Binds
    abstract fun bindFileCreator(fileCreator: FileCreatorImpl): FileCreator

    @Binds
    abstract fun bindInitReducer(reducer: InitReducerImpl): InitReducer

    @Binds
    abstract fun bindOkClickedReducer(reducer: OkClickedReducerImpl): OkClickedReducer

    @Binds
    abstract fun bindCategoryIndexChangedReducer(reducer: CategoryIndexChangedReducerImpl): CategoryIndexChangedReducer

    companion object {

        @Provides
        @Singleton
        fun provideState() = MutableStateFlow(NewScreenState())

        @Provides
        @Singleton
        fun provideEffect() = MutableSharedFlow<NewScreenEffect>(replay = 0)

        @Provides
        @Singleton
        fun provideScope(): CoroutineScope = MainScope()
    }
}