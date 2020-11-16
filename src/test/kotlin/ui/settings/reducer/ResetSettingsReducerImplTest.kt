package ui.settings.reducer

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import data.repository.SettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.Category
import model.ScreenElement
import model.Settings
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mock
import ui.settings.SettingsState

@ExperimentalCoroutinesApi
class ResetSettingsReducerImplTest : BaseReducerTest() {

    @Mock
    private lateinit var settingsRepositoryMock: SettingsRepository

    private val settings = Settings(
        screenElements = mutableListOf(
            ScreenElement(name = "test")
        ),
        categories = mutableListOf()
    )

    private lateinit var reducer: ResetSettingsReducerImpl

    @Test
    fun `when categories empty on invoke`() {
        whenever(settingsRepositoryMock.loadSettings()) doReturn settings
        reducer = ResetSettingsReducerImpl(state, effectMock, TestCoroutineScope(), settingsRepositoryMock)

        reducer.invoke()

        assertEquals(
            SettingsState(
                screenElements = mutableListOf(
                    ScreenElement(name = "test")
                ),
                categories = emptyList(),
                selectedCategoryIndex = null
            ),
            state.value
        )
    }

    @Test
    fun `when categories not empty on invoke`() {
        whenever(settingsRepositoryMock.loadSettings()) doReturn settings.copy(categories = mutableListOf(Category()))
        reducer = ResetSettingsReducerImpl(state, effectMock, TestCoroutineScope(), settingsRepositoryMock)

        reducer.invoke()

        assertEquals(
            SettingsState(
                screenElements = mutableListOf(
                    ScreenElement(name = "test")
                ),
                categories = listOf(Category()),
                selectedCategoryIndex = 0
            ),
            state.value
        )
    }
}