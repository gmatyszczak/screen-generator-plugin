package data.file

import data.repository.SourceRootRepository
import javax.inject.Inject

interface PackageExtractor {

    fun extractFromCurrentPath(): String
}

class PackageExtractorImpl @Inject constructor(
    private val currentPath: CurrentPath?,
    private val sourceRootRepository: SourceRootRepository
) : PackageExtractor {

    override fun extractFromCurrentPath(): String {
        val sourceRootPath = currentPath?.let { sourceRootRepository.findCodeSourceRoot(currentPath.module)?.path }
        return if (currentPath != null && sourceRootPath != null && currentPath.path != sourceRootPath && currentPath.path.contains(
                sourceRootPath
            )
        ) {
            currentPath.path.removePrefix("$sourceRootPath/")
                .removeFilePath(currentPath.isDirectory)
                .replace("/", ".")
        } else {
            ""
        }
    }

    private fun String.removeFilePath(isDirectory: Boolean) =
        if (!isDirectory) {
            removeRange(indexOfLast { it == '/' }, length)
        } else {
            this
        }

}
