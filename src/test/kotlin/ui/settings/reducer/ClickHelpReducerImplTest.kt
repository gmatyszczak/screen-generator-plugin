package ui.settings.reducer

import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import ui.settings.SettingsEffect

@ExperimentalCoroutinesApi
class ClickHelpReducerImplTest : BaseReducerTest() {

    private lateinit var reducer: ClickHelpReducerImpl

    @Before
    fun setUp() {
        reducer = ClickHelpReducerImpl(state, effectMock, TestCoroutineScope())
    }

    @Test
    fun `on invoke`() = runBlockingTest {
        reducer.invoke()

        verify(effectMock).emit(SettingsEffect.ShowHelp)
    }
}