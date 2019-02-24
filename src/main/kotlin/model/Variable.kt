package model

enum class Variable(val value: String, val description: String) {
    NAME("%name%", "Name of the screen."),
    NAME_SNAKE_CASE("%nameSnakeCase%", "Name of the screen written in snake_case."),
    SCREEN_ELEMENT("%screenElement%", "Screen element's name."),
    PACKAGE_NAME("%packageName%", "Full package name."),
    ANDROID_COMPONENT_SHORT_NAME("%androidComponentShortName%", "Android component's short name, e.g. Activity"),
    ANDROID_COMPONENT_LONG_NAME("%androidComponentLongName%", "Android component's last part from it's full base class, e.g. AppCompatActivity"),
    ANDROID_COMPONENT_FULL_NAME("%androidComponentFullName%", "Android component's full name from taken from base class, e.g. androidx.appcompat.app.AppCompatActivity")
}