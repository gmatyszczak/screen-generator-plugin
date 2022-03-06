package data.file

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import javax.inject.Inject

private const val COMMAND_NAME = "Screen Generator"
private const val GROUP_ID = "SCREEN_GENERATOR_ID"

class WriteActionDispatcher @Inject constructor(
    val project: Project
) {

    fun dispatch(action: () -> Unit) =
        WriteCommandAction.runWriteCommandAction(project, COMMAND_NAME, GROUP_ID, {
            action()
        })
}
