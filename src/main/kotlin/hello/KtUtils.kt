package hello

import java.util.Optional

fun <T> Optional<T>.orNull(): T? = this.orElse(null)