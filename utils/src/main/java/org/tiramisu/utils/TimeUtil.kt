package org.tiramisu.utils

import com.blankj.utilcode.util.TimeUtils

object TimeUtil {

    private const val HOUR = 60 * 60 * 1000

    fun millisToStringWithHour(millis: Long): String {
        val hour = millis / HOUR
        val left = millis % HOUR
        val postfix = TimeUtils.millis2String(left, "mm:ss")
        val hourString = String.format("%02d", hour)
        return "$hourString:$postfix"
    }
}