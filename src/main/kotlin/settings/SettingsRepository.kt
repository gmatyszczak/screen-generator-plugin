package settings

import com.intellij.openapi.project.Project
import model.ScreenElement

interface SettingsRepository {
    fun loadScreenElements(): List<ScreenElement>
    fun update(screenElements: List<ScreenElement>)
}

class SettingsRepositoryImpl(private val project: Project) : SettingsRepository {

    override fun loadScreenElements() = ScreenGeneratorComponent.getInstance(project).screenElements

    override fun update(screenElements: List<ScreenElement>) = ScreenGeneratorComponent.getInstance(project).run {
        this.screenElements = screenElements
    }
}