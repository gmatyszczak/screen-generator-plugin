package data.file

import data.repository.SettingsRepository
import model.AndroidComponent
import model.Category
import model.CustomVariable
import model.Module
import model.ScreenElementType.FILE_MODIFICATION
import model.ScreenElementType.NEW_FILE
import javax.inject.Inject

class ScreenElementCreator @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val targetDirectory: TargetDirectory,
) {

    fun create(
        packageName: String,
        screenName: String,
        androidComponent: AndroidComponent,
        module: Module,
        category: Category,
        customVariablesMap: Map<CustomVariable, String>
    ) {
        settingsRepository.loadScreenElements(category.id)
            .filter { it.relatedAndroidComponent == AndroidComponent.NONE || it.relatedAndroidComponent == androidComponent }
            .map { targetDirectory.findOrCreate(it, module, packageName) to it }
            .forEach { (targetDirectory, screenElement) ->
                if (targetDirectory != null) {
                    when (screenElement.type) {
                        NEW_FILE -> targetDirectory.addFile(
                            screenElement.toFile(
                                screenName,
                                packageName,
                                androidComponent,
                                customVariablesMap,
                            )
                        )
                        FILE_MODIFICATION -> targetDirectory.modifyFile(
                            screenElement.toModification(
                                screenName,
                                packageName,
                                androidComponent,
                                customVariablesMap,
                            )
                        )
                    }
                }
            }
    }
}
