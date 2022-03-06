package data.repository

import com.intellij.openapi.project.Project
import data.ScreenGeneratorComponent
import model.Category
import model.CategoryScreenElements
import model.Settings
import javax.inject.Inject

class SettingsRepository @Inject constructor(private val project: Project) {

    fun loadCategoriesWithScreenElements(): List<CategoryScreenElements> {
        val settings = loadSettings()
        return settings.categories.map { category ->
            CategoryScreenElements(
                category,
                settings.screenElements.filter { it.categoryId == category.id }
            )
        }
    }

    fun update(settings: Settings) = ScreenGeneratorComponent.getInstance(project).run {
        this.settings = settings
    }

    fun loadCategories(): List<Category> = loadSettings().categories

    fun loadScreenElements(categoryId: Int) =
        loadSettings().screenElements.filter { it.categoryId == categoryId }

    private fun loadSettings() = ScreenGeneratorComponent.getInstance(project).settings
}
