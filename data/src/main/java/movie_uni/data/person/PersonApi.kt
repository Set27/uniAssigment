package movie_uni.data.person

import movie_uni.data.person.model.PersonDetailsResponse
import movie_uni.data.person.model.PersonMovieCreditsResponse
import movie_uni.data.person.model.PersonTvCreditsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PersonApi {

    @GET("person/{person_id}")
    suspend fun getPersonDetails(
        @Path("person_id") personId: Int
    ): Response<PersonDetailsResponse>

    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonMovieCasting(
        @Path("person_id") personId: Int
    ): Response<PersonMovieCreditsResponse>

    @GET("person/{person_id}/tv_credits")
    suspend fun getPersonTvCasting(
        @Path("person_id") personId: Int
    ): Response<PersonTvCreditsResponse>
}