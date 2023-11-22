package movie_uni.domain.tvshow

import androidx.paging.PagingData
import com.github.michaelbull.result.Result
import movie_uni.domain.Failure
import kotlinx.coroutines.flow.Flow
import movie_uni.domain.tvshow.model.PopularTvShow
import movie_uni.domain.tvshow.model.TopRatedTvShow
import movie_uni.domain.tvshow.model.TvShowCast
import movie_uni.domain.tvshow.model.TvShowDetails

interface TvShowRepository {
    suspend fun getTvShowDetails(tvShowId: Int): Result<TvShowDetails, Failure>
    suspend fun getTvShowCast(tvShowId: Int): Result<List<TvShowCast>, Failure>
    fun getPopularTvShowsPagingFlow(): Flow<PagingData<PopularTvShow>>
    fun getTopRatedTvShowsPagingFlow(): Flow<PagingData<TopRatedTvShow>>
    suspend fun addTvShowToFavorites(tvShowId: Int)
    suspend fun removeTvShowFromFavorites(tvShowId: Int)
}