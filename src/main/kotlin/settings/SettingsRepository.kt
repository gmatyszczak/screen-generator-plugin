package settings

import com.intellij.openapi.project.Project

interface SettingsRepository {
    fun loadScreenElements(): List<ScreenElement>
}

class SettingsRepositoryImpl(private val project: Project) : SettingsRepository {

    override fun loadScreenElements() = ScreenGeneratorComponent.getInstance(project).settings.screenElements
}