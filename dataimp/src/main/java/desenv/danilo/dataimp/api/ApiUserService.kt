package desenv.danilo.dataimp.api

import desenv.danilo.data.data.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiUserService {


    @GET("users")
    fun getAllUser(): Single<List<User>>


    @GET("user/{id}")
    fun getUser(@Path("id") id: String): Single<User>

}