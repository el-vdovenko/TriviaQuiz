package com.vdovenko.triviaquiz.data.repository

import com.vdovenko.triviaquiz.data.mapper.toEntity
import com.vdovenko.triviaquiz.data.network.api.ApiService
import com.vdovenko.triviaquiz.domain.entities.Category
import com.vdovenko.triviaquiz.domain.entities.DataError
import com.vdovenko.triviaquiz.domain.entities.Question
import com.vdovenko.triviaquiz.domain.entities.Resource
import com.vdovenko.triviaquiz.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class RepositoryImpl(private val apiService: ApiService) : Repository {

    override suspend fun getCategories(): Resource<List<Category>, DataError> {

        return withContext(Dispatchers.IO) {
            val response = safeApiCall { apiService.getCategories() }
            when (response) {
                is Resource.Success -> {
                    val data = response.data.categoriesList.map { it.toEntity() }
                    Resource.Success(data)
                }

                is Resource.Error -> {
                    response as Resource<List<Category>, DataError>
                }
            }
        }
    }

    override suspend fun getQuestions(
        sessionToken: String?,
        categoryId: Int?,
        difficulty: String,
        amount: Int
    ): Resource<List<Question>, DataError> {
        return withContext(Dispatchers.IO) {
            val response = safeApiCall {
                apiService.getQuestions(
                    sessionToken = sessionToken,
                    category = categoryId,
                    difficulty = difficulty,
                    amount = amount
                )
            }
            when (response) {
                is Resource.Success -> {
                    val responseCode = response.data.responseCode
                    handleApiAnswer(responseCode, response.data.questionsList.map { it.toEntity() })
                }

                is Resource.Error -> {
                    response as Resource<List<Question>, DataError>
                }
            }
        }
    }

    override suspend fun getToken(): Resource<String, DataError> {
        return withContext(Dispatchers.IO) {
            val response = safeApiCall { apiService.getToken() }
            when(response) {
                is Resource.Success -> {
                    val responseCode = response.data.responseCode
                    handleApiAnswer(responseCode, response.data.token)
                }
                is Resource.Error -> {
                    response as Resource<String, DataError>
                }
            }
        }
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T, DataError> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error(DataError.Network.EMPTY_BODY)
            } else {
                when(response.code()) {
                    408 -> Resource.Error(DataError.Network.REQUEST_TIMEOUT)
                    429 -> Resource.Error(DataError.Network.TOO_MANY_REQUESTS)
                    else -> Resource.Error(
                        DataError.Network.UNKNOWN,
                        message = "${response.code()} -- ${response.message()}"
                    )
                }
            }
        } catch (e: IOException) {
            Resource.Error(DataError.Network.NO_INTERNET)
        } catch (e: HttpException) {
            when (e.code()) {
                408 -> Resource.Error(DataError.Network.REQUEST_TIMEOUT)
                429 -> Resource.Error(DataError.Network.TOO_MANY_REQUESTS)
                else -> Resource.Error(
                    DataError.Network.UNKNOWN,
                    message = "${e.code()} -- ${e.message()}"
                )
            }
        } catch (e: Exception) {
            Resource.Error(DataError.Network.UNKNOWN, message = e.localizedMessage)
        }
    }

    private fun <T> handleApiAnswer(responseCode: Int, successData: T): Resource<T, DataError> {
        return when (responseCode) {
            0 -> Resource.Success(successData)
            1 -> Resource.Error(DataError.Api.NO_RESULTS)
            2 -> Resource.Error(DataError.Api.INVALID_PARAMETER)
            3 -> Resource.Error(DataError.Api.TOKEN_NOT_FOUND)
            4 -> Resource.Error(DataError.Api.TOKEN_EMPTY)
            5 -> Resource.Error(DataError.Api.RATE_LIMIT)
            else -> Resource.Error(
                DataError.Api.UNKNOWN_CODE,
                message = responseCode.toString()
            )
        }
    }
}