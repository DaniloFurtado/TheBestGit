package desenv.danilo.data.data

import com.google.gson.annotations.SerializedName

data class ApiResponse<T> (
    val data: T,
    @SerializedName("message")
    val messageErro: String? = null
)