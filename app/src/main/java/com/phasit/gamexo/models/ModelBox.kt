package com.phasit.gamexo.models

import kotlinx.serialization.Serializable

@Serializable
data class ModelBox(
    var player: String = "",
    var isHighlight: Boolean = false
)