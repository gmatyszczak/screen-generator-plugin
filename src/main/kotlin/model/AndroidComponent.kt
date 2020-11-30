package model

enum class AndroidComponent(val displayName: String) {
    NONE("None"),
    ACTIVITY("Activity"),
    FRAGMENT("Fragment");

    override fun toString() = displayName
}