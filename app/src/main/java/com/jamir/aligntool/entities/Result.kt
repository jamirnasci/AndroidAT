package com.jamir.aligntool.entities

import java.io.Serializable

data class Result(
    val score: Int,
    val similarity: Float,
    val size: Int,
    val matches: Int,
    val gaps: Int,
    val mismatches: Int
): Serializable
