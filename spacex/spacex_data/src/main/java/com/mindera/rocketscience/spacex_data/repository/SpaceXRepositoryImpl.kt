package com.mindera.rocketscience.spacex_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mindera.rocketscience.spacex_data.local.database.SpaceXDao
import com.mindera.rocketscience.spacex_data.local.entity.SpaceXLaunchItemEntity
import com.mindera.rocketscience.spacex_data.mapper.toCompanyInfo
import com.mindera.rocketscience.spacex_data.mapper.toSpaceXLaunchItem
import com.mindera.rocketscience.spacex_data.mapper.toSpaceXLaunchItemEntity
import com.mindera.rocketscience.spacex_data.remote.dto.AllSpaceXLaunchesResponseItem
import com.mindera.rocketscience.spacex_data.remote.service.SpaceXApiServiceRetrofit
import com.mindera.rocketscience.spacex_domain.model.CompanyInfo
import com.mindera.rocketscience.spacex_domain.model.SpaceXLaunchItem
import com.mindera.rocketscience.spacex_domain.repository.SpaceXRepository
import com.mindera.rocketscience.util.SpaceXException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException

class SpaceXRepositoryImpl(
    private val spaceXDao: SpaceXDao,
    private val apiService: SpaceXApiServiceRetrofit
) : SpaceXRepository {
    override fun getCompanyInfo(): Flow<Result<CompanyInfo>> = flow {
        try {

//            val apiResponse = apiService.getCompanyInfo().toCompanyInfo()
//            emit(Result.success(apiResponse))
            val response = apiService.getCompanyInfo()
            if (response.isSuccessful && response.body() != null) {
                emit(
                    Result.success(
                        response.body()!!.toCompanyInfo()
                    )
                )
            } else {
                val errorMessage =
                    "Get company info failed: ${response.code()}"
                emit(Result.failure(Exception(errorMessage)))
            }
        } catch (e: IOException) {
            val errorMessage = "Network error: ${e.message}"
            emit(Result.failure(SpaceXException.NetworkException(errorMessage)))
        } catch (e: Exception) {
            val errorMessage = "Unknown API error: ${e.message}"
            emit(Result.failure(SpaceXException.ApiException(errorMessage, -1)))
        }
    }

    override suspend fun fetchAndStoreALlSpaceItemLaunches(): Result<Boolean> {

        return try {

            val apiResponse = apiService.getAllLaunches()
//            spaceXDao.insertSpaceXLaunchItem(apiResponse.map { it.toSpaceXLaunchItemEntity() })
//            Result.success(true)
            if (apiResponse.isSuccessful && apiResponse.body() != null) {
                val result = apiResponse.body()!!.map { value: AllSpaceXLaunchesResponseItem ->
                    value.toSpaceXLaunchItemEntity()
                }
                Result.success(true)
            } else {
                val errorMessage =
                    "Get all launches failed: - ${apiResponse.code()}"
                Result.failure(Exception(errorMessage))
            }

        } catch (e: IOException) {
            val errorMessage = "Network error: ${e.message}"
            Result.failure(SpaceXException.NetworkException(errorMessage))
        } catch (e: Exception) {
            val errorMessage = "Unknown API error: ${e.message}"
            Result.failure(SpaceXException.ApiException(errorMessage, -1))
        }
    }


    override fun getSpaceXLaunchItem(): Flow<PagingData<SpaceXLaunchItem>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                spaceXDao.getSpaceXLaunchItem()
            }
        ).flow
            .map { value: PagingData<SpaceXLaunchItemEntity> ->
                value.map { spaceXLaunchItemEntity ->
                    spaceXLaunchItemEntity.toSpaceXLaunchItem()
                }
            }
    }

    private companion object {
        const val PAGE_SIZE = 10
    }
}