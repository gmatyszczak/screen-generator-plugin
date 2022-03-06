package ui.settings.reducer

import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.FileType
import model.ScreenElement
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsState

class ChangeFileTypeReducerImplTest : BaseReducerTest() {

    val updateScreenElementReducerMock: UpdateScreenElementReducer = mockk(relaxUnitFun = true)
    lateinit var reducer: ChangeFileTypeReducerImpl

    @BeforeEach
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

        verify {
            updateScreenElementReducerMock.invoke(
                ScreenElement(
                    fileType = FileType.LAYOUT_XML,
                    fileNameTemplate = FileType.LAYOUT_XML.defaultFileName,
                    template = FileType.LAYOUT_XML.defaultTemplate,
                )
            )
        }
    }

    @Test
    fun `if selected element null on invoke`() {
        reducer.invoke(FileType.LAYOUT_XML.ordinal)

        verify { updateScreenElementReducerMock wasNot Called }
    }
}
