package movie_uni.domain.tvshow

import com.mutkuensert.androidphase.Phase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import movie_uni.domain.tvshow.model.TvShowCast
import movie_uni.domain.util.GetPhaseFlow

class GetTvShowCastUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {

    suspend fun execute(tvShowId: Int): Flow<Phase<List<TvShowCast>>> {
        return GetPhaseFlow.execute {
            tvShowRepository.getTvShowCast(tvShowId)
        }
    }
}