package com.fzellner.marvelappcompose.network.utils

interface Mapper<in T, out Y> {
    fun mapFrom(from:T): Y
}