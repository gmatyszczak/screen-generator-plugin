package data.file

import data.repository.SettingsRepository

interface FileCreator {

    fun createScreenFiles(sourceRoot: SourceRoot, packageName: String, screenName: String)
}

class FileCreatorImpl(private val settingsRepository: SettingsRepository) : FileCreator {

    override fun createScreenFiles(sourceRoot: SourceRoot, packageName: String, screenName: String) {
        val subdirectory = findSubdirectory(sourceRoot, packageName)
        settingsRepository.loadSettings().screenElements.forEach {
            val file = File("$screenName${it.name}", it.body(screenName, packageName))
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