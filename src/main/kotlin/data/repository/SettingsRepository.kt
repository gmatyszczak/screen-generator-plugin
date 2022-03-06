package data.repository

import com.intellij.openapi.project.Project
import data.ScreenGeneratorComponent
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import model.Settings
import javax.inject.Inject

interface SettingsRepository {
    fun loadCategoriesWithScreenElements(): List<CategoryScreenElements>
    fun update(settings: Settings)
    fun loadCategories(): List<Category>
    fun loadScreenElements(categoryId: Int): List<ScreenElement>
}

class SettingsRepositoryImpl @Inject constructor(private val project: Project) : SettingsRepository {

    override fun loadCategoriesWithScreenElements(): List<CategoryScreenElements> {
        val settings = loadSettings()
        return settings.categories.map { category ->
            CategoryScreenElements(
                category,
                settings.screenElements.filter { it.categoryId == category.id }
            )
        }
    }

    override fun update(settings: Settings) = ScreenGeneratorComponent.getInstance(project).run {
        this.settings = settings
    }

    override fun loadCategories() = loadSettings().categories

    override fun loadScreenElements(categoryId: Int) =
        loadSettings().screenElements.filter { it.categoryId == categoryId }

    private fun loadSettings() = ScreenGeneratorComponent.getInstance(project).settings
}
