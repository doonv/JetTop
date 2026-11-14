package doonv.jettop.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import doonv.jettop.data.Settings
import doonv.jettop.data.settingsDataStore
import kotlinx.coroutines.launch

class SettingsViewModel(app: Application) : AndroidViewModel(app) {

    private val store = app.settingsDataStore
    val settings = store.data

    fun updateSettings(transform: (Settings) -> Settings) {
        viewModelScope.launch {
            store.updateData(transform)
        }
    }
}
