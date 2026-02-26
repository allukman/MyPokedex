package com.karsatech.mypokedex.core.data.repository

import com.karsatech.mypokedex.core.common.utils.state.ApiState
import com.karsatech.mypokedex.core.data.model.local.SampleModelEntity
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getListData(): Flow<ApiState<List<SampleModelEntity>>>
}