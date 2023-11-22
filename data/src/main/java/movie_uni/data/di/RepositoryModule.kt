package movie_uni.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import movie_uni.data.account.AccountRepositoryImpl
import movie_uni.data.authentication.AuthenticationRepositoryImpl
import movie_uni.data.movie.MovieRepositoryImpl
import movie_uni.data.multisearch.MultiSearchRepositoryImpl
import movie_uni.data.person.PersonRepositoryImpl
import movie_uni.data.tvshow.TvShowRepositoryImpl
import movie_uni.domain.account.AccountRepository
import movie_uni.domain.login.AuthenticationRepository
import movie_uni.domain.movie.MovieRepository
import movie_uni.domain.multisearch.MultiSearchRepository
import movie_uni.domain.person.PersonRepository
import movie_uni.domain.tvshow.TvShowRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindAuthenticationRepository(authenticationRepositoryImpl: AuthenticationRepositoryImpl)
            : AuthenticationRepository

    @Singleton
    @Binds
    fun bindAccountRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository

    @Singleton
    @Binds
    fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Singleton
    @Binds
    fun bindMultiSearchRepository(multiSearchRepositoryImpl: MultiSearchRepositoryImpl)
            : MultiSearchRepository

    @Singleton
    @Binds
    fun bindPersonRepository(personRepositoryImpl: PersonRepositoryImpl): PersonRepository

    @Singleton
    @Binds
    fun bindTvShowRepository(tvShowRepositoryImpl: TvShowRepositoryImpl): TvShowRepository
}