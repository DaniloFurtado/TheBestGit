package desenv.danilo.thebestgit

import android.content.Intent
import desenv.danilo.thebestgit.util.ControlReloadFromBackGround

abstract class BaseReloadActivity: BaseActivity() {

    private  var controlReloadFromBackGround = ControlReloadFromBackGround(::onResumeReload)
    init {
        // adiciona o observer para o lifecycle
        addObserver(controlReloadFromBackGround)
    }

    // Notifica a ControlReloadFromBackGround sobre o controle de recarregar ou não
    // a view quando voltar do backcground
    fun Intent.hasCalledActivity(){
        controlReloadFromBackGround.setHasCalledActivity()
    }

    abstract fun onResumeReload()
}
