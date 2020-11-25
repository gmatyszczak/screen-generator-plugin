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
    private lateinit var clickHelpReducerMock: ClickHelpReducer

    @Mock
    private lateinit var changeAndroidComponentReducerMock: ChangeAndroidComponentReducer

    @Mock
    private lateinit var addCategoryReducerMock: AddCategoryReducer

    @Mock
    private lateinit var selectCategoryReducerMock: SelectCategoryReducer

    @Mock
    private lateinit var removeCategoryReducerMock: RemoveCategoryReducer

    @Mock
    private lateinit var moveUpCategoryReducerMock: MoveUpCategoryReducer

    @Mock
    private lateinit var moveDownCategoryReducerMock: MoveDownCategoryReducer

    @Mock
    private lateinit var changeSubdirectoryReducerMock: ChangeSubdirectoryReducer

    @Mock
    private lateinit var changeSourceSetReducerMock: ChangeSourceSetReducer

    @Mock
    private lateinit var changeCategoryNameReducerMock: ChangeCategoryNameReducer

    @Mock
    private lateinit var addCustomVariableReducerMock: AddCustomVariableReducer

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
    fun `when ClickHelp on invoke`() {
        viewModel.reduce(SettingsAction.ClickHelp)

        verify(clickHelpReducerMock).invoke()
    }

    @Test
    fun `when ChangeAndroidComponent on invoke`() {
        viewModel.reduce(SettingsAction.ChangeAndroidComponent(1))

        verify(changeAndroidComponentReducerMock).invoke(1)
    }

    @Test
    fun `when AddCategory on invoke`() {
        viewModel.reduce(SettingsAction.AddCategory)

        verify(addCategoryReducerMock).invoke()
    }


    @Test
    fun `when SelectCategory on invoke`() {
        viewModel.reduce(SettingsAction.SelectCategory(0))
        verify(selectCategoryReducerMock).invoke(0)
    }

    @Test
    fun `when RemoveCategory on invoke`() {
        viewModel.reduce(SettingsAction.RemoveCategory(0))
        verify(removeCategoryReducerMock).invoke(0)
    }

    @Test
    fun `when MoveUpCategory on invoke`() {
        viewModel.reduce(SettingsAction.MoveUpCategory(0))
        verify(moveUpCategoryReducerMock).invoke(0)
    }

    @Test
    fun `when MoveDownCategory on invoke`() {
        viewModel.reduce(SettingsAction.MoveDownCategory(0))
        verify(moveDownCategoryReducerMock).invoke(0)
    }

    @Test
    fun `when ChangeSubdirectory on invoke`() {
        viewModel.reduce(SettingsAction.ChangeSubdirectory("test"))
        verify(changeSubdirectoryReducerMock).invoke("test")
    }

    @Test
    fun `when ChangeSourceSet on invoke`() {
        viewModel.reduce(SettingsAction.ChangeSourceSet("test"))
        verify(changeSourceSetReducerMock).invoke("test")
    }

    @Test
    fun `when ChangeCategoryName on invoke`() {
        viewModel.reduce(SettingsAction.ChangeCategoryName("test"))
        verify(changeCategoryNameReducerMock).invoke("test")
    }

    @Test
    fun `when AddCustomVariable on invoke`() {
        viewModel.reduce(SettingsAction.AddCustomVariable)
        verify(addCustomVariableReducerMock).invoke()
    }
}