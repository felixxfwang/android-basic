package org.tiramisu.utils.extensions

const val EMPTY_STRING = ""

fun Any?.toStringExt() = this?.toString() ?: EMPTY_STRING