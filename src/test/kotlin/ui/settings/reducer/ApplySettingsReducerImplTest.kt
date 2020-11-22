package ui.settings.reducer

import com.nhaarman.mockitokotlin2.verify
import data.repository.SettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import model.Settings
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class ApplySettingsReducerImplTest : BaseReducerTest() {

    @Mock
    private lateinit var settingsRepositoryMock: SettingsRepository

    private lateinit var reducer: ApplySettingsReducerImpl

    private val categoryScreenElement = CategoryScreenElements(
        Category(),
        listOf(ScreenElement(name = "test"))
    )

    private val initialState = SettingsState(
        categories = listOf(categoryScreenElement),
        selectedCategoryIndex = 0,
        isModified = true
    )

    @Before
    fun setUp() {
        state.value = initialState
        reducer = ApplySettingsReducerImpl(state, effectMock, TestCoroutineScope(), settingsRepositoryMock)
    }

    @Test
    fun `on invoke`() {
        reducer.invoke()

        assertEquals(initialState.copy(isModified = false), state.value)
        verify(settingsRepositoryMock).update(
            Settings(
                screenElements = initialState.categories.flatMap { it.screenElements }.toMutableList(),
                categories = initialState.categories.map { it.category }.toMutableList()
            )
        )
    }
}