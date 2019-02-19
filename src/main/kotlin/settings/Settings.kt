package settings

import java.io.Serializable

data class Settings(val screenElements: List<ScreenElement> = emptyList()) : Serializable