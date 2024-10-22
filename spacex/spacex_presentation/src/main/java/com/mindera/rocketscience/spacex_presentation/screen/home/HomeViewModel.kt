package com.mindera.rocketscience.spacex_presentation.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mindera.rocketscience.preference.SpaceXPreference
import com.mindera.rocketscience.spacex_domain.model.CompanyInfo
import com.mindera.rocketscience.spacex_domain.model.SpaceXLaunchItem
import com.mindera.rocketscience.spacex_domain.usecase.SpaceXUseCases
import com.mindera.rocketscience.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//@HiltViewModel
class HomeViewModel(
    private val spaceXUseCase: SpaceXUseCases,
    private val spaceXPreference: SpaceXPreference
) : ViewModel() {
    private var _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState = _uiState.asStateFlow()


    private val _allLaunches = MutableStateFlow(spaceXUseCase.getAllLaunchUseCase())
    val allLaunches: StateFlow<Flow<PagingData<SpaceXLaunchItem>>> = _allLaunches.asStateFlow()

    val scrollPosition by mutableIntStateOf(spaceXPreference.getScrollPosition())

    fun getCompanyInfo() {
        viewModelScope.launch {
            spaceXUseCase.getCompanyInfoUseCase().collect { result: Result<CompanyInfo> ->
                result.onSuccess { companyInfo ->
                    val newState = uiState.value.copy(
                        companyName = companyInfo.name,
                        founderName = companyInfo.founder,
                        employees = companyInfo.employees,
                        launchSites = companyInfo.launchSites,
                        valuation = companyInfo.valuation,
                    )
                    _uiState.tryEmit(newState)
                }
                    .onFailure { exception: Throwable ->
                        exception.message?.let { message: String ->
                            _uiState.tryEmit(
                                uiState.value.copy(
                                    error = UiText.DynamicString(message)
                                )
                            )
                        }
                    }
            }
        }
    }

    fun fetchAllSpaceXLaunchItems() {
        viewModelScope.launch {
            spaceXUseCase.fetchAndStoreAllLaunchesUseCase()
                .onFailure { exception: Throwable ->

                    exception.message?.let { message: String ->
                        _uiState.tryEmit(uiState.value.copy(error = UiText.DynamicString(message)))
                    }
                }
                .onSuccess {
                    _uiState.tryEmit(uiState.value.copy(error = null))
                }
        }
    }

    fun persistScrollPosition(position: Int) {
        viewModelScope.launch {
            spaceXPreference.persistScrollPosition(position)
        }
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.FilterSpaceXItems -> {
                _allLaunches.value = spaceXUseCase.filterAllLaunchUseCase(
                    year = event.year,
                    onlySuccessfulLaunch = event.onlySuccessfulLaunch
                ).cachedIn(viewModelScope)
            }
        }
    }
}