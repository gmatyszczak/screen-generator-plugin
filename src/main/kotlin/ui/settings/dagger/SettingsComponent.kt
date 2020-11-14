package ui.settings.dagger

import com.intellij.openapi.project.Project
import dagger.BindsInstance
import dagger.Component
import ui.settings.SettingsConfigurable
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        SettingsModule::class
    ]
)
interface SettingsComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance project: Project): SettingsComponent
    }

    fun inject(configurable: SettingsConfigurable)
}