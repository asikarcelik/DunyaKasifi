package com.asikarcelik.dnyakaifi.di

import com.asikarcelik.dnyakaifi.ui.viewmodel.ExplorerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ExplorerViewModel() }
} 