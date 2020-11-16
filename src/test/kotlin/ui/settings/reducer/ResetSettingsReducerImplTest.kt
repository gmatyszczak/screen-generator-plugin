package ui.settings.reducer

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import data.repository.SettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.ScreenElement
import model.Settings
import org.junit.Assert.assertEquals
import org.junit.Before
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
        )
    )

    private lateinit var reducer: ResetSettingsReducerImpl

    @Before
    fun setUp() {
        whenever(settingsRepositoryMock.loadSettings()) doReturn settings
        reducer = ResetSettingsReducerImpl(state, effectMock, TestCoroutineScope(), settingsRepositoryMock)
    }

    @Test
    fun `on invoke`() {
        reducer.invoke()

        assertEquals(
            SettingsState(
                screenElements = mutableListOf(
                    ScreenElement(name = "test")
                )
            ),
            state.value
        )
    }
}