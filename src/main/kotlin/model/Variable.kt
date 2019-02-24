package model

enum class Variable(val value: String, val description: String) {
    NAME("%screenName%", "Name of the screen."),
    NAME_SNAKE_CASE("%screenNameSnakeCase%", "Name of the screen written in snake_case."),
    SCREEN_ELEMENT("%screenElement%", "Screen element's name."),
    PACKAGE_NAME("%packageName%", "Full package name."),
    ANDROID_COMPONENT_SHORT_NAME("%component%", "Android component's name, e.g. Activity or Fragment"),
    ANDROID_COMPONENT_CLASS_NAME("%componentClass%", "Android component's base class, e.g. AppCompatActivity"),
    ANDROID_COMPONENT_FULL_CLASS_NAME("%componentClassFull%", "Android component's base class with package name, e.g. androidx.appcompat.app.AppCompatActivity")
}