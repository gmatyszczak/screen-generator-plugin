package util

import org.junit.Assert.assertEquals
import org.junit.Test

class ExtensionsKtTest {

    @Test
    fun `to camel case`() {
        assertEquals("test_view", "TestView".toCamelCase())
        assertEquals("test_presenter_ok", "TestPresenter_Ok".toCamelCase())
        assertEquals("already_camel_case", "already_camel_case".toCamelCase())
        assertEquals("single", "Single".toCamelCase())
    }
}