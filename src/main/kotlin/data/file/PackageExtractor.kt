package data.file

import data.repository.SourceRootRepository

interface PackageExtractor {

    fun extractFromCurrentPath(): String
}

class PackageExtractorImpl(private val currentPath: CurrentPath?,
                           private val sourceRootRepository: SourceRootRepository) : PackageExtractor {

    override fun extractFromCurrentPath(): String {
        val sourceRootPath = sourceRootRepository.findCodeSourceRoot().path
        return if (currentPath != null && currentPath.path != sourceRootPath && currentPath.path.contains(sourceRootPath)) {
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
