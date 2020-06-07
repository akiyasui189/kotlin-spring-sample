package io.spring.start.kotlin.sample.exceptions

import java.lang.RuntimeException

class AlreadyExistsException : RuntimeException {
    constructor(message: String, cause: Throwable): super(message, cause) {}
    constructor(message: String): super(message) {}
    constructor(cause: Throwable): super(cause) {}
}