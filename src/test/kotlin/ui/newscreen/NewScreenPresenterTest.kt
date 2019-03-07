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

    private val currentPath = CurrentPath("src", true, moduleName)

    private lateinit var presenter: NewScreenPresenter

    @Before
    fun setUp() {
        presenter = NewScreenPresenter(viewMock, fileCreatorMock, packageExtractorMock, writeActionDispatcherMock, moduleRepositoryMock, currentPath)
    }

    @Test
    fun `on load view`() {
        val packageName = "com.example"
        whenever(packageExtractorMock.extractFromCurrentPath()).thenReturn(packageName)
        whenever(moduleRepositoryMock.getAllModules()).thenReturn(listOf("app", "domain"))

        presenter.onLoadView()

        verify(viewMock).showPackage(packageName)
        verify(viewMock).showModules(listOf("app", "domain"))
        verify(viewMock).selectModule("domain")
    }

    @Test
    fun `on ok click`() {
        whenever(writeActionDispatcherMock.dispatch(any())).thenAnswer { (it.arguments[0] as () -> Unit).invoke() }
        val screenName = "Test"
        val packageName = "com.test"

        presenter.onOkClick(packageName, screenName, AndroidComponent.ACTIVITY, moduleName)

        verify(fileCreatorMock).createScreenFiles(packageName, screenName, AndroidComponent.ACTIVITY, moduleName)
        verify(viewMock).close()
    }
}