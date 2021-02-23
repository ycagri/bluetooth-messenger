package com.ycagri.bluetooth.di.fragment

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ycagri.bluetooth.di.FragmentScoped
import dagger.Module
import dagger.Provides

@Module
abstract class ChatFragmentModule {

    @Module
    companion object {

        @FragmentScoped
        @JvmStatic
        @Provides
        fun provideLayoutManager(context: Context): RecyclerView.LayoutManager {
            return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        @FragmentScoped
        @JvmStatic
        @Provides
        fun provideItemDecoration(context: Context): RecyclerView.ItemDecoration {
            return DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        }
    }
}