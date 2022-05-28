package com.example.polycareer.di.screen

import com.example.polycareer.domain.usecase.AuthUseCase
import com.example.polycareer.screens.auth.grades.GradesViewModel
import com.example.polycareer.screens.auth.sign_up.SingUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single { AuthUseCase(get(), get()) }
    viewModel { SingUpViewModel(get()) }

    viewModel { GradesViewModel(get()) }
}