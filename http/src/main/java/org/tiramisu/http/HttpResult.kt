package org.tiramisu.http

typealias HttpResult<V> = Result<V, HttpException>

sealed class Result<out V, out E : Exception> {

    open operator fun component1(): V? = null
    open operator fun component2(): E? = null

    inline fun <X> fold(success: (V) -> X, failure: (E) -> X): X = when (this) {
        is Success -> success(this.value)
        is Failure -> failure(this.error)
    }

    abstract fun get(): V

    class Success<out V : Any>(val value: V) : Result<V, Nothing>() {
        override fun component1(): V? = value

        override fun get(): V = value

        override fun toString() = "[Success: $value]"

        override fun hashCode(): Int = value.hashCode()

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            return other is Success<*> && value == other.value
        }
    }

    class Failure<out E : Exception>(val error: E) : Result<Nothing, E>() {
        override fun component2(): E? = error

        override fun get() = throw error

        fun getException(): E = error

        override fun toString() = "[Failure: $error]"

        override fun hashCode(): Int = error.hashCode()

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            return other is Failure<*> && error == other.error
        }
    }

    companion object {
        // Factory methods
        fun <E : Exception> error(ex: E) = Failure(ex)

        fun <V : Any> success(v: V) = Success(v)

        fun <V : Any, E : Exception> of(value: V?, fail: (() -> E)): Result<V, E> =
                value?.let { success(it) } ?: error(fail())

        fun <V : Any, E: Exception> of(f: () -> V): Result<V, E> = try {
            success(f())
        } catch (ex: Exception) {
            error(ex as E)
        }
    }

}