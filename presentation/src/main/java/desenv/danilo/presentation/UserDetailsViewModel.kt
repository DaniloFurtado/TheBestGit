package desenv.danilo.presentation

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import desenv.danilo.domain.interactor.ViewUserUseCase
import desenv.danilo.modelbind.UserBind
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor(
    private val viewUserUseCase: ViewUserUseCase
): BaseViewModel() {

    private val stateUser = MutableLiveData<ViewState<UserBind>>()

    fun getStateUser(): MutableLiveData<ViewState<UserBind>> {
        return stateUser
    }

    fun showUser(idUser: String){
        viewUserUseCase.executeUseCase(idUser,
            (this::showUserResult),
            (this::errorHandle),
            onSubscribe = (this::showProgress))
    }

    fun showProgress(){
        stateUser.value = ViewState(ViewState.Status.LOADING)
    }

    private fun showUserResult(user: UserBind){
        stateUser.value = ViewState(ViewState.Status.SUCCESS, data = user)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        viewUserUseCase.dispose()
    }

}