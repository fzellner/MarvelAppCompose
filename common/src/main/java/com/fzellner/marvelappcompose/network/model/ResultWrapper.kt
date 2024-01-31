package com.fzellner.marvelappcompose.network.model

sealed class ResultWrapper <out success: Any, out failure: Any> {
    data class Success <success: Any>(val data: success): ResultWrapper<success, Nothing>()
    data class Error <failure: Any>(val error: failure): ResultWrapper<Nothing, failure>()
}