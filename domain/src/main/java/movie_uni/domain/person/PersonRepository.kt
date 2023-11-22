package movie_uni.domain.person

import com.github.michaelbull.result.Result
import movie_uni.domain.Failure
import movie_uni.domain.person.model.PersonDetails
import movie_uni.domain.person.model.PersonMovieCast
import movie_uni.domain.person.model.PersonTvCast

interface PersonRepository {
    suspend fun getPersonMovieCasting(personId: Int): Result<List<PersonMovieCast>, Failure>
    suspend fun getPersonTvCasting(personId: Int): Result<List<PersonTvCast>, Failure>
    suspend fun getPersonDetails(personId: Int): Result<PersonDetails, Failure>
}