package movie_uni.data.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GenericMoshi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MultiSearchResultMoshi