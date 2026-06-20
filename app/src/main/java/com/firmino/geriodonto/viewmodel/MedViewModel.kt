package com.firmino.geriodonto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firmino.geriodonto.data.database.DatabaseSeeder
import com.firmino.geriodonto.data.database.Med
import com.firmino.geriodonto.data.database.MedRepository
import com.firmino.geriodonto.data.database.MedWithInteractions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedViewModel @Inject constructor(
    val repository: MedRepository,
    private val databaseSeeder: DatabaseSeeder,
) : ViewModel() {
    private val _seedingState = MutableStateFlow<SeedingState>(SeedingState.Idle)
    private val _searchQuery = MutableStateFlow("")
    private val _excludeMedsIds = MutableStateFlow<Set<String>>(emptySet())
    val seedingState: StateFlow<SeedingState> = _seedingState.asStateFlow()

    fun seedDatabase() {
        if (_seedingState.value is SeedingState.Loading || _seedingState.value is SeedingState.Success) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            _seedingState.value = SeedingState.Loading
            try {
                databaseSeeder.checkAndSeedDatabase()
                _seedingState.value = SeedingState.Success
            } catch (e: Exception) {
                _seedingState.value = SeedingState.Error(e.message ?: "Erro")
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val medsList: StateFlow<List<MedWithInteractions>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                repository.allMedsFlow
            } else {
                repository.searchMeds(query)
            }
        }
        .combine(_excludeMedsIds){ meds, excludeIds ->
            meds.filterNot{ med ->
                excludeIds.contains(med.med.id)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList(),
        )

    fun onSearchQueryChanged(newQuery: String, excludeMeds: Set<Med>) {
        _excludeMedsIds.update { excludeMeds.map { it.id }.toSet() }
        _searchQuery.value = newQuery
    }
}

sealed interface SeedingState {
    object Loading : SeedingState
    object Success : SeedingState
    object Idle : SeedingState
    data class Error(val message: String) : SeedingState
}