package com.karsatech.mypokedex.core.data.repository

import com.karsatech.mypokedex.core.common.base.BaseRepository
import com.karsatech.mypokedex.core.data.source.remote.ApiService
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AppRepository, BaseRepository() {
    override fun getListData() = collectApiResult(
        fetchApi = { apiService.getListData() },
        transformData = { it.map { response -> response.mapToEntity() } }
    )
}