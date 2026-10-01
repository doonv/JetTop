package doonv.jettop.ui.theme

import android.util.Log

object GematriaUtils {
    const val LETTERS = "אבגדהוזחטיכךלמםנןסעפףצץקרשת"
    val VALUES = listOf(
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20, 20, 30, 40, 40,
        50, 50, 60, 70, 80, 80, 90, 90, 100, 200, 300, 400
    )
    const val SEPARATOR = "״"
    private val SKIP = setOf(' ', 'ך', 'ם', 'ן', 'ף', 'ץ')
    fun fromNum(num: Int): String? {
        if (num == 15) return "טו"
        if (num == 16) return "טז"
        if (num !in 1..499) return null

        var num = num
        var str = ""
        for (i in VALUES.indices.reversed()) {
            val c = LETTERS[i]
            if (c in SKIP) continue
            if (VALUES[i] <= num) {
                num -= VALUES[i]
                if (str.isNotEmpty())
                    str += SEPARATOR
                str += c
            }
        }
        return str
    }
}