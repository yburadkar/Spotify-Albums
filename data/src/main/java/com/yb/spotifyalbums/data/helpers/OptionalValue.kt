package com.yb.spotifyalbums.data.helpers

import com.yb.spotifyalbums.domain.models.Optional

data class OptionalValue<T>(
    override val value: T?
) : Optional<T>

fun <T> T?.asOptional(): Optional<T> = OptionalValue(this)