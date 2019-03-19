package desenv.danilo.thebestgit.di

import dagger.Component
import desenv.danilo.thebestgit.UserListActivity
import desenv.danilo.thebestgit.UserDetailActivity
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun injectMain(activity: UserListActivity)

    fun injectDetail(activity: UserDetailActivity)
}