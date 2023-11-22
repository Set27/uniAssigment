package movie_uni.domain.multisearch

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import movie_uni.domain.multisearch.model.SearchResult

interface MultiSearchRepository {
    suspend fun getSearchPagingFlow(query: String): Flow<PagingData<SearchResult>>
}