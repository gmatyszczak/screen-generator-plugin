package newscreen

import com.nhaarman.mockitokotlin2.verify
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
    private lateinit var fileCreator: FileCreator

    @InjectMocks
    private lateinit var presenter: NewScreenPresenter

    @Test
    fun `on ok click`() {
        val screenName = "Test"

        presenter.onOkClick(screenName)

        verify(fileCreator).createScreenFiles(screenName)
        verify(viewMock).close()
    }
}