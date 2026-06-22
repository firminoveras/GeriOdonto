package com.firmino.geriodonto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firmino.geriodonto.data.InteractionAlertLevel
import com.firmino.geriodonto.data.MedClass
import com.firmino.geriodonto.data.Risk
import com.firmino.geriodonto.data.database.DatabaseSeeder
import com.firmino.geriodonto.data.database.MedRepository
import com.firmino.geriodonto.data.database.MedWithInteractions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class MedViewModel @Inject constructor(
    private val repository: MedRepository,
    private val databaseSeeder: DatabaseSeeder,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val filteredMedsList: StateFlow<List<Med>> = _searchQuery
        .debounce(300.milliseconds)
        .flatMapLatest { query ->
            repository.searchMeds(query)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList(),
        )

    private val _seedingState = MutableStateFlow<SeedingState>(SeedingState.Idle)
    val seedingState: StateFlow<SeedingState> = _seedingState.asStateFlow()
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

    private val _suggestionMedsQuery = MutableStateFlow<MedClass?>(null)
    fun onSuggestionMedsQueryChanged(newQuery: MedClass?) {
        _suggestionMedsQuery.value = newQuery
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val medsByClass: StateFlow<List<Med>> = _suggestionMedsQuery
        .flatMapLatest { medClass ->
            if (medClass != null) repository.searchMedsByClass(medClass)
            else emptyFlow()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList(),
        )

    suspend fun getMedById(id: String, type: MedListType, addedBy: String) = repository.getMedById(id)?.copy(type = type, addedBy = addedBy)
}

sealed interface SeedingState {
    object Loading : SeedingState
    object Success : SeedingState
    object Idle : SeedingState
    data class Error(val message: String) : SeedingState
}

enum class MedListType {
    PRE,
    POS
}

data class Med(
    val id: String,
    val name: String,
    val description: String,
    val principleActive: String,
    val medClass: MedClass,
    val byDisease: String,
    val risks: List<Risk>,
    val interactions: List<Interaction>,
    val addedBy: String = "",
    val type: MedListType? = null,
)

data class Interaction(
    val interactingMedName: String,
    val interactingMedId: String,
    val risk: Risk,
    val description: String,
    val alertLevel: InteractionAlertLevel,
)

fun MedWithInteractions.toMed(
    mappedInteractions: List<Interaction>,
    addedBy: String = "",
    type: MedListType? = null,
) = Med(
    id = this.med.id,
    name = this.med.name,
    description = this.med.description,
    principleActive = this.med.principleActive,
    medClass = this.med.medClass,
    byDisease = this.med.byDisease,
    risks = this.med.risks,
    interactions = mappedInteractions,
    addedBy = addedBy,
    type = type,
)
