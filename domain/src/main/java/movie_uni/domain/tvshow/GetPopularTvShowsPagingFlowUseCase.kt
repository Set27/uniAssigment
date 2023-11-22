package movie_uni.domain.tvshow

import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import movie_uni.domain.tvshow.model.PopularTvShow

class GetPopularTvShowsPagingFlowUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {

    fun execute(): Flow<PagingData<PopularTvShow>> =
        tvShowRepository.getPopularTvShowsPagingFlow()
}