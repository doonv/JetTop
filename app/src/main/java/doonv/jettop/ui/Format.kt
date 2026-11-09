package doonv.jettop.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLocale
import java.time.format.DateTimeFormatter

@Composable
fun rememberFullDateFormatter(): DateTimeFormatter {
    val locale = LocalLocale.current.platformLocale
    return remember(locale) {
        DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy", locale)
    }
}
