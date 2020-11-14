package ui.settings

sealed class SettingsEffect {

    object ShowHelp : SettingsEffect()
    data class SelectScreenElement(val index: Int) : SettingsEffect()
}