package dev.mike.compose_themechange_with_datastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AppViewModel(private val dataStoreRepo: DataStoreRepo) : ViewModel() {

    val theme = dataStoreRepo.currentTheme.map { number ->
        number?.let {
            AppThemes.values()[it]
        }
    }

    fun changeTheme(theme: AppThemes) {
        viewModelScope.launch {

            dataStoreRepo.setTheme(theme.ordinal)
        }
    }
}

class AppViewModelFactory(val dataStoreRepo: DataStoreRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {

            return AppViewModel(dataStoreRepo) as T
        }

        throw IllegalArgumentException("Unkown Viewmodel Class")
    }
}
