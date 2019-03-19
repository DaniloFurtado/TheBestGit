package desenv.danilo.modelbind

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable


open class UserBind(
    private var _id: String,
    private var _login: String,
    private var _avatarUrl: String,
    private var _name: String? = "",
    private var _blog: String? = "",
    private var _location: String? = null,
    private var _email: String? = null,
    private var _repos: String? = "",
    private var _followers: String? = "",
    private var _following: String? = "",
    private var _createdAt: String? = "",
    private var _updatedAt: String? = "",
    private var _type: String? = "",
    private var _company: String? = ""

) : BaseObservable(), Parcelable {
    @get:Bindable
    var id: String
        get() = _id
        set(value) {
            _id = value

            notifyPropertyChanged(BR.id)
        }

    @get:Bindable
    var login: String
        get() = _login
        set(value) {
            _login = value
            notifyPropertyChanged(BR.login)
        }

    @get:Bindable
    var avatarUrl: String
        get() = _avatarUrl
        set(value) {
            _avatarUrl = value
            notifyPropertyChanged(BR.avatarUrl)
        }

    @get:Bindable
    var name: String?
        get() = _name
        set(value) {
            _name = value
            notifyPropertyChanged(BR.name)
        }

    @get:Bindable
    var blog: String?
        get() = _blog
        set(value) {
            _blog = value
            notifyPropertyChanged(BR.blog)
        }

    @get:Bindable
    var location: String?
        get() = _location
        set(value) {
            _location = value
            notifyPropertyChanged(BR.location)
        }

    @get:Bindable
    var email: String?
        get() = _email
        set(value) {
            _email = value
            notifyPropertyChanged(BR.email)
        }

    @get:Bindable
    var repos: String?
        get() = _repos
        set(value) {
            _repos = value
            notifyPropertyChanged(BR.repos)
        }

    @get:Bindable
    var followers: String?
        get() = _followers
        set(value) {
            _followers = value
            notifyPropertyChanged(BR.followers)
        }

    @get:Bindable
    var following: String?
        get() = _following
        set(value) {
            _following = value
            notifyPropertyChanged(BR.following)
        }

    @get:Bindable
    var createdAt: String?
        get() = _createdAt
        set(value) {
            _createdAt = value
            notifyPropertyChanged(BR.createdAt)
        }

    @get:Bindable
    var updatedAt: String?
        get() = _updatedAt
        set(value) {
            _updatedAt = value
            notifyPropertyChanged(BR.updatedAt)
        }

    @get:Bindable
    var type: String?
        get() = _type
        set(value) {
            _type = value
            notifyPropertyChanged(BR.type)
        }

    @get:Bindable
    var company: String?
        get() = _company
        set(value) {
            _company = value
            notifyPropertyChanged(BR.company)
        }

    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(_id)
        writeString(_login)
        writeString(_avatarUrl)
        writeString(_name)
        writeString(_blog)
        writeString(_location)
        writeString(_email)
        writeString(_repos)
        writeString(_followers)
        writeString(_following)
        writeString(_createdAt)
        writeString(_updatedAt)
        writeString(_type)
        writeString(_company)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UserBind> = object : Parcelable.Creator<UserBind> {
            override fun createFromParcel(source: Parcel): UserBind = UserBind(source)
            override fun newArray(size: Int): Array<UserBind?> = arrayOfNulls(size)
        }
    }
}