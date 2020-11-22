package ui.newscreen.dagger

import dagger.Binds
import dagger.Module
import data.file.*
import data.repository.*

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
}