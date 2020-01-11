package dev.shog.lib.token

import java.io.Serializable

/**
 * An authorization token.
 */
data class Token(
        val token: String,
        val owner: Long,
        val createdOn: Long,
        val expiresOn: Long
) : Serializable