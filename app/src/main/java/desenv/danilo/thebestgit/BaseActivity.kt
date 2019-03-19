package desenv.danilo.thebestgit

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import com.google.android.material.snackbar.Snackbar
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import desenv.danilo.presentation.ViewErrorState
import desenv.danilo.thebestgit.di.AppModule
import desenv.danilo.thebestgit.di.DaggerAppComponent
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.design.longSnackbar


abstract class BaseActivity: AppCompatActivity() {

    protected val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(this)}
    private var mSnackBar: Snackbar? = null

    private val daggerInjet = DaggerAppComponent.builder()
        .appModule(AppModule())
        .build()!!

    init {
        inject()
    }

    private fun inject() {
        when(this){
            is UserDetailActivity -> daggerInjet.injectDetail(this)
            is UserListActivity -> daggerInjet.injectMain(this)
        }
    }


    protected fun avaliableTransition(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    fun initToolbarBack(toolbar: androidx.appcompat.widget.Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null)
            when(item.itemId){
                android.R.id.home -> onBackPressed()

            }
        return super.onOptionsItemSelected(item)
    }

    fun addObserver(cycleObserver: LifecycleObserver){
        lifecycle.addObserver(cycleObserver)
    }


    protected fun Activity.showSnackError(view: View, it: ViewErrorState, methodCall:  (()-> Unit)? = null) {
        mSnackBar = if (!it.retry){
            view.longSnackbar(it.idMessage)
        }else {
            view.indefiniteSnackbar(it.idMessage).setActionTextColor(Color.RED)
                .setAction(getString(desenv.danilo.thebestgit.R.string.retry)) {
                    methodCall?.invoke()
                }
        }
    }

    fun hideSnkack(){
        mSnackBar?.dismiss()
    }
}
