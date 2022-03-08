package ui.settings

import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.core.UI
import ui.settings.SettingsAction.ResetSettings

class SettingsViewModelTest {

    val actionFlow = MutableSharedFlow<SettingsAction>()
    lateinit var viewModel: SettingsViewModel

    @BeforeEach
    fun setup() {
        mockkStatic(Dispatchers::UI)
        every { Dispatchers.UI } returns TestCoroutineDispatcher()
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `on init`() = runBlockingTest {
        actionFlow.test {
            viewModel = SettingsViewModel(
                mockk(),
                mockk(),
                actionFlow,
                emptyMap(),
            )

            awaitItem() shouldBeEqualTo ResetSettings
            cancelAndIgnoreRemainingEvents()
        }
    }
}
