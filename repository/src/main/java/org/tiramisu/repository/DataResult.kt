package org.tiramisu.repository

typealias DataResult<V> = Result<V, DataException>

fun <M, N> DataResult<M>.transform(block: (M) -> N): DataResult<N> {
    return when (this) {
        is Result.Success -> Result.success(block(this.get()))
        is Result.Failure -> Result.error(this.getException())
    }
}

sealed class Result<out V, out E : Exception> {

    open operator fun component1(): V? = null
    open operator fun component2(): E? = null

    inline fun <X> fold(success: (V) -> X, failure: (E) -> X): X = when (this) {
        is Success -> success(this.value)
        is Failure -> failure(this.error)
    }

    abstract fun get(): V

    abstract fun isSuccess(): Boolean

    class Success<out V>(val value: V) : Result<V, Nothing>() {
        override fun component1(): V? = value

        override fun get(): V = value

        override fun toString() = "[Success: $value]"

        override fun hashCode(): Int = value.hashCode()

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            return other is Success<*> && value == other.value
        }

        override fun isSuccess(): Boolean = true
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

        override fun isSuccess(): Boolean = false
    }

    companion object {
        // Factory methods
        fun <E : Exception> error(ex: E) = Failure(ex)

        fun <V> success(v: V) = Success(v)

        fun <V, E : Exception> of(value: V?, fail: (() -> E)): Result<V, E> =
            value?.let { success(it) } ?: error(fail())

        fun <V, E: Exception> of(f: () -> V): Result<V, E> = try {
            success(f())
        } catch (ex: Exception) {
            error(ex as E)
        }
    }

}