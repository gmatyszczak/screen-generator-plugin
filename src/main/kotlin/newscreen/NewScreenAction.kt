package newscreen

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class NewScreenAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        NewScreenDialog(event.project!!).show()
    }
}