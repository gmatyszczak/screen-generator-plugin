package ui.newscreen

import app.cash.turbine.test
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import ui.newscreen.NewScreenAction.Init

@ExperimentalCoroutinesApi
class NewScreenViewModelTest {

    val actionFlow = MutableSharedFlow<NewScreenAction>()
    lateinit var viewModel: NewScreenViewModel

    @Test
    fun `on init`() = runBlockingTest {
        actionFlow.test {
            viewModel = NewScreenViewModel(mockk(), mockk(), actionFlow, emptyMap())

            awaitItem() shouldBeEqualTo Init
            cancelAndIgnoreRemainingEvents()
        }
    }
}
