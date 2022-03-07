package ui.settings.reducer

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.settings.SettingsAction.ClickHelp
import ui.settings.SettingsEffect

@ExperimentalCoroutinesApi
class ClickHelpReducerTest {

    val effect = MutableSharedFlow<SettingsEffect>()
    lateinit var reducer: ClickHelpReducer

    @BeforeEach
    fun setUp() {
        reducer = ClickHelpReducer(effect)
    }

    @Test
    fun `on invoke`() = runBlockingTest {
        effect.test {
            reducer.invoke(ClickHelp)

            awaitItem() shouldBeEqualTo SettingsEffect.ShowHelp
            cancelAndIgnoreRemainingEvents()
        }
    }
}
