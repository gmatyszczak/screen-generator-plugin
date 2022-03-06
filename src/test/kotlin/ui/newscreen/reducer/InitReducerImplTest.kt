package ui.newscreen.reducer

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import data.file.CurrentPath
import data.file.PackageExtractor
import data.repository.ModuleRepository
import data.repository.SettingsRepository
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.Module
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import ui.newscreen.NewScreenState

class InitReducerImplTest : BaseReducerTest() {

    @Mock
    private lateinit var packageExtractorMock: PackageExtractor

    @Mock
    private lateinit var settingsRepositoryMock: SettingsRepository

    @Mock
    private lateinit var moduleRepositoryMock: ModuleRepository

    private lateinit var reducer: InitReducerImpl

    private val packageName = "com.example"
    private val moduleName = "domain"
    private val moduleDomain = Module("MyApplication.$moduleName", moduleName)
    private val moduleApp = Module("MyApplication.app", "app")
    private val category = Category()
    private val currentPath = CurrentPath("src", true, moduleDomain)

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
        whenever(packageExtractorMock.extractFromCurrentPath()).thenReturn(packageName)
        whenever(moduleRepositoryMock.getAllModules()).thenReturn(listOf(moduleApp, moduleDomain))
        whenever(settingsRepositoryMock.loadCategories()) doReturn listOf(category)

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
