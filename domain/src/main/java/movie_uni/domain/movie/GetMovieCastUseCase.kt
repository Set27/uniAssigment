package movie_uni.domain.movie

import com.mutkuensert.androidphase.Phase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import movie_uni.domain.movie.model.MovieCast
import movie_uni.domain.util.GetPhaseFlow

class GetMovieCastUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend fun execute(movieId: Int): Flow<Phase<List<MovieCast>>> {
        return GetPhaseFlow.execute {
            movieRepository.getMovieCast(movieId)
        }
    }
}