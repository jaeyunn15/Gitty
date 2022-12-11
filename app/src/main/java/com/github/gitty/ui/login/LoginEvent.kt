package com.github.gitty.ui.login

sealed interface LoginScreenEvent {
    object StartGitAuth: LoginScreenEvent
    data class Login(val code: String?): LoginScreenEvent
    object StartMain: LoginScreenEvent
}