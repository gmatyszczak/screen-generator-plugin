package ui.settings.reducer

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import data.repository.SettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.CategoryScreenElements
import model.ScreenElement
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mock
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class ResetSettingsReducerImplTest : BaseReducerTest() {

    @Mock
    private lateinit var settingsRepositoryMock: SettingsRepository

    private val categories = mutableListOf(
        CategoryScreenElements(
            Category(name = "test"),
            listOf(ScreenElement(name = "test"))
        )
    )

    private lateinit var reducer: ResetSettingsReducerImpl

    @Test
    fun `when categories not empty on invoke`() {
        whenever(settingsRepositoryMock.loadCategoriesWithScreenElements()) doReturn categories
        reducer = ResetSettingsReducerImpl(state, effectMock, TestCoroutineScope(), settingsRepositoryMock)

        reducer.invoke()

        assertEquals(
            SettingsState(
                categories = categories,
                selectedCategoryIndex = 0
            ),
            state.value
        )
    }

    @Test
    fun `when categories empty on invoke`() {
        reducer = ResetSettingsReducerImpl(state, effectMock, TestCoroutineScope(), settingsRepositoryMock)

        reducer.invoke()

        assertEquals(
            SettingsState(
                categories = emptyList(),
                selectedCategoryIndex = null
            ),
            state.value
        )
    }
}