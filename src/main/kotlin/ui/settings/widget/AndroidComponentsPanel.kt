package ui.settings.widget

import com.intellij.ui.IdeBorderFactory
import ui.settings.SettingsState
import util.addTextChangeListener
import util.updateText
import java.awt.GridBagLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class AndroidComponentsPanel : JPanel() {

    var onActivityTextChanged: ((String) -> Unit)? = null
    var onFragmentTextChanged: ((String) -> Unit)? = null

    private val activityTextField = JTextField()
    private val fragmentTextField = JTextField()

    private var listenersBlocked = false

    init {
        border = IdeBorderFactory.createTitledBorder("Android Components", false)
        layout = GridBagLayout()
        add(JLabel("Activity Base Class:"), constraintsLeft(0, 0))
        add(activityTextField, constraintsRight(1, 0))
        add(JLabel("Fragment Base Class:"), constraintsLeft(0, 1))
        add(fragmentTextField, constraintsRight(1, 1))

        activityTextField.addTextChangeListener { if (!listenersBlocked) onActivityTextChanged?.invoke(it) }
        fragmentTextField.addTextChangeListener { if (!listenersBlocked) onFragmentTextChanged?.invoke(it) }
    }

    fun render(state: SettingsState) {
        listenersBlocked = true
        activityTextField.updateText(state.activityBaseClass)
        fragmentTextField.updateText(state.fragmentBaseClass)
        listenersBlocked = false
    }

}