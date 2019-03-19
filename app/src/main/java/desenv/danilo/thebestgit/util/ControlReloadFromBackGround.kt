package desenv.danilo.thebestgit.util

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


class ControlReloadFromBackGround(private var onResumeReload: (() -> Unit)): LifecycleObserver {

    private var mustCallReload = false

    fun setHasCalledActivity(){
        mustCallReload = false
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        // Verifica se está voltando do brackground ou de uma chamad de outra activity
        if (mustCallReload) {
            onResumeReload.invoke()
            Log.e("ControlReloadFrom", "OnReloadBackGround Called")
        }
        mustCallReload = true
    }

}