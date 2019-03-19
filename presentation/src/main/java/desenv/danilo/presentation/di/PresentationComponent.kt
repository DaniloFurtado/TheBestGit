package desenv.danilo.presentation.di

import dagger.Component
import desenv.danilo.presentation.UserDetailsViewModel
import desenv.danilo.presentation.UserListViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [PresentationModule::class])
interface PresentationComponent {

    fun injectUserListViewModel(): UserListViewModel

    fun injectUserDetailsViewModel(): UserDetailsViewModel
}