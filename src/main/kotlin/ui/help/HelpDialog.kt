package ui.help

import com.intellij.openapi.ui.DialogWrapper

class HelpDialog : DialogWrapper(false) {

    private val panel = HelpPanel()

    init {
        init()
    }

    override fun createCenterPanel() = panel
}