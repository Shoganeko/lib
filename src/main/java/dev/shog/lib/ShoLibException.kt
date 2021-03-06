package dev.shog.lib

/**
 * A ShoLib exception.
 *
 * @param message The message for the exception.
 */
class ShoLibException internal constructor(message: String, otherException: Exception? = null):
        Exception(message)