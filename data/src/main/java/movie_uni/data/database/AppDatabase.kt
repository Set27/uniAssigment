package movie_uni.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import movie_uni.data.account.local.model.FavoriteMovieEntity
import movie_uni.data.account.local.model.FavoriteTvShowEntity
import movie_uni.data.movie.local.MovieDao
import movie_uni.data.movie.local.model.MovieNowPlayingEntity
import movie_uni.data.movie.local.model.PopularMovieEntity
import movie_uni.data.tvshow.local.TvShowDao
import movie_uni.data.tvshow.local.model.PopularTvShowEntity
import movie_uni.data.tvshow.local.model.TopRatedTvShowEntity

@Database(
    entities = [
        PopularMovieEntity::class,
        MovieNowPlayingEntity::class,
        PopularTvShowEntity::class,
        TopRatedTvShowEntity::class,
        FavoriteMovieEntity::class,
        FavoriteTvShowEntity::class,
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): movie_uni.data.account.local.AccountDao
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
}