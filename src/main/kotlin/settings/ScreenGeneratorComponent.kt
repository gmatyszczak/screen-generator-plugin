package settings

import com.intellij.openapi.components.AbstractProjectComponent
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil
import model.ScreenElement
import java.io.Serializable

@State(name = "ScreenGeneratorConfiguration",
        storages = [Storage(value = "screenGeneratorConfiguration.xml")])
class ScreenGeneratorComponent(project: Project) : AbstractProjectComponent(project), Serializable, PersistentStateComponent<List<ScreenElement>> {

    companion object {
        fun getInstance(project: Project): ScreenGeneratorComponent = project.getComponent(ScreenGeneratorComponent::class.java)
    }

    var screenElements: List<ScreenElement> = emptyList()

    override fun getState(): List<ScreenElement>? = screenElements

    override fun loadState(state: List<ScreenElement>) {
        XmlSerializerUtil.copyBean(state, screenElements)
    }
}