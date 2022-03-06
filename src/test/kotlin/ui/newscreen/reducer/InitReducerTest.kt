package ui.newscreen.reducer

import data.file.CurrentPath
import data.file.PackageExtractor
import data.repository.ModuleRepository
import data.repository.SettingsRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import model.Category
import model.Module
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.newscreen.NewScreenAction.Init
import ui.newscreen.NewScreenState

class InitReducerTest {

    val packageName = "com.example"
    val moduleName = "domain"
    val moduleDomain = Module("MyApplication.$moduleName", moduleName)
    val moduleApp = Module("MyApplication.app", "app")
    val category = Category()
    val currentPath = CurrentPath("src", true, moduleDomain)
    val packageExtractor: PackageExtractor = mockk()
    val settingsRepository: SettingsRepository = mockk()
    val moduleRepository: ModuleRepository = mockk()
    val state = MutableStateFlow(NewScreenState())

    lateinit var reducer: InitReducer

    @BeforeEach
    fun setUp() {
        reducer = InitReducer(
            state,
            packageExtractor,
            moduleRepository,
            currentPath,
            settingsRepository
        )
    }

    @Test
    fun `on invoke`() {
        every { packageExtractor.extractFromCurrentPath() } returns packageName
        every { moduleRepository.getAllModules() } returns listOf(moduleApp, moduleDomain)
        every { settingsRepository.loadCategories() } returns listOf(category)

        reducer(Init)

        state.value shouldBeEqualTo NewScreenState(
            packageName,
            listOf(moduleApp, moduleDomain),
            moduleDomain,
            listOf(category),
            category
        )
    }
}
