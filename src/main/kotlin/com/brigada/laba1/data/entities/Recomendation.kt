package com.brigada.laba1.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Recommendation(
    val recommendation: String,
    val user: String
)