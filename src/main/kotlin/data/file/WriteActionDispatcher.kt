package data.file

import com.intellij.openapi.application.ApplicationManager

interface WriteActionDispatcher {
    fun dispatch(action: () -> Unit)
}

class WriteActionDispatcherImpl : WriteActionDispatcher {

    override fun dispatch(action: () -> Unit) = ApplicationManager.getApplication().runWriteAction {
        action()
    }
}
