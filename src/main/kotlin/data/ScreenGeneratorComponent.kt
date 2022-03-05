package data

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil
import model.Settings
import java.io.Serializable

@State(
    name = "ScreenGeneratorConfiguration",
    storages = [Storage(value = "screenGeneratorConfiguration.xml")]
)
class ScreenGeneratorComponent : Serializable, PersistentStateComponent<ScreenGeneratorComponent> {

    companion object {
        fun getInstance(project: Project) = ServiceManager.getService(project, ScreenGeneratorComponent::class.java)
    }

    var settings: Settings = Settings()

    override fun getState(): ScreenGeneratorComponent = this

    override fun loadState(state: ScreenGeneratorComponent) {
        XmlSerializerUtil.copyBean(state, this)
    }
}
