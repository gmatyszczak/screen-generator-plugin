package ui.settings

import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import ui.settings.reducer.AddCategoryReducer
import ui.settings.reducer.AddCustomVariableReducer
import ui.settings.reducer.AddScreenElementReducer
import ui.settings.reducer.ApplySettingsReducer
import ui.settings.reducer.ChangeAndroidComponentReducer
import ui.settings.reducer.ChangeCategoryNameReducer
import ui.settings.reducer.ChangeCustomVariableNameReducer
import ui.settings.reducer.ChangeFileNameReducer
import ui.settings.reducer.ChangeFileTypeReducer
import ui.settings.reducer.ChangeNameReducer
import ui.settings.reducer.ChangeSourceSetReducer
import ui.settings.reducer.ChangeSubdirectoryReducer
import ui.settings.reducer.ChangeTemplateReducer
import ui.settings.reducer.ClickHelpReducer
import ui.settings.reducer.MoveDownCategoryReducer
import ui.settings.reducer.MoveDownCustomVariableReducer
import ui.settings.reducer.MoveDownScreenElementReducer
import ui.settings.reducer.MoveUpCategoryReducer
import ui.settings.reducer.MoveUpCustomVariableReducer
import ui.settings.reducer.MoveUpScreenElementReducer
import ui.settings.reducer.RemoveCategoryReducer
import ui.settings.reducer.RemoveCustomVariableReducer
import ui.settings.reducer.RemoveScreenElementReducer
import ui.settings.reducer.ResetSettingsReducer
import ui.settings.reducer.SelectCategoryReducer
import ui.settings.reducer.SelectCustomVariableReducer
import ui.settings.reducer.SelectScreenElementReducer

class SettingsViewModelTest {

    val applySettingsReducerMock: ApplySettingsReducer = mockk(relaxUnitFun = true)
    val resetSettingsReducerMock: ResetSettingsReducer = mockk(relaxUnitFun = true)
    val addScreenElementReducerMock: AddScreenElementReducer = mockk(relaxUnitFun = true)
    val removeScreenElementReducerMock: RemoveScreenElementReducer = mockk(relaxUnitFun = true)
    val moveDownScreenElementReducerMock: MoveDownScreenElementReducer = mockk(relaxUnitFun = true)
    val moveUpScreenElementReducerMock: MoveUpScreenElementReducer = mockk(relaxUnitFun = true)
    val selectScreenElementReducerMock: SelectScreenElementReducer = mockk(relaxUnitFun = true)
    val changeNameReducerMock: ChangeNameReducer = mockk(relaxUnitFun = true)
    val changeFileNameReducerMock: ChangeFileNameReducer = mockk(relaxUnitFun = true)
    val changeTemplateReducerMock: ChangeTemplateReducer = mockk(relaxUnitFun = true)
    val changeFileTypeReducerMock: ChangeFileTypeReducer = mockk(relaxUnitFun = true)
    val clickHelpReducerMock: ClickHelpReducer = mockk(relaxUnitFun = true)
    val changeAndroidComponentReducerMock: ChangeAndroidComponentReducer = mockk(relaxUnitFun = true)
    val addCategoryReducerMock: AddCategoryReducer = mockk(relaxUnitFun = true)
    val selectCategoryReducerMock: SelectCategoryReducer = mockk(relaxUnitFun = true)
    val removeCategoryReducerMock: RemoveCategoryReducer = mockk(relaxUnitFun = true)
    val moveUpCategoryReducerMock: MoveUpCategoryReducer = mockk(relaxUnitFun = true)
    val moveDownCategoryReducerMock: MoveDownCategoryReducer = mockk(relaxUnitFun = true)
    val changeSubdirectoryReducerMock: ChangeSubdirectoryReducer = mockk(relaxUnitFun = true)
    val changeSourceSetReducerMock: ChangeSourceSetReducer = mockk(relaxUnitFun = true)
    val changeCategoryNameReducerMock: ChangeCategoryNameReducer = mockk(relaxUnitFun = true)
    val addCustomVariableReducerMock: AddCustomVariableReducer = mockk(relaxUnitFun = true)
    val selectCustomVariableReducerMock: SelectCustomVariableReducer = mockk(relaxUnitFun = true)
    val removeCustomVariableReducerMock: RemoveCustomVariableReducer = mockk(relaxUnitFun = true)
    val moveDownCustomVariableReducerMock: MoveDownCustomVariableReducer = mockk(relaxUnitFun = true)
    val moveUpCustomVariableReducerMock: MoveUpCustomVariableReducer = mockk(relaxUnitFun = true)
    val changeCustomVariableNameReducerMock: ChangeCustomVariableNameReducer = mockk(relaxUnitFun = true)

    val viewModel = SettingsViewModel(
        applySettingsReducerMock,
        resetSettingsReducerMock,
        addScreenElementReducerMock,
        removeScreenElementReducerMock,
        moveDownScreenElementReducerMock,
        moveUpScreenElementReducerMock,
        selectScreenElementReducerMock,
        changeNameReducerMock,
        changeFileNameReducerMock,
        changeTemplateReducerMock,
        changeFileTypeReducerMock,
        clickHelpReducerMock,
        changeAndroidComponentReducerMock,
        addCategoryReducerMock,
        selectCategoryReducerMock,
        removeCategoryReducerMock,
        moveUpCategoryReducerMock,
        moveDownCategoryReducerMock,
        changeSubdirectoryReducerMock,
        changeSourceSetReducerMock,
        changeCategoryNameReducerMock,
        addCustomVariableReducerMock,
        selectCustomVariableReducerMock,
        removeCustomVariableReducerMock,
        moveDownCustomVariableReducerMock,
        moveUpCustomVariableReducerMock,
        changeCustomVariableNameReducerMock,
    )

    @Test
    fun `on init`() {
        verify { resetSettingsReducerMock.invoke() }
    }

    @Test
    fun `when ApplySettings on invoke`() {
        viewModel.reduce(SettingsAction.ApplySettings)

        verify { applySettingsReducerMock.invoke() }
    }

    @Test
    fun `when ResetSettings on invoke`() {
        viewModel.reduce(SettingsAction.ResetSettings)

        verify(exactly = 2) { resetSettingsReducerMock.invoke() }
    }

    @Test
    fun `when AddScreenElement on invoke`() {
        viewModel.reduce(SettingsAction.AddScreenElement)

        verify { addScreenElementReducerMock.invoke() }
    }

    @Test
    fun `when RemoveScreenElement on invoke`() {
        viewModel.reduce(SettingsAction.RemoveScreenElement(10))

        verify { removeScreenElementReducerMock.invoke(10) }
    }

    @Test
    fun `when MoveDownScreenElement on invoke`() {
        viewModel.reduce(SettingsAction.MoveDownScreenElement(10))

        verify { moveDownScreenElementReducerMock.invoke(10) }
    }

    @Test
    fun `when MoveUpScreenElement on invoke`() {
        viewModel.reduce(SettingsAction.MoveUpScreenElement(10))

        verify { moveUpScreenElementReducerMock.invoke(10) }
    }

    @Test
    fun `when SelectScreenElement on invoke`() {
        viewModel.reduce(SettingsAction.SelectScreenElement(10))

        verify { selectScreenElementReducerMock.invoke(10) }
    }

    @Test
    fun `when ChangeName on invoke`() {
        viewModel.reduce(SettingsAction.ChangeName("test"))

        verify { changeNameReducerMock.invoke("test") }
    }

    @Test
    fun `when ChangeFileName on invoke`() {
        viewModel.reduce(SettingsAction.ChangeFileName("test"))

        verify { changeFileNameReducerMock.invoke("test") }
    }

    @Test
    fun `when ChangeTemplate on invoke`() {
        viewModel.reduce(SettingsAction.ChangeTemplate("test"))
        verify { changeTemplateReducerMock.invoke("test") }
    }

    @Test
    fun `when ChangeFileType on invoke`() {
        viewModel.reduce(SettingsAction.ChangeFileType(0))

        verify { changeFileTypeReducerMock.invoke(0) }
    }

    @Test
    fun `when ClickHelp on invoke`() {
        viewModel.reduce(SettingsAction.ClickHelp)

        verify { clickHelpReducerMock.invoke() }
    }

    @Test
    fun `when ChangeAndroidComponent on invoke`() {
        viewModel.reduce(SettingsAction.ChangeAndroidComponent(1))

        verify { changeAndroidComponentReducerMock.invoke(1) }
    }

    @Test
    fun `when AddCategory on invoke`() {
        viewModel.reduce(SettingsAction.AddCategory)

        verify { addCategoryReducerMock.invoke() }
    }

    @Test
    fun `when SelectCategory on invoke`() {
        viewModel.reduce(SettingsAction.SelectCategory(0))
        verify { selectCategoryReducerMock.invoke(0) }
    }

    @Test
    fun `when RemoveCategory on invoke`() {
        viewModel.reduce(SettingsAction.RemoveCategory(0))
        verify { removeCategoryReducerMock.invoke(0) }
    }

    @Test
    fun `when MoveUpCategory on invoke`() {
        viewModel.reduce(SettingsAction.MoveUpCategory(0))
        verify { moveUpCategoryReducerMock.invoke(0) }
    }

    @Test
    fun `when MoveDownCategory on invoke`() {
        viewModel.reduce(SettingsAction.MoveDownCategory(0))
        verify { moveDownCategoryReducerMock.invoke(0) }
    }

    @Test
    fun `when ChangeSubdirectory on invoke`() {
        viewModel.reduce(SettingsAction.ChangeSubdirectory("test"))
        verify { changeSubdirectoryReducerMock.invoke("test") }
    }

    @Test
    fun `when ChangeSourceSet on invoke`() {
        viewModel.reduce(SettingsAction.ChangeSourceSet("test"))
        verify { changeSourceSetReducerMock.invoke("test") }
    }

    @Test
    fun `when ChangeCategoryName on invoke`() {
        viewModel.reduce(SettingsAction.ChangeCategoryName("test"))
        verify { changeCategoryNameReducerMock.invoke("test") }
    }

    @Test
    fun `when AddCustomVariable on invoke`() {
        viewModel.reduce(SettingsAction.AddCustomVariable)
        verify { addCustomVariableReducerMock.invoke() }
    }

    @Test
    fun `when SelectCustomVariable on invoke`() {
        viewModel.reduce(SettingsAction.SelectCustomVariable(10))
        verify { selectCustomVariableReducerMock.invoke(10) }
    }

    @Test
    fun `when RemoveCustomVariable on invoke`() {
        viewModel.reduce(SettingsAction.RemoveCustomVariable(10))
        verify { removeCustomVariableReducerMock.invoke(10) }
    }

    @Test
    fun `when MoveDownCustomVariable on invoke`() {
        viewModel.reduce(SettingsAction.MoveDownCustomVariable(10))
        verify { moveDownCustomVariableReducerMock.invoke(10) }
    }

    @Test
    fun `when MoveUpCustomVariable on invoke`() {
        viewModel.reduce(SettingsAction.MoveUpCustomVariable(10))
        verify { moveUpCustomVariableReducerMock.invoke(10) }
    }

    @Test
    fun `when ChangeCustomVariableName on invoke`() {
        viewModel.reduce(SettingsAction.ChangeCustomVariableName("test"))
        verify { changeCustomVariableNameReducerMock.invoke("test") }
    }
}
