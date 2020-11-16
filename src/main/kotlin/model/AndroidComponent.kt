package model

enum class AndroidComponent(val displayName: String) {
    ACTIVITY("Activity"),
    FRAGMENT("Fragment"),
    NONE("None");

    override fun toString() = displayName
}