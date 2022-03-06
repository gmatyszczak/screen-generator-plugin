package ui.newscreen.reducer

import data.file.CurrentPath
import data.file.PackageExtractor
import data.repository.ModuleRepository
import data.repository.SettingsRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.Module
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ui.newscreen.NewScreenState

class InitReducerImplTest : BaseReducerTest() {

    val packageName = "com.example"
    val moduleName = "domain"
    val moduleDomain = Module("MyApplication.$moduleName", moduleName)
    val moduleApp = Module("MyApplication.app", "app")
    val category = Category()
    val currentPath = CurrentPath("src", true, moduleDomain)
    val packageExtractorMock: PackageExtractor = mockk()
    val settingsRepositoryMock: SettingsRepository = mockk()
    val moduleRepositoryMock: ModuleRepository = mockk()

    lateinit var reducer: InitReducerImpl

    @Before
    fun setUp() {
        reducer = InitReducerImpl(
            state,
            effectMock,
            TestCoroutineScope(),
            packageExtractorMock,
            moduleRepositoryMock,
            currentPath,
            settingsRepositoryMock
        )
    }

    @Test
    fun `on invoke`() {
        every { packageExtractorMock.extractFromCurrentPath() } returns packageName
        every { moduleRepositoryMock.getAllModules() } returns listOf(moduleApp, moduleDomain)
        every { settingsRepositoryMock.loadCategories() } returns listOf(category)

        reducer()

        assertEquals(
            NewScreenState(
                packageName,
                listOf(moduleApp, moduleDomain),
                moduleDomain,
                listOf(category),
                category
            ),
            state.value
        )
    }
}
