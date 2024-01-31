package com.fzellner.marvelappcompose.presentation.state

enum class ListState {
    IDLE,
    LOADING,
    PAGINATING,
    ERROR,
    PAGINATION_EXHAUST,
}