package ui.settings.reducer

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ui.settings.SettingsState
import ui.settings.renderSampleCode
import ui.settings.renderSampleFileName

@ExperimentalCoroutinesApi
class UpdateScreenElementReducerImplTest : BaseReducerTest() {

    lateinit var reducer: UpdateScreenElementReducerImpl

    @Before
    fun setup() {
        reducer = UpdateScreenElementReducerImpl(state, effectMock, TestCoroutineScope())
    }

    @Test
    fun `on invoke`() {
        val initialState = SettingsState(
            categories = listOf(
                CategoryScreenElements(
                    Category(),
                    listOf(ScreenElement(name = "test1"))
                )
            ),
            selectedElementIndex = 0,
            selectedCategoryIndex = 0
        )
        val updatedElement = ScreenElement(name = "test2")

        state.value = initialState
        reducer.invoke(updatedElement)

        assertEquals(
            initialState.copy(
                categories = listOf(
                    CategoryScreenElements(
                        Category(),
                        listOf(updatedElement)
                    )
                ),
                fileNameRendered = updatedElement.renderSampleFileName(),
                sampleCode = updatedElement.renderSampleCode(),
                isModified = true
            ),
            state.value
        )
    }
}
