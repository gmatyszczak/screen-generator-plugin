package model

import java.io.Serializable

data class Settings(val screenElements: List<ScreenElement>,
                    val activityBaseClass: String,
                    val fragmentBaseClass: String) : Serializable