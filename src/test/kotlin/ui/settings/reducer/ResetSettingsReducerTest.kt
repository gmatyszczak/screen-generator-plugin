package ui.settings.reducer

import data.repository.SettingsRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ui.settings.SettingsAction.ResetSettings
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class ResetSettingsReducerTest {

    val state = MutableStateFlow(SettingsState())
    val settingsRepository: SettingsRepository = mockk(relaxUnitFun = true)

    val categories = mutableListOf(
        CategoryScreenElements(
            Category(name = "test"),
            listOf(ScreenElement(name = "test"))
        )
    )

    val reducer = ResetSettingsReducer(state, settingsRepository)

    @Test
    fun `when categories not empty on invoke`() {
        every { settingsRepository.loadCategoriesWithScreenElements() } returns categories

        reducer.invoke(ResetSettings)

        state.value shouldBeEqualTo SettingsState(
            categories = categories,
            selectedCategoryIndex = 0
        )
    }

    @Test
    fun `when categories empty on invoke`() {
        every { settingsRepository.loadCategoriesWithScreenElements() } returns emptyList()

        reducer.invoke(ResetSettings)

        state.value shouldBeEqualTo SettingsState(
            categories = emptyList(),
            selectedCategoryIndex = null
        )
    }
}
