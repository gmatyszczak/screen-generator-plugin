package ui.settings.reducer

import app.cash.turbine.test
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import model.Category
import model.CategoryScreenElements
import model.FileType
import model.ScreenElement
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsAction
import ui.settings.SettingsAction.ChangeFileType
import ui.settings.SettingsState

class ChangeFileTypeReducerTest {

    val state = MutableStateFlow(SettingsState())
    val actionFlow = MutableSharedFlow<SettingsAction>()
    lateinit var reducer: ChangeFileTypeReducer

    @BeforeEach
    fun setup() {
        reducer = ChangeFileTypeReducer(state, actionFlow)
    }

    @Test
    fun `if selected element not null on invoke`() = runBlockingTest {
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

        actionFlow.test {
            reducer.invoke(ChangeFileType(FileType.LAYOUT_XML.ordinal))

            awaitItem() shouldBeEqualTo SettingsAction.UpdateScreenElement(
                ScreenElement(
                    fileType = FileType.LAYOUT_XML,
                    fileNameTemplate = FileType.LAYOUT_XML.defaultFileName,
                    template = FileType.LAYOUT_XML.defaultTemplate,
                )
            )
            cancelAndIgnoreRemainingEvents()
        }
    }
}
