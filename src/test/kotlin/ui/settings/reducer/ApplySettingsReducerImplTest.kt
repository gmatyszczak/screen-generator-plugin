package ui.settings.reducer

import data.repository.SettingsRepository
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import model.Settings
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class ApplySettingsReducerImplTest : BaseReducerTest() {

    val settingsRepositoryMock: SettingsRepository = mockk(relaxUnitFun = true)
    lateinit var reducer: ApplySettingsReducerImpl

    val categoryScreenElement = CategoryScreenElements(
        Category(),
        listOf(ScreenElement(name = "test"))
    )

    val initialState = SettingsState(
        categories = listOf(categoryScreenElement),
        selectedCategoryIndex = 0,
        isModified = true
    )

    @BeforeEach
    fun setUp() {
        state.value = initialState
        reducer = ApplySettingsReducerImpl(state, effectMock, TestCoroutineScope(), settingsRepositoryMock)
    }

    @Test
    fun `on invoke`() {
        reducer.invoke()

        state.value shouldBeEqualTo initialState.copy(isModified = false)
        verify {
            settingsRepositoryMock.update(
                Settings(
                    screenElements = initialState.categories.flatMap { it.screenElements }.toMutableList(),
                    categories = initialState.categories.map { it.category }.toMutableList()
                )
            )
        }
    }
}
