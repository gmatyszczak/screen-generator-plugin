package ui.settings.reducer

import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import model.ScreenElement
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import ui.settings.SettingsState
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class RemoveScreenElementReducerImplTest : BaseReducerTest() {

    @Mock
    private lateinit var selectScreenElementReducerMock: SelectScreenElementReducer

    private lateinit var reducer: RemoveScreenElementReducerImpl

    private val initialState = SettingsState(
        screenElements = listOf(
            ScreenElement(name = "test1"),
            ScreenElement(name = "test2")
        )
    )

    @Before
    fun setup() {
        state.value = initialState
        reducer = RemoveScreenElementReducerImpl(state, effectMock, TestCoroutineScope(), selectScreenElementReducerMock)
    }

    @Test
    fun `on invoke`() {
        reducer.invoke(0)

        assertEquals(
            SettingsState(
                isModified = true,
                screenElements = listOf(
                    ScreenElement(name = "test2")
                )
            ),
            state.value
        )
        verify(selectScreenElementReducerMock).invoke(0)
    }
}