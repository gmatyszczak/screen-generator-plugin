package ui.settings.reducer

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.FileType
import model.ScreenElement
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import ui.settings.SettingsState

class ChangeFileTypeReducerImplTest : BaseReducerTest() {

    @Mock
    private lateinit var updateScreenElementReducerMock: UpdateScreenElementReducer

    private lateinit var reducer: ChangeFileTypeReducerImpl

    @Before
    fun setup() {
        reducer = ChangeFileTypeReducerImpl(state, effectMock, TestCoroutineScope(), updateScreenElementReducerMock)
    }

    @Test
    fun `if selected element not null on invoke`() {
        state.value = SettingsState(
            categories = listOf(
                CategoryScreenElements(
                    Category(),
                    listOf(ScreenElement())
                )
            ),
            selectedElementIndex = 0,
            selectedCategoryIndex = 0
        )

        reducer.invoke(FileType.LAYOUT_XML.ordinal)

        verify(updateScreenElementReducerMock).invoke(
            ScreenElement(
                fileType = FileType.LAYOUT_XML,
                fileNameTemplate = FileType.LAYOUT_XML.defaultFileName,
                template = FileType.LAYOUT_XML.defaultTemplate,
            )
        )
    }

    @Test
    fun `if selected element null on invoke`() {
        reducer.invoke(FileType.LAYOUT_XML.ordinal)

        verifyZeroInteractions(updateScreenElementReducerMock)
    }
}