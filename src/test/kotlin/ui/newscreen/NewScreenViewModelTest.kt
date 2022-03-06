package ui.newscreen

import app.cash.turbine.test
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.newscreen.NewScreenAction.Init

@ExperimentalCoroutinesApi
class NewScreenViewModelTest {

    val actionFlow = MutableSharedFlow<NewScreenAction>()
    lateinit var viewModel: NewScreenViewModel

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `on init`() = runBlockingTest {
        actionFlow.test {
            viewModel = NewScreenViewModel(mockk(), mockk(), actionFlow, emptyMap())

            awaitItem() shouldBeEqualTo Init
            cancelAndIgnoreRemainingEvents()
        }
    }
}
