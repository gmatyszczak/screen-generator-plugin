package ui.settings.widget

import com.intellij.ui.CollectionListModel
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import model.ScreenElement
import ui.settings.SettingsState
import java.awt.GridLayout
import javax.swing.JPanel
import javax.swing.ListSelectionModel

class ScreenElementsPanel : JPanel() {

    private val listModel = CollectionListModel<ScreenElement>()
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
        border = IdeBorderFactory.createTitledBorder("Screen Elements", false)
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
        state.selectedCategoryScreenElements?.screenElements?.forEachIndexed { index, screenElement ->
            if (index < listModel.size && listModel.getElementAt(index) != screenElement) {
                listModel.setElementAt(screenElement, index)
            } else if (index >= listModel.size) {
                listModel.add(screenElement)
            }
        }
        if (listModel.size > state.selectedCategoryScreenElements?.screenElements?.size ?: 0) {
            listModel.removeRange(state.selectedCategoryScreenElements?.screenElements?.size ?: 0, listModel.size - 1)
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
