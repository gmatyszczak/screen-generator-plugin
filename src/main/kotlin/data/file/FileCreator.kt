package data.file

import data.repository.SettingsRepository
import data.repository.SourceRootRepository
import model.FileType
import util.toCamelCase

private const val LAYOUT_DIRECTORY = "layout"

interface FileCreator {

    fun createScreenFiles(packageName: String, screenName: String)
}

class FileCreatorImpl(private val settingsRepository: SettingsRepository,
                      private val sourceRootRepository: SourceRootRepository) : FileCreator {

    override fun createScreenFiles(packageName: String, screenName: String) {
        val codeSourceRoot = sourceRootRepository.findCodeSourceRoot()
        val codeSubdirectory = findCodeSubdirectory(codeSourceRoot, packageName)
        val resourcesSourceRoot = sourceRootRepository.findResourcesSourceRoot()
        val resourcesSubdirectory = findResourcesSubdirectory(resourcesSourceRoot)
        settingsRepository.loadSettings().screenElements.forEach {
            if (it.fileType == FileType.LAYOUT_XML) {
                val file = File(screenName.toCamelCase(), it.body(screenName, packageName), it.fileType)
                resourcesSubdirectory.addFile(file)
            } else {
                val file = File("$screenName${it.name}", it.body(screenName, packageName), it.fileType)
                codeSubdirectory.addFile(file)
            }
        }
    }

    private fun findCodeSubdirectory(sourceRoot: SourceRoot, packageName: String): Directory {
        var directory = sourceRoot.directory
        packageName.split(".").forEach {
            directory = directory.findSubdirectory(it) ?: directory.createSubdirectory(it)
        }
        return directory
    }

    private fun findResourcesSubdirectory(sourceRoot: SourceRoot) =
            sourceRoot.directory.run { findSubdirectory(LAYOUT_DIRECTORY) ?: createSubdirectory(LAYOUT_DIRECTORY) }
}