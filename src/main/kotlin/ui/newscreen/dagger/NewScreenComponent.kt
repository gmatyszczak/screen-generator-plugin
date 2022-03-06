package ui.newscreen.dagger

import com.intellij.openapi.project.Project
import dagger.BindsInstance
import dagger.Component
import data.file.CurrentPath
import ui.newscreen.NewScreenDialog
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NewScreenModule::class
    ]
)
interface NewScreenComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance project: Project, @BindsInstance currentPath: CurrentPath?): NewScreenComponent
    }

    fun inject(dialog: NewScreenDialog)
}
