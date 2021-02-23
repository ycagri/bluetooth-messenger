package com.ycagri.bluetooth.di.datasource

import android.content.Context
import androidx.room.Room
import com.ycagri.bluetooth.database.MessageDatabase
import com.ycagri.bluetooth.datasource.MessageDataConnection
import com.ycagri.bluetooth.datasource.MessageDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideMessageDataSource(dataConnection: MessageDataConnection): MessageDataSource

    @Module
    companion object {

        @Singleton
        @Provides
        @JvmStatic
        fun provideMessageDatabase(context: Context) =
            Room.databaseBuilder(context, MessageDatabase::class.java, "db_messages").build()
    }
}