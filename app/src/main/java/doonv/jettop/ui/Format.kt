package doonv.jettop.ui

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import android.icu.util.ULocale
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLocale
import doonv.jettop.LocalDateType
import doonv.jettop.data.DateType
import java.time.format.DateTimeFormatter

@Composable
fun rememberFullDateFormatter(): DateTimeFormatter {
    val locale = LocalLocale.current.platformLocale
    return remember(locale) {
        DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy", locale)
    }
}


@SuppressLint("SimpleDateFormat")
@Composable
fun formatYear(year: Int): String {
    val date = GregorianCalendar(
        year, Calendar.JANUARY, 1,
    ).time

    val formatter = when (LocalDateType.current) {
        DateType.Gregorian ->
            SimpleDateFormat("y", ULocale.forLocale(LocalLocale.current.platformLocale))

        DateType.Hebrew ->
            SimpleDateFormat("y", "y=hebr", ULocale("he@calendar=hebrew"))
    }

    return formatter.format(date)
}
