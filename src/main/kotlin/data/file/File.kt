package data.file

import model.FileType

data class File(
    val name: String,
    val content: String,
    val fileType: FileType
)