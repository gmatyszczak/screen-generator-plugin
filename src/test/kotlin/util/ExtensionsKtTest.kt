package util

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

class ExtensionsKtTest {

    @Test
    fun `to snake case`() {
        "TestView".toSnakeCase() shouldBeEqualTo "test_view"
        "TestPresenter_Ok".toSnakeCase() shouldBeEqualTo "test_presenter_ok"
        "already_camel_case".toSnakeCase() shouldBeEqualTo "already_camel_case"
        "Single".toSnakeCase() shouldBeEqualTo "single"
    }

    @Test
    fun `swap`() {
        mutableListOf("test1", "test2", "test3").apply { swap(1, 2) } shouldBeEqualTo mutableListOf(
            "test1",
            "test3",
            "test2"
        )
    }
}
