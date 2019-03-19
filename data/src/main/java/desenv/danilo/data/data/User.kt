package desenv.danilo.data.data

import com.google.gson.annotations.SerializedName

data class User(
    val id: String,
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val name: String? = "" ,
    val blog: String? = "",
    val location: String? = "",
    val email: String? = "",
    @SerializedName("public_repos")
    val repos: String? ="",
    val followers: String? ="",
    val following: String? ="",
    @SerializedName("created_at")
    val createdAt: String? ="",
    @SerializedName("updated_at")
    val updatedAt: String? = "",
    val type: String? = "",
    val company: String? = ""
)