package ui.settings.reducer

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import ui.settings.SettingsEffect
import ui.settings.SettingsState

@RunWith(MockitoJUnitRunner::class)
open class BaseReducerTest {

    @Mock
    protected lateinit var effectMock: MutableSharedFlow<SettingsEffect>

    protected val state = MutableStateFlow(SettingsState())

}
