package newscreen

import newscreen.files.Directory
import newscreen.files.File
import newscreen.files.SourceRoot
import settings.SettingsRepository

interface FileCreator {

    fun createScreenFiles(sourceRoot: SourceRoot, packageName: String, screenName: String)
}

class FileCreatorImpl(private val settingsRepository: SettingsRepository) : FileCreator {

    override fun createScreenFiles(sourceRoot: SourceRoot, packageName: String, screenName: String) {
        val subdirectory = findSubdirectory(sourceRoot, packageName)
        settingsRepository.loadScreenElements().forEach {
            val file = File("$screenName${it.name}", "Test")
            subdirectory.addFile(file)
        }
    }

    private fun findSubdirectory(sourceRoot: SourceRoot, packageName: String): Directory {
        var directory = sourceRoot.directory
        packageName.split(".").forEach {
            directory = directory.findSubdirectory(it) ?: directory.createSubdirectory(it)
        }
        return directory
    }
}