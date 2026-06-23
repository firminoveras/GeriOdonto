package com.firmino.geriodonto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firmino.geriodonto.data.datastorage.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: SettingsRepository) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = repository.settings.map { settings ->
        SettingsUiState.Success(settings)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SettingsUiState.Loading,
    )

    fun saveLightMode(value: String) = viewModelScope.launch { repository.saveLightMode(value) }
    fun saveAccentColor(value: String) = viewModelScope.launch { repository.saveAccentColor(value) }
    fun saveOledMode(value: String) = viewModelScope.launch { repository.saveOledMode(value) }
    fun savePallete(value: String) = viewModelScope.launch { repository.savePallete(value) }
}

sealed interface SettingsUiState {
    object Loading : SettingsUiState
    data class Success(val settings: SettingsRepository.UserSettings) : SettingsUiState
}