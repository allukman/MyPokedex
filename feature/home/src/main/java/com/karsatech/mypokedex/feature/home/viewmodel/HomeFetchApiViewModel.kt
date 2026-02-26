package com.karsatech.mypokedex.feature.home.viewmodel

import com.karsatech.mypokedex.core.common.base.BaseViewModel
import com.karsatech.mypokedex.core.common.utils.state.UiState
import com.karsatech.mypokedex.core.common.utils.state.UiState.StateInitial
import com.karsatech.mypokedex.core.data.model.local.SampleModelEntity
import com.karsatech.mypokedex.core.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeFetchApiViewModel @Inject constructor(
    private val repo: AppRepository
) : BaseViewModel() {

    private val _sampleResponse = MutableStateFlow<UiState<List<SampleModelEntity>>>(StateInitial)
    val sampleResponse = _sampleResponse.asStateFlow()

    fun getSampleData() = collectApiAsUiState(
        response = repo.getListData(),
        updateState = { _sampleResponse.value = it }
    )
}