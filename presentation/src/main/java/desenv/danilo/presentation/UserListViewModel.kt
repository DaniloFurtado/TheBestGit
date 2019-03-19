package desenv.danilo.presentation

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import desenv.danilo.domain.interactor.ListUserUseCase
import desenv.danilo.modelbind.UserBind
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val listUserUseCase: ListUserUseCase
): BaseViewModel() {
    private val stateListUser: MutableLiveData<ViewState<List<UserBind>>> = MutableLiveData()

    fun getStateListUser(): MutableLiveData<ViewState<List<UserBind>>> {
        return stateListUser
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(){
        listUser()
    }

    fun listUser(){
        listUserUseCase.executeUseCase(null,
            (this::listUserResult),
            (this::errorHandle),
            onSubscribe = (this::showProgress))
    }

    fun showProgress(){
        stateListUser.postValue(ViewState(ViewState.Status.LOADING))
    }

    private fun listUserResult(listUsers: List<UserBind>){
        stateListUser.postValue(ViewState(ViewState.Status.SUCCESS, data = listUsers))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        listUserUseCase.dispose()
    }
}