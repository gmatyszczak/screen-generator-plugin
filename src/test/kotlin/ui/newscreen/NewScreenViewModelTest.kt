package ui.newscreen

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import data.file.CurrentPath
import data.file.FileCreator
import data.file.PackageExtractor
import data.file.WriteActionDispatcher
import data.repository.ModuleRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import model.AndroidComponent
import model.Module
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NewScreenViewModelTest {

    @Mock
    private lateinit var fileCreatorMock: FileCreator

    @Mock
    private lateinit var packageExtractorMock: PackageExtractor

    @Mock
    private lateinit var writeActionDispatcherMock: WriteActionDispatcher

    @Mock
    private lateinit var moduleRepositoryMock: ModuleRepository


    private val packageName = "com.example"
    private val moduleName = "domain"
    private val moduleDomain = Module("MyApplication.$moduleName", moduleName)
    private val moduleApp = Module("MyApplication.app", "app")

    private val currentPath = CurrentPath("src", true, moduleDomain)

    private lateinit var viewModel: NewScreenViewModel

    @Before
    fun setUp() {
        whenever(packageExtractorMock.extractFromCurrentPath()).thenReturn(packageName)
        whenever(moduleRepositoryMock.getAllModules()).thenReturn(listOf(moduleApp, moduleDomain))

        viewModel = NewScreenViewModel(
            fileCreatorMock,
            packageExtractorMock,
            writeActionDispatcherMock,
            moduleRepositoryMock,
            currentPath
        )
    }

    @Test
    fun `on init`() {
        assertEquals(NewScreenState(packageName, listOf(moduleApp, moduleDomain), moduleDomain), viewModel.state.value)
    }

    @Test
    fun `on ok click`() {
        whenever(writeActionDispatcherMock.dispatch(any())).thenAnswer { (it.arguments[0] as () -> Unit).invoke() }
        val screenName = "Test"
        val packageName = "com.test"

        viewModel.onOkClick(packageName, screenName, AndroidComponent.ACTIVITY.ordinal, moduleDomain)

        // TODO test effect
        verify(fileCreatorMock).createScreenFiles(packageName, screenName, AndroidComponent.ACTIVITY, moduleDomain)
    }
}