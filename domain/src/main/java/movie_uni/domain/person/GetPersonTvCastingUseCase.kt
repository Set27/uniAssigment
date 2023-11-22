package movie_uni.domain.person

import com.mutkuensert.androidphase.Phase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import movie_uni.domain.person.model.PersonTvCast
import movie_uni.domain.util.GetPhaseFlow

class GetPersonTvCastingUseCase @Inject constructor(
    private val personRepository: PersonRepository
) {

    suspend fun execute(personId: Int): Flow<Phase<List<PersonTvCast>>> {
        return GetPhaseFlow.execute {
            personRepository.getPersonTvCasting(personId)
        }
    }
}