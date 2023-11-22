package movie_uni.domain.account

import javax.inject.Inject
import movie_uni.domain.library.UserManager

class GetAccountDetailsUseCase @Inject constructor(private val userManager: UserManager) {

    fun execute(): AccountDetails? = userManager.getUser()
}