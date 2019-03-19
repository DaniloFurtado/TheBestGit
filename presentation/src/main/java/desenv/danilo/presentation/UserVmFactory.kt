package desenv.danilo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import desenv.danilo.presentation.di.DaggerPresentationComponent


class UserVmFactory: ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when {
            modelClass.isAssignableFrom(UserListViewModel::class.java)  -> {
                DaggerPresentationComponent.builder().build().injectUserListViewModel() as T
            }
            modelClass.isAssignableFrom(UserDetailsViewModel::class.java) ->{
                DaggerPresentationComponent.builder().build().injectUserDetailsViewModel() as T
            }
            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}