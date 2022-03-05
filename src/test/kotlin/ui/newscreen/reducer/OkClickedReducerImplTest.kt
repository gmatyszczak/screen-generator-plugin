package ui.newscreen.reducer

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import data.file.FileCreator
import data.file.WriteActionDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import model.AndroidComponent
import model.Category
import model.Module
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import ui.newscreen.NewScreenEffect

class OkClickedReducerImplTest : BaseReducerTest() {

    @Mock
    private lateinit var fileCreatorMock: FileCreator

    @Mock
    private lateinit var writeActionDispatcherMock: WriteActionDispatcher

    private lateinit var reducer: OkClickedReducerImpl

    private val moduleName = "domain"
    private val moduleDomain = Module("MyApplication.$moduleName", moduleName)
    private val category = Category()

    @Before
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
        whenever(writeActionDispatcherMock.dispatch(any())).thenAnswer { (it.arguments[0] as () -> Unit).invoke() }
        val screenName = "Test"
        val packageName = "com.test"

        reducer(packageName, screenName, AndroidComponent.ACTIVITY.ordinal, moduleDomain, category, emptyMap())

        verify(fileCreatorMock).createScreenFiles(
            packageName,
            screenName,
            AndroidComponent.ACTIVITY,
            moduleDomain,
            category,
            emptyMap()
        )
        verify(effectMock).emit(NewScreenEffect.Close)
    }
}
