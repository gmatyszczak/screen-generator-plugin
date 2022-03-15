package ui.newscreen.reducer

import app.cash.turbine.test
import data.file.ScreenElementCreator
import data.file.WriteActionDispatcher
import io.mockk.every
import io.mockk.invoke
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runBlockingTest
import model.AndroidComponent
import model.Category
import model.Module
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.newscreen.NewScreenAction
import ui.newscreen.NewScreenEffect

class OkClickedReducerTest {

    val moduleName = "domain"
    val moduleDomain = Module("MyApplication.$moduleName", moduleName)
    val category = Category()
    val screenElementCreator: ScreenElementCreator = mockk(relaxUnitFun = true)
    val effect = MutableSharedFlow<NewScreenEffect>()
    val writeActionDispatcher: WriteActionDispatcher = mockk()

    lateinit var reducer: OkClickedReducer

    @BeforeEach
    fun setUp() {
        reducer = OkClickedReducer(
            effect,
            screenElementCreator,
            writeActionDispatcher
        )
    }

    @Test
    fun `on invoke`() = runBlockingTest {
        every { writeActionDispatcher.dispatch(captureLambda()) } answers {
            lambda<() -> Unit>().invoke()
        }
        val screenName = "Test"
        val packageName = "com.test"
        val action = NewScreenAction.OkClicked(
            packageName,
            screenName,
            AndroidComponent.ACTIVITY.ordinal,
            moduleDomain,
            category,
            emptyMap()
        )

        effect.test {
            reducer(action)

            verify {
                screenElementCreator.create(
                    packageName,
                    screenName,
                    AndroidComponent.ACTIVITY,
                    moduleDomain,
                    category,
                    emptyMap()
                )
            }
            awaitItem() shouldBeEqualTo NewScreenEffect.Close
            cancelAndIgnoreRemainingEvents()
        }
    }
}
