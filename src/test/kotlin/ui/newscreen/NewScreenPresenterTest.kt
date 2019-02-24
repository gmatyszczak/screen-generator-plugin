package ui.newscreen

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import data.file.FileCreator
import data.file.PackageExtractor
import data.file.WriteActionDispatcher
import model.AndroidComponent
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
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

    @InjectMocks
    private lateinit var presenter: NewScreenPresenter

    @Test
    fun `on load view`() {
        val packageName = "com.example"
        whenever(packageExtractorMock.extractFromCurrentPath()).thenReturn(packageName)

        presenter.onLoadView()

        verify(viewMock).showPackage(packageName)
    }

    @Test
    fun `on ok click`() {
        whenever(writeActionDispatcherMock.dispatch(any())).thenAnswer { (it.arguments[0] as () -> Unit).invoke() }
        val screenName = "Test"
        val packageName = "com.test"

        presenter.onOkClick(packageName, screenName, AndroidComponent.ACTIVITY)

        verify(fileCreatorMock).createScreenFiles(packageName, screenName, AndroidComponent.ACTIVITY)
        verify(viewMock).close()
    }
}