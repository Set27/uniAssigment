package movie_uni.data.multisearch

import movie_uni.data.multisearch.model.MultiSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MultiSearchApi {

    @GET("search/multi")
    suspend fun multiSearch(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): Response<MultiSearchResponse>
}