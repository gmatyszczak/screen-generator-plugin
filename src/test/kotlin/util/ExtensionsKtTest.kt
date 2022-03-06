package util

import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test

class ExtensionsKtTest {

    @Test
    fun `to snake case`() {
        assertEquals("test_view", "TestView".toSnakeCase())
        assertEquals("test_presenter_ok", "TestPresenter_Ok".toSnakeCase())
        assertEquals("already_camel_case", "already_camel_case".toSnakeCase())
        assertEquals("single", "Single".toSnakeCase())
    }

    @Test
    fun `swap`() {
        assertEquals(mutableListOf("test1", "test3", "test2"), mutableListOf("test1", "test2", "test3").apply { swap(1, 2) })
    }
}
