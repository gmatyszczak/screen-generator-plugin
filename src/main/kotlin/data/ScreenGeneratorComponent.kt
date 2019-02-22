package data

import com.intellij.openapi.components.AbstractProjectComponent
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil
import model.Settings
import java.io.Serializable

@State(name = "ScreenGeneratorConfiguration",
        storages = [Storage(value = "screenGeneratorConfiguration.xml")])
class ScreenGeneratorComponent(project: Project) : AbstractProjectComponent(project), Serializable, PersistentStateComponent<Settings> {

    companion object {
        fun getInstance(project: Project): ScreenGeneratorComponent = project.getComponent(ScreenGeneratorComponent::class.java)
    }

    var settings: Settings = Settings(emptyList(), "", "")

    override fun getState(): Settings? = settings

    override fun loadState(state: Settings) {
        XmlSerializerUtil.copyBean(state, settings)
    }
}