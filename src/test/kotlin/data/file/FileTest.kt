package data.file

import io.mockk.every
import io.mockk.mockk
import model.AndroidComponent
import model.CustomVariable
import model.FileType
import model.ScreenElement
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

internal class FileTest {

    val screenName = "screenName"
    val packageName = "packageName"
    val customVariables: Map<CustomVariable, String> = mockk()
    val screenElement: ScreenElement = mockk {
        every { fileType } returns FileType.KOTLIN
    }

    @Test
    fun `when toFile called`() {
        every {
            screenElement.fileName(
                screenName,
                packageName,
                AndroidComponent.ACTIVITY.displayName,
                customVariables
            )
        } returns "fileName"
        every {
            screenElement.body(
                screenName,
                packageName,
                AndroidComponent.ACTIVITY.displayName,
                customVariables
            )
        } returns "body"

        screenElement.toFile(
            screenName,
            packageName,
            AndroidComponent.ACTIVITY,
            customVariables
        ) shouldBeEqualTo File(
            "fileName",
            "body",
            FileType.KOTLIN,
        )
    }
}