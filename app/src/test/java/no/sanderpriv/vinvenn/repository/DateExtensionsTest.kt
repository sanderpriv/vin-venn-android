package no.sanderpriv.vinvenn.repository

import org.junit.Test
import java.util.Date
import java.util.concurrent.TimeUnit
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DateExtensionsTest {

    @Test
    fun testIsExpired_withPastDate_returnsTrue() {
        val pastDate = Date().apply { time -= TimeUnit.HOURS.toMillis(1) }
        assertTrue(pastDate.isExpired())
    }

    @Test
    fun testIsExpired_withFutureDate_returnsFalse() {
        val futureDate = Date().apply { time += TimeUnit.HOURS.toMillis(1) }
        assertFalse(futureDate.isExpired())
    }
}
