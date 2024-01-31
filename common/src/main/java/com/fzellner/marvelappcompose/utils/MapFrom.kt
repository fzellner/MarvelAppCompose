package com.fzellner.marvelappcompose.utils

interface Mapper<in T, out Y> {
    fun mapFrom(from:T): Y
}