package movie_uni.data.movie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.github.michaelbull.result.Result
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import movie_uni.data.movie.local.MovieDao
import movie_uni.data.movie.remote.mapper.MovieNowPlayingDtoMapper
import movie_uni.data.movie.remote.mapper.PopularMovieDtoMapper
import movie_uni.data.movie.remote.mediator.MoviesNowPlayingRemoteMediator
import movie_uni.data.movie.remote.mediator.PopularMoviesRemoteMediator
import movie_uni.data.network.toResult
import movie_uni.data.util.getImageUrl
import movie_uni.data.util.withDecimalDigits
import movie_uni.domain.Failure
import movie_uni.domain.movie.MovieRepository
import movie_uni.domain.movie.model.MovieCast
import movie_uni.domain.movie.model.MovieDetails
import movie_uni.domain.movie.model.MovieNowPlaying
import movie_uni.domain.movie.model.PopularMovie

@OptIn(ExperimentalPagingApi::class)
class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao,
    private val popularMovieDtoMapper: PopularMovieDtoMapper,
    private val movieNowPlayingDtoMapper: MovieNowPlayingDtoMapper,
) : MovieRepository {

    override fun getPopularMoviesPagingFlow(): Flow<PagingData<PopularMovie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = PopularMoviesRemoteMediator(
                getPopularMovies = movieApi::getPopularMovies,
                movieDao = movieDao,
                popularMovieDtoMapper = popularMovieDtoMapper
            ),
            pagingSourceFactory = { movieDao.getPopularMoviesPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map {
                PopularMovie(
                    imageUrl = getImageUrl(it.posterPath),
                    title = it.title,
                    id = it.id,
                    isFavorite = it.isFavorite,
                    voteAverage = it.voteAverage
                )
            }
        }
    }

    override fun getMoviesNowPlayingPagingFlow(): Flow<PagingData<MovieNowPlaying>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = MoviesNowPlayingRemoteMediator(
                getMoviesNowPlaying = movieApi::getMoviesNowPlaying,
                movieDao = movieDao,
                movieNowPlayingDtoMapper = movieNowPlayingDtoMapper
            ),
            pagingSourceFactory = { movieDao.getMoviesNowPlayingPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map {
                MovieNowPlaying(
                    imageUrl = getImageUrl(it.posterPath),
                    title = it.title,
                    id = it.id,
                    voteAverage = it.voteAverage,
                    isFavorite = it.isFavorite
                )
            }
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Result<MovieDetails, Failure> {
        return movieApi.getMovieDetails(movieId).toResult(mapper = {
            MovieDetails(
                imageUrl = getImageUrl(it.posterPath),
                title = it.title,
                voteAverage = it.voteAverage.withDecimalDigits(1),
                runtime = it.runtime,
                overview = it.overview
            )
        })
    }

    override suspend fun getMovieCast(movieId: Int): Result<List<MovieCast>, Failure> {
        return movieApi.getMovieCredits(movieId).toResult(mapper = { response ->
            response.cast.map {
                MovieCast(
                    id = it.id,
                    imageUrl = getImageUrl(it.profilePath),
                    name = it.name,
                    character = it.character
                )
            }
        })
    }

    override suspend fun addMovieToFavorites(movieId: Int) {
        movieDao.getPopularMovie(movieId)
            ?.copyWithPrimaryKey(isFavorite = true)
            ?.let {
                movieDao.update(it)
            }

        movieDao.getMovieNowPlaying(movieId)
            ?.copyWithPrimaryKey(isFavorite = true)
            ?.let {
                movieDao.update(it)
            }
    }

    override suspend fun removeMovieFromFavorites(movieId: Int) {
        movieDao.getPopularMovie(movieId)
            ?.copyWithPrimaryKey(isFavorite = false)
            ?.let {
                movieDao.update(it)
            }

        movieDao.getMovieNowPlaying(movieId)
            ?.copyWithPrimaryKey(isFavorite = false)
            ?.let {
                movieDao.update(it)
            }
    }
}