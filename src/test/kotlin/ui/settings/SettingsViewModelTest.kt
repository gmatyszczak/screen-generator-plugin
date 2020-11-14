package ui.settings

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import ui.settings.reducer.*

@RunWith(MockitoJUnitRunner::class)
class SettingsViewModelTest {

    @Mock
    private lateinit var applySettingsReducerMock: ApplySettingsReducer

    @Mock
    private lateinit var resetSettingsReducerMock: ResetSettingsReducer

    @Mock
    private lateinit var addScreenElementReducerMock: AddScreenElementReducer

    @Mock
    private lateinit var removeScreenElementReducerMock: RemoveScreenElementReducer

    @Mock
    private lateinit var moveDownScreenElementReducerMock: MoveDownScreenElementReducer

    @Mock
    private lateinit var moveUpScreenElementReducerMock: MoveUpScreenElementReducer

    @Mock
    private lateinit var selectScreenElementReducerMock: SelectScreenElementReducer

    @Mock
    private lateinit var changeNameReducerMock: ChangeNameReducer

    @Mock
    private lateinit var changeFileNameReducerMock: ChangeFileNameReducer

    @Mock
    private lateinit var changeTemplateReducerMock: ChangeTemplateReducer

    @Mock
    private lateinit var changeFileTypeReducerMock: ChangeFileTypeReducer

    @Mock
    private lateinit var changeActivityReducerMock: ChangeActivityReducer

    @Mock
    private lateinit var changeFragmentReducerMock: ChangeFragmentReducer

    @Mock
    private lateinit var clickHelpReducerMock: ClickHelpReducer

    @InjectMocks
    lateinit var viewModel: SettingsViewModel

    @Test
    fun `on init`() {
        verify(resetSettingsReducerMock).invoke()
    }

    @Test
    fun `when ApplySettings on invoke`() {
        viewModel.reduce(SettingsAction.ApplySettings)

        verify(applySettingsReducerMock).invoke()
    }

    @Test
    fun `when ResetSettings on invoke`() {
        viewModel.reduce(SettingsAction.ResetSettings)

        verify(resetSettingsReducerMock, times(2)).invoke()
    }

    @Test
    fun `when AddScreenElement on invoke`() {
        viewModel.reduce(SettingsAction.AddScreenElement)

        verify(addScreenElementReducerMock).invoke()
    }

    @Test
    fun `when RemoveScreenElement on invoke`() {
        viewModel.reduce(SettingsAction.RemoveScreenElement(10))

        verify(removeScreenElementReducerMock).invoke(10)
    }

    @Test
    fun `when MoveDownScreenElement on invoke`() {
        viewModel.reduce(SettingsAction.MoveDownScreenElement(10))

        verify(moveDownScreenElementReducerMock).invoke(10)
    }

    @Test
    fun `when MoveUpScreenElement on invoke`() {
        viewModel.reduce(SettingsAction.MoveUpScreenElement(10))

        verify(moveUpScreenElementReducerMock).invoke(10)
    }

    @Test
    fun `when SelectScreenElement on invoke`() {
        viewModel.reduce(SettingsAction.SelectScreenElement(10))

        verify(selectScreenElementReducerMock).invoke(10)
    }

    @Test
    fun `when ChangeName on invoke`() {
        viewModel.reduce(SettingsAction.ChangeName("test"))

        verify(changeNameReducerMock).invoke("test")
    }

    @Test
    fun `when ChangeFileName on invoke`() {
        viewModel.reduce(SettingsAction.ChangeFileName("test"))

        verify(changeFileNameReducerMock).invoke("test")
    }

    @Test
    fun `when ChangeTemplate on invoke`() {
        viewModel.reduce(SettingsAction.ChangeTemplate("test"))
        verify(changeTemplateReducerMock).invoke("test")
    }

    @Test
    fun `when ChangeFileType on invoke`() {
        viewModel.reduce(SettingsAction.ChangeFileType(0))

        verify(changeFileTypeReducerMock).invoke(0)
    }

    @Test
    fun `when ChangeActivity on invoke`() {
        viewModel.reduce(SettingsAction.ChangeActivity("test"))

        verify(changeActivityReducerMock).invoke("test")
    }

    @Test
    fun `when ChangeFragment on invoke`() {
        viewModel.reduce(SettingsAction.ChangeFragment("test"))

        verify(changeFragmentReducerMock).invoke("test")
    }

    @Test
    fun `when ClickHelp on invoke`() {
        viewModel.reduce(SettingsAction.ClickHelp)

        verify(clickHelpReducerMock).invoke()
    }
}