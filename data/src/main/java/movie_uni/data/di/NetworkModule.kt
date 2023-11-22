package movie_uni.data.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import movie_uni.data.account.remote.AccountApi
import movie_uni.data.authentication.AuthenticationApi
import movie_uni.data.movie.MovieApi
import movie_uni.data.multisearch.MultiSearchApi
import movie_uni.data.multisearch.model.MediaType
import movie_uni.data.multisearch.model.MovieResultItemDto
import movie_uni.data.multisearch.model.MultiSearchResultDto
import movie_uni.data.multisearch.model.PersonResulItemDto
import movie_uni.data.multisearch.model.TvResultItemDto
import movie_uni.data.network.AccountIdInterceptor
import movie_uni.data.network.AuthenticationInterceptor
import movie_uni.data.person.PersonApi
import movie_uni.data.tvshow.TvShowsApi
import movie_uni.data.util.ApiConstants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @GenericMoshi
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @MultiSearchResultMoshi
    @Provides
    fun provideMoshiWithMultiSearchResultFactory(): Moshi {
        val multiSearchResultFactory =
            PolymorphicJsonAdapterFactory.of(MultiSearchResultDto::class.java, "media_type")
                .withSubtype(MovieResultItemDto::class.java, MediaType.MOVIE)
                .withSubtype(TvResultItemDto::class.java, MediaType.TV)
                .withSubtype(PersonResulItemDto::class.java, MediaType.PERSON)

        return Moshi
            .Builder()
            .add(multiSearchResultFactory)
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideClient(accountIdInterceptor: AccountIdInterceptor): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(AuthenticationInterceptor())
            .addInterceptor(accountIdInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(@GenericMoshi moshi: Moshi, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideTvShowsApi(retrofit: Retrofit): TvShowsApi {
        return retrofit.create(TvShowsApi::class.java)
    }

    @Singleton
    @Provides
    fun providePersonApi(retrofit: Retrofit): PersonApi {
        return retrofit.create(PersonApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthenticationApi(retrofit: Retrofit): AuthenticationApi {
        return retrofit.create(AuthenticationApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAccountApi(retrofit: Retrofit): AccountApi {
        return retrofit.create(AccountApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMultiSearchApi(
        @MultiSearchResultMoshi moshi: Moshi,
        client: OkHttpClient
    ): MultiSearchApi {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(MultiSearchApi::class.java)
    }
}