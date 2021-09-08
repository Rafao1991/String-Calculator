import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AddTest {
    @Test
    fun assert_simple_cases() {
        assertEquals(8, add("1,2,5"))
        assertEquals(8, add("2,2,2,2,0"))
        assertEquals(10, add("5,5"))
        assertEquals(1000, add("500,500"))
        assertEquals(1111, add("1000,100,10,1"))
    }

    @Test
    fun assert_empty() {
        assertEquals(0, add(""))
        assertEquals(0, add("  "))
        assertEquals(0, add(","))
    }

    @Test
    fun assert_with_new_lines() {
        assertEquals(6, add("1\n,2,3"))
        assertEquals(7, add("1,\n2,4"))
        assertEquals(8, add("\n2,2,\n2\n,2\n\n\n"))
    }

    @Test
    fun assert_with_delimiter() {
        assertEquals(8, add("//;\n1;3;4"))
        assertEquals(6, add("//$\n1$2$3"))
        assertEquals(13, add("//@\n2@3@8@0"))
        assertEquals(13, add("//@!\n2@!3@!8"))
        assertEquals(13, add("//@,!\n2@3!8"))
        assertEquals(14, add("//**,*,***\n2*3***8**1"))
        assertEquals(14, add("//**,$$$,^\n2^3$$$8**1"))
    }

    @Test
    fun assert_with_negative_numbers() {
        var exception = assertFailsWith<Exception> {
            add("//;\n1;-3;4")
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)

        exception = assertFailsWith {
            add("//@!\n-2@!3@!8")
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)

        exception = assertFailsWith {
            add("1,-2,5")
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)

        exception = assertFailsWith {
            add("2,2,-2,-2")
        }
        assertEquals(EXCEPTION_MESSAGE, exception.message)
    }

    @Test
    fun assert_ignore_greater_than_1000() {
        assertEquals(8, add("1,1002,2,5"))
        assertEquals(8, add("2,2,2,2,5000,0"))
        assertEquals(10, add("5,5,99999"))
    }
}