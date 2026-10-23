package doonv.jettop.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import doonv.jettop.R

val GoogleSans = googleSansWithGrade(0)
val GoogleSansHighlighted = googleSansWithGrade(100)

private fun googleSansWithGrade(grade: Int): FontFamily = FontFamily(
    Font(
        R.font.googlesans_lat_he,
        FontWeight.Normal,
        FontStyle.Normal,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(400),
            FontVariation.grade(grade)
        )
    ),
    Font(
        R.font.googlesans_lat_he,
        FontWeight.Medium,
        FontStyle.Normal,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(500),
            FontVariation.grade(grade)
        )
    ),
    Font(
        R.font.googlesans_lat_he,
        FontWeight.SemiBold,
        FontStyle.Normal,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(600),
            FontVariation.grade(grade)
        )
    ),
    Font(
        R.font.googlesans_lat_he,
        FontWeight.Bold,
        FontStyle.Normal,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(700),
            FontVariation.grade(grade)
        )
    ),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
).withFamily(GoogleSans) // TODO: allow users to turn Google Sans off in settings

fun Typography.withFamily(fontFamily: FontFamily): Typography {
    return Typography(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily),
    )
}
