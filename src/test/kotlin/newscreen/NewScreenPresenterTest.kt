package newscreen

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import newscreen.files.ProjectStructure
import newscreen.files.SourceRoot
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
    private lateinit var projectStructureMock: ProjectStructure

    @Mock
    private lateinit var sourceRootMock: SourceRoot

    @InjectMocks
    private lateinit var presenter: NewScreenPresenter

    @Test
    fun `on ok click`() {
        whenever(projectStructureMock.findSourceRoots()).thenReturn(listOf(sourceRootMock))
        whenever(sourceRootMock.path).thenReturn("src")
        val screenName = "Test"
        val packageName = "com.test"

        presenter.onOkClick(packageName, screenName)

        verify(fileCreatorMock).createScreenFiles(sourceRootMock, packageName, screenName)
        verify(viewMock).close()
    }
}