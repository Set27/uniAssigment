package movie_uni.domain.movie

import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import movie_uni.domain.movie.model.MovieNowPlaying

class GetMoviesNowPlayingPagingFlowUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    fun execute(): Flow<PagingData<MovieNowPlaying>> =
        movieRepository.getMoviesNowPlayingPagingFlow()
}