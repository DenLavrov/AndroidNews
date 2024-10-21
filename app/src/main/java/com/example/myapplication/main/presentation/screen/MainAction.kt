package com.example.myapplication.main.presentation.screen

sealed class MainAction {
    data class UpdateQuery(val query: String) : MainAction()

    data object Init : MainAction()
}