package com.firmino.geriodonto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firmino.geriodonto.data.database.DatabaseSeeder
import com.firmino.geriodonto.data.database.MedRepository
import com.firmino.geriodonto.data.database.MedWithInteractions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedViewModel @Inject constructor(
    val repository: MedRepository,
    private val databaseSeeder: DatabaseSeeder
) : ViewModel() {
    init { viewModelScope.launch { databaseSeeder.checkAndSeedDatabase() } }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val medsList: StateFlow<List<MedWithInteractions>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                repository.allMedsFlow
            } else {
                repository.searchMeds(query)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }


    suspend fun getMed(id: String): MedWithInteractions? {
        return repository.getMedById(id)
    }
}
