package ui.settings.widget

import com.intellij.ui.CollectionListModel
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import model.CustomVariable
import ui.settings.SettingsState
import java.awt.GridLayout
import javax.swing.JPanel
import javax.swing.ListSelectionModel

class CustomVariablesPanel : JPanel() {

    private val listModel = CollectionListModel<CustomVariable>()
    val list = JBList(listModel).apply {
        selectionMode = ListSelectionModel.SINGLE_SELECTION
    }
    private val toolbarDecorator: ToolbarDecorator = ToolbarDecorator.createDecorator(list)

    var onAddClicked: (() -> Unit)? = null
    var onRemoveClicked: ((Int) -> Unit)? = null
    var onMoveDownClicked: ((Int) -> Unit)? = null
    var onMoveUpClicked: ((Int) -> Unit)? = null

    private var listenersBlocked = false

    init {
        border = IdeBorderFactory.createTitledBorder("Custom Variables", false)
        layout = GridLayout(1, 1)
        toolbarDecorator.apply {
            setAddAction { onAddClicked?.invoke() }
            setRemoveAction { onRemoveClicked?.invoke(list.selectedIndex) }
            setMoveDownAction { onMoveDownClicked?.invoke(list.selectedIndex) }
            setMoveUpAction { onMoveUpClicked?.invoke(list.selectedIndex) }
            add(createPanel())
        }
    }

    fun render(state: SettingsState) {
        listenersBlocked = true
        val selectedCategoryScreenElements = state.selectedCategoryScreenElements
        if (selectedCategoryScreenElements != null) {
            selectedCategoryScreenElements.category.customVariables.forEachIndexed { index, customVariable ->
                if (index < listModel.size && listModel.getElementAt(index) != customVariable) {
                    listModel.setElementAt(customVariable, index)
                } else if (index >= listModel.size) {
                    listModel.add(customVariable)
                }
            }
            if (listModel.size > selectedCategoryScreenElements.category.customVariables.size) {
                listModel.removeRange(selectedCategoryScreenElements.category.customVariables.size, listModel.size - 1)
            }
        } else {
            listModel.removeAll()
        }
        listenersBlocked = false
    }
}