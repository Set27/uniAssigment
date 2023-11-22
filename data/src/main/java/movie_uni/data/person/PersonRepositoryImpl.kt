package movie_uni.data.person

import com.github.michaelbull.result.Result
import movie_uni.domain.Failure
import movie_uni.domain.person.PersonRepository
import movie_uni.domain.person.model.PersonDetails
import movie_uni.domain.person.model.PersonMovieCast
import movie_uni.domain.person.model.PersonTvCast
import javax.inject.Inject
import movie_uni.data.network.toResult
import movie_uni.data.util.getImageUrl

class PersonRepositoryImpl @Inject constructor(
    private val personApi: PersonApi
) : PersonRepository {

    override suspend fun getPersonMovieCasting(personId: Int): Result<List<PersonMovieCast>, Failure> {
        return personApi.getPersonMovieCasting(personId = personId).toResult(mapper = { response ->
            response.cast.map {
                PersonMovieCast(
                    character = it.character,
                    title = it.title,
                    id = it.id,
                    imageUrl = getImageUrl(it.posterPath)
                )
            }
        })
    }

    override suspend fun getPersonTvCasting(personId: Int): Result<List<PersonTvCast>, Failure> {
        return personApi.getPersonTvCasting(personId).toResult(mapper = { response ->
            response.cast.map {
                PersonTvCast(
                    character = it.character,
                    name = it.name,
                    id = it.id,
                    imageUrl = getImageUrl(it.posterPath)
                )
            }
        })
    }

    override suspend fun getPersonDetails(personId: Int): Result<PersonDetails, Failure> {
        return personApi.getPersonDetails(personId).toResult(mapper = {
            PersonDetails(
                name = it.name,
                biography = it.biography,
                imageUrl = getImageUrl(it.profilePath)
            )
        })
    }
}