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
class AddScreenElementReducerImplTest : BaseReducerTest() {

    @Mock
    private lateinit var selectScreenElementReducerMock: SelectScreenElementReducer

    private lateinit var reducer: AddScreenElementReducerImpl

    private val initialState = SettingsState(
        screenElements = listOf(
            ScreenElement(name = "test")
        )
    )

    @Before
    fun setup() {
        state.value = initialState
        reducer = AddScreenElementReducerImpl(state, effectMock, TestCoroutineScope(), selectScreenElementReducerMock)
    }

    @Test
    fun `on invoke`() {
        reducer.invoke()

        assertEquals(
            SettingsState(
                isModified = true,
                screenElements = listOf(ScreenElement(name = "test"), ScreenElement.getDefault())
            ),
            state.value
        )
        verify(selectScreenElementReducerMock).invoke(1)
    }
}