package ui.settings.reducer

import data.repository.SettingsRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class ResetSettingsReducerImplTest : BaseReducerTest() {

    val settingsRepositoryMock: SettingsRepository = mockk(relaxUnitFun = true)

    val categories = mutableListOf(
        CategoryScreenElements(
            Category(name = "test"),
            listOf(ScreenElement(name = "test"))
        )
    )

    lateinit var reducer: ResetSettingsReducerImpl

    @Test
    fun `when categories not empty on invoke`() {
        every { settingsRepositoryMock.loadCategoriesWithScreenElements() } returns categories
        reducer = ResetSettingsReducerImpl(state, effectMock, TestCoroutineScope(), settingsRepositoryMock)

        reducer.invoke()

        state.value shouldBeEqualTo SettingsState(
            categories = categories,
            selectedCategoryIndex = 0
        )
    }

    @Test
    fun `when categories empty on invoke`() {
        every { settingsRepositoryMock.loadCategoriesWithScreenElements() } returns emptyList()
        reducer = ResetSettingsReducerImpl(state, effectMock, TestCoroutineScope(), settingsRepositoryMock)

        reducer.invoke()

        state.value shouldBeEqualTo SettingsState(
            categories = emptyList(),
            selectedCategoryIndex = null
        )
    }
}
