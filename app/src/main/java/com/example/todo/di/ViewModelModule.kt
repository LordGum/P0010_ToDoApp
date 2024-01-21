package com.example.todo.di

import androidx.lifecycle.ViewModel
import com.example.todo.presentation.add_item_package.AddItemViewModel
import com.example.todo.presentation.main_activity.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddItemViewModel::class)
    fun bindAddItemViewModel(viewModel: AddItemViewModel): ViewModel
}