package ui.newscreen.reducer

import data.file.FileCreator
import data.file.WriteActionDispatcher
import io.mockk.coVerify
import io.mockk.every
import io.mockk.invoke
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import model.AndroidComponent
import model.Category
import model.Module
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ui.newscreen.NewScreenEffect

class OkClickedReducerImplTest : BaseReducerTest() {

    val moduleName = "domain"
    val moduleDomain = Module("MyApplication.$moduleName", moduleName)
    val category = Category()
    val fileCreatorMock: FileCreator = mockk(relaxUnitFun = true)
    val writeActionDispatcherMock: WriteActionDispatcher = mockk()

    lateinit var reducer: OkClickedReducerImpl

    @BeforeEach
    fun setUp() {
        reducer = OkClickedReducerImpl(
            state,
            effectMock,
            TestCoroutineScope(),
            fileCreatorMock,
            writeActionDispatcherMock
        )
    }

    @Test
    fun `on invoke`() = runBlockingTest {
        every { writeActionDispatcherMock.dispatch(captureLambda()) } answers {
            lambda<() -> Unit>().invoke()
        }
        val screenName = "Test"
        val packageName = "com.test"

        reducer(packageName, screenName, AndroidComponent.ACTIVITY.ordinal, moduleDomain, category, emptyMap())

        verify {
            fileCreatorMock.createScreenFiles(
                packageName,
                screenName,
                AndroidComponent.ACTIVITY,
                moduleDomain,
                category,
                emptyMap()
            )
        }
        coVerify { effectMock.emit(NewScreenEffect.Close) }
    }
}
