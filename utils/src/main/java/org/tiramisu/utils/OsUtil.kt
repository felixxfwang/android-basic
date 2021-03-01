package org.tiramisu.utils

import java.util.*

/**
 * @author felixxfwang
 */
object OsUtil {

    fun getOsLanguage(): String {
        return Locale.getDefault().language
    }

    fun getOsCountry(): String {
        return Locale.getDefault().country
    }
}