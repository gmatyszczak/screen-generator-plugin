package ui.newscreen

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import data.file.CurrentPath
import data.file.FileCreator
import data.file.PackageExtractor
import data.file.WriteActionDispatcher
import data.repository.ModuleRepository
import model.AndroidComponent
import model.Module
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewScreenPresenterTest {

    @Mock
    private lateinit var viewMock: NewScreenView

    @Mock
    private lateinit var fileCreatorMock: FileCreator

    @Mock
    private lateinit var packageExtractorMock: PackageExtractor

    @Mock
    private lateinit var writeActionDispatcherMock: WriteActionDispatcher

    @Mock
    private lateinit var moduleRepositoryMock: ModuleRepository

    private val moduleName = "domain"
    private val moduleDomain = Module("MyApplication.$moduleName", moduleName)
    private val moduleApp = Module("MyApplication.app", "app")

    private val currentPath = CurrentPath("src", true, moduleDomain)

    private lateinit var presenter: NewScreenPresenter

    @Before
    fun setUp() {
        presenter = NewScreenPresenter(viewMock, fileCreatorMock, packageExtractorMock, writeActionDispatcherMock, moduleRepositoryMock, currentPath)
    }

    @Test
    fun `on load view`() {
        val packageName = "com.example"
        whenever(packageExtractorMock.extractFromCurrentPath()).thenReturn(packageName)
        whenever(moduleRepositoryMock.getAllModules()).thenReturn(listOf(moduleApp, moduleDomain))

        presenter.onLoadView()

        verify(viewMock).showPackage(packageName)
        verify(viewMock).showModules(listOf(moduleApp, moduleDomain))
        verify(viewMock).selectModule(moduleDomain)
    }

    @Test
    fun `on ok click`() {
        whenever(writeActionDispatcherMock.dispatch(any())).thenAnswer { (it.arguments[0] as () -> Unit).invoke() }
        val screenName = "Test"
        val packageName = "com.test"

        presenter.onOkClick(packageName, screenName, AndroidComponent.ACTIVITY, moduleDomain)

        verify(fileCreatorMock).createScreenFiles(packageName, screenName, AndroidComponent.ACTIVITY, moduleDomain)
        verify(viewMock).close()
    }
}