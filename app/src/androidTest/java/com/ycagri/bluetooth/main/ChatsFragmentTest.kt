package com.ycagri.bluetooth.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ycagri.bluetooth.R
import com.ycagri.bluetooth.database.model.BluetoothMessage
import com.ycagri.bluetooth.main.fragments.ChatsFragment
import com.ycagri.bluetooth.main.viewmodel.ChatsFragmentViewModel
import com.ycagri.bluetooth.util.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@RunWith(AndroidJUnit4::class)
class ChatsFragmentTest {

    @Rule
    @JvmField
    val executorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val countingAppExecutors = CountingAppExecutorsRule()

    private val viewModel: ChatsFragmentViewModel = mock(ChatsFragmentViewModel::class.java)

    private val chats = MutableLiveData<List<BluetoothMessage>>()

    @Before
    fun setup() {
        doNothing().`when`(viewModel).setSearchTerm(anyString())
        `when`(viewModel.chats).thenReturn(chats)
        launchFragmentInContainer() {
            ChatsFragment().apply {
                appExecutors = countingAppExecutors.appExecutors
                viewModelFactory = ViewModelUtil.createFor(viewModel)
            }
        }
    }

    @Test
    fun testLoaded() {
        val messages = ArrayList<BluetoothMessage>()
        messages.add(TestUtil.createMessage(1, "Hello", BluetoothMessage.TYPE_SENT))
        chats.postValue(messages)

        for (pos in messages.indices)
            Espresso.onView(RecyclerViewMatcher(R.id.rv_chats).atPosition(pos)).apply {
                check(ViewAssertions.matches(hasDescendant(withText(messages[pos].message))))
            }
    }
}