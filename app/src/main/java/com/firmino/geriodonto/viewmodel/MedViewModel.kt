package com.firmino.geriodonto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firmino.geriodonto.data.database.DatabaseSeeder
import com.firmino.geriodonto.data.database.MedRepository
import com.firmino.geriodonto.data.database.MedWithInteractions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedViewModel @Inject constructor(
    repository: MedRepository,
    private val databaseSeeder: DatabaseSeeder,
) : ViewModel() {
    private val _seedingState = MutableStateFlow<SeedingState>(SeedingState.Idle)
    val seedingState: StateFlow<SeedingState> = _seedingState.asStateFlow()
    val medsList: StateFlow<List<MedWithInteractions>> = repository.allMedsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList(),
    )

    fun seedDatabase() {
        if (_seedingState.value is SeedingState.Loading || _seedingState.value is SeedingState.Success) return
        _seedingState.value = SeedingState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                databaseSeeder.checkAndSeedDatabase()
                _seedingState.value = SeedingState.Success
            } catch (e: Exception) {
                _seedingState.value = SeedingState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
}

sealed interface SeedingState {
    object Loading : SeedingState
    object Success : SeedingState
    object Idle : SeedingState
    data class Error(val message: String) : SeedingState
}