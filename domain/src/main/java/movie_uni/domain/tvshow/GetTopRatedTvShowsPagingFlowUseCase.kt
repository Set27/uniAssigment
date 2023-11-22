package movie_uni.domain.tvshow

import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import movie_uni.domain.tvshow.model.TopRatedTvShow

class GetTopRatedTvShowsPagingFlowUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {

    fun execute(): Flow<PagingData<TopRatedTvShow>> =
        tvShowRepository.getTopRatedTvShowsPagingFlow()
}