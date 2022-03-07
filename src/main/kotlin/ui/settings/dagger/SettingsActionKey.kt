package ui.settings.dagger

import dagger.MapKey
import ui.settings.SettingsAction
import kotlin.reflect.KClass

@MapKey
annotation class SettingsActionKey(val value: KClass<out SettingsAction>)
