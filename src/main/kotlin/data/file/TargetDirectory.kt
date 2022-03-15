package data.file

import data.repository.SourceRootRepository
import model.FileType
import model.Module
import model.ScreenElement
import javax.inject.Inject

private const val LAYOUT_DIRECTORY = "layout"

class TargetDirectory @Inject constructor(
    private val sourceRootRepository: SourceRootRepository,
) {

    fun findOrCreate(screenElement: ScreenElement, module: Module, packageName: String): Directory? {
        val parentDirectory = when (screenElement.fileType) {
            FileType.KOTLIN -> findCodeParentDirectory(packageName, module, screenElement.sourceSet)
            FileType.LAYOUT_XML -> findResourcesParentDirectory(module)
        }
        return if (parentDirectory != null) {
            if (screenElement.subdirectory.isNotEmpty()) {
                parentDirectory.findOrCreateSubdirectory(screenElement.subdirectory)
            } else {
                parentDirectory
            }
        } else {
            null
        }
    }

    private fun findCodeParentDirectory(packageName: String, module: Module, sourceSet: String): Directory? =
        sourceRootRepository.findCodeSourceRoot(module, sourceSet)?.run {
            var subdirectory = directory
            packageName.split(".").forEach {
                subdirectory = subdirectory.findSubdirectory(it) ?: subdirectory.createSubdirectory(it)
            }
            return subdirectory
        }

    private fun findResourcesParentDirectory(module: Module) =
        sourceRootRepository.findResourcesSourceRoot(module)?.directory?.run {
            findSubdirectory(LAYOUT_DIRECTORY) ?: createSubdirectory(LAYOUT_DIRECTORY)
        }

    private fun Directory.findOrCreateSubdirectory(subdirectory: String): Directory {
        var newSubdirectory = this
        subdirectory.split("/").forEach { segment ->
            newSubdirectory = findSubdirectory(segment) ?: createSubdirectory(segment)
        }
        return newSubdirectory
    }
}
