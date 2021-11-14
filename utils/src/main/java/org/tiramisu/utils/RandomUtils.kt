package org.tiramisu.utils

import java.lang.StringBuilder
import java.security.SecureRandom

object RandomUtils {

    /**
     * 生成16位不重复的随机数，含数字+大小写
     * @return
     */
    fun getGUID(bits: Int): String {
        val sb = StringBuilder()
        //产生16位的强随机数
        val rd = SecureRandom()
        for (i in 0 until bits) {
            //产生0-2的3位随机数
            when (rd.nextInt(3)) {
                0 -> sb.append(rd.nextInt(10))   //0-9的随机数
                1 -> sb.append((rd.nextInt(25) + 65).toChar()) //ASCII在65-90之间为大写,获取大写随机
                2 -> sb.append((rd.nextInt(25) + 97).toChar()) //ASCII在97-122之间为小写，获取小写随机
            }
        }
        return sb.toString()
    }
}