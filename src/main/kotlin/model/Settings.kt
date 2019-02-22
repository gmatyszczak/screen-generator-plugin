package model

data class Settings(val screenElements: List<ScreenElement>,
                    val activityBaseClass: String,
                    val fragmentBaseClass: String)