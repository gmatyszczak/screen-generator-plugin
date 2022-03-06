package ui.newscreen.dagger

import dagger.MapKey
import ui.newscreen.NewScreenAction
import kotlin.reflect.KClass

@MapKey
annotation class NewScreenActionKey(val value: KClass<out NewScreenAction>)
