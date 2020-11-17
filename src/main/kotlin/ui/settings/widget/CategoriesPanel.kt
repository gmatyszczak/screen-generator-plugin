package ui.settings.widget

import com.intellij.ui.CollectionListModel
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import model.Category
import ui.settings.SettingsState
import java.awt.GridLayout
import javax.swing.JPanel
import javax.swing.ListSelectionModel

class CategoriesPanel : JPanel() {

    private val listModel = CollectionListModel<Category>()
    val list = JBList(listModel).apply {
        selectionMode = ListSelectionModel.SINGLE_SELECTION
    }
    private val toolbarDecorator: ToolbarDecorator = ToolbarDecorator.createDecorator(list)

    var onAddClicked: (() -> Unit)? = null
    var onRemoveClicked: ((Int) -> Unit)? = null
    var onMoveDownClicked: ((Int) -> Unit)? = null
    var onMoveUpClicked: ((Int) -> Unit)? = null
    var onItemSelected: ((Int) -> Unit)? = null

    private var listenersBlocked = false

    init {
        border = IdeBorderFactory.createTitledBorder("Category", false)
        layout = GridLayout(1, 1)
        toolbarDecorator.apply {
            setAddAction { onAddClicked?.invoke() }
            setRemoveAction { onRemoveClicked?.invoke(list.selectedIndex) }
            setMoveDownAction { onMoveDownClicked?.invoke(list.selectedIndex) }
            setMoveUpAction { onMoveUpClicked?.invoke(list.selectedIndex) }
            add(createPanel())
        }
        list.addListSelectionListener { if (!it.valueIsAdjusting && !listenersBlocked) onItemSelected?.invoke(list.selectedIndex) }
    }

    fun render(state: SettingsState) {
        listenersBlocked = true
        state.categories.forEachIndexed { index, categoryScreenElements ->
            if (index < listModel.size && listModel.getElementAt(index) != categoryScreenElements.category) {
                listModel.setElementAt(categoryScreenElements.category, index)
            } else if (index >= listModel.size) {
                listModel.add(categoryScreenElements.category)
            }
        }
        if (listModel.size > state.categories.size) {
            listModel.removeRange(state.categories.size, listModel.size - 1)
        }
        if (state.selectedCategoryIndex != null && list.selectedIndex != state.selectedCategoryIndex) {
            list.selectedIndex = state.selectedCategoryIndex
        }
        listenersBlocked = false
    }

    fun updateSelectedIndex(index: Int) {
        listenersBlocked = true
        if (list.selectedIndex != index) {
            list.selectedIndex = index
        }
        listenersBlocked = false
    }
}