package movie_uni.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import movie_uni.data.library.session.SessionManagerImpl
import movie_uni.data.library.user.UserManagerImpl
import movie_uni.domain.library.SessionManager
import movie_uni.domain.library.UserManager

@Module
@InstallIn(SingletonComponent::class)
interface LibraryModule {

    @Binds
    @Singleton
    fun provideSessionManager(sessionManagerImpl: SessionManagerImpl): SessionManager

    @Binds
    @Singleton
    fun provideUserManager(userManagerImpl: UserManagerImpl): UserManager
}