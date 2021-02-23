package com.ycagri.bluetooth.di

import android.app.Application
import android.bluetooth.BluetoothAdapter
import com.ycagri.bluetooth.di.datasource.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ActivityBindingModule::class,
        FragmentBindingModule::class,
        ApplicationModule::class,
        AndroidSupportInjectionModule::class,
        RepositoryModule::class,
        ServiceBindingModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun bluetoothAdapter(adapter: BluetoothAdapter): Builder

        fun build(): AppComponent
    }
}