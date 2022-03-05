package data.file

import model.Module

data class CurrentPath(val path: String, val isDirectory: Boolean, val module: Module)
