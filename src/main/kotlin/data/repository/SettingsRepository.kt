package data.repository

import com.intellij.openapi.project.Project
import data.ScreenGeneratorComponent
import model.CategoryScreenElements
import model.Settings
import javax.inject.Inject

interface SettingsRepository {
    fun loadSettings(): Settings
    fun loadCategoriesWithScreenElements(): List<CategoryScreenElements>
    fun update(settings: Settings)
}

class SettingsRepositoryImpl @Inject constructor(private val project: Project) : SettingsRepository {

    override fun loadSettings() = ScreenGeneratorComponent.getInstance(project).settings

    override fun loadCategoriesWithScreenElements(): List<CategoryScreenElements> {
        val settings = loadSettings()
        return settings.categories.map { category ->
            CategoryScreenElements(
                category,
                settings.screenElements.filter { it.categoryId == category.id })
        }
    }

    override fun update(settings: Settings) = ScreenGeneratorComponent.getInstance(project).run {
        this.settings = settings
    }


}