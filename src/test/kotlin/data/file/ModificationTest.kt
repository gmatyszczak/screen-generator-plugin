package data.file

import io.mockk.every
import io.mockk.mockk
import model.Anchor
import model.AnchorPosition
import model.AndroidComponent
import model.CustomVariable
import model.FileType
import model.ScreenElement
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

internal class ModificationTest {

    val screenName = "screenName"
    val packageName = "packageName"
    val customVariables: Map<CustomVariable, String> = mockk()
    val screenElement: ScreenElement = mockk {
        every { fileType } returns FileType.KOTLIN
        every { anchor } returns Anchor.CLASS
        every { anchorPosition } returns AnchorPosition.TOP
        every { anchorName } returns "anchorName"
    }

    @Test
    fun `when toModification called`() {
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

        screenElement.toModification(
            screenName,
            packageName,
            AndroidComponent.ACTIVITY,
            customVariables
        ) shouldBeEqualTo Modification(
            "fileName",
            "body",
            FileType.KOTLIN,
            Anchor.CLASS,
            AnchorPosition.TOP,
            "anchorName",
        )
    }
}