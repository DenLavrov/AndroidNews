package com.example.myapplication.main.presentation.screen

sealed class MainEffect {
    data object Refresh : MainEffect()
}