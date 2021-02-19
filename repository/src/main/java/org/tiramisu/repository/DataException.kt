package org.tiramisu.repository

import java.lang.Exception

class DataException(val code: Int, message: String?, cause: Throwable? = null): Exception(message, cause)