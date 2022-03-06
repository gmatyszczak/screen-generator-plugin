package ui.settings.reducer

import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsEffect

@ExperimentalCoroutinesApi
class ClickHelpReducerImplTest : BaseReducerTest() {

    lateinit var reducer: ClickHelpReducerImpl

    @BeforeEach
    fun setUp() {
        reducer = ClickHelpReducerImpl(state, effectMock, TestCoroutineScope())
    }

    @Test
    fun `on invoke`() = runBlockingTest {
        reducer.invoke()

        coVerify { effectMock.emit(SettingsEffect.ShowHelp) }
    }
}
