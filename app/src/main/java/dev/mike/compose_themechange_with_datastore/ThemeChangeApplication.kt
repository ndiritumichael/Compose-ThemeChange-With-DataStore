package dev.mike.compose_themechange_with_datastore

import android.app.Application

class ThemeChangeApplication : Application() {
    val dataStoreRepo by lazy {

        DataStoreRepo(this)
    }
}
