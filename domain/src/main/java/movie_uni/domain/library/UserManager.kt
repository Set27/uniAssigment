package movie_uni.domain.library

import movie_uni.domain.account.AccountDetails

interface UserManager {
    fun getUser(): AccountDetails?
    fun setCurrentUser(
        profilePicturePath: String?,
        id: Int,
        includeAdult: Boolean,
        name: String,
        userName: String
    ): Boolean

    fun removeCurrentUser(): Boolean
}