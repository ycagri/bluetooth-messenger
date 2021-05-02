package com.ycagri.bluetooth.main

import android.app.Activity
import android.app.Instrumentation
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ycagri.bluetooth.R
import com.ycagri.bluetooth.chat.BluetoothChatActivity
import com.ycagri.bluetooth.database.model.BluetoothMessage
import com.ycagri.bluetooth.main.fragments.ChatsFragment
import com.ycagri.bluetooth.main.viewmodel.ChatsFragmentViewModel
import com.ycagri.bluetooth.util.CountingAppExecutorsRule
import com.ycagri.bluetooth.util.RecyclerViewMatcher
import com.ycagri.bluetooth.util.TestUtil
import com.ycagri.bluetooth.util.ViewModelUtil
import org.hamcrest.Matchers.allOf
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
        `when`(viewModel.chats).thenReturn(chats)
        launchFragmentInContainer(null, R.style.Theme_AppCompat) {
            ChatsFragment().apply {
                appExecutors = countingAppExecutors.appExecutors
                viewModelFactory = ViewModelUtil.createFor(viewModel)
            }
        }
    }

    @Test
    fun testLoaded() {
        val messages = ArrayList<BluetoothMessage>()
        messages.addAll(TestUtil.createMessages(10, "Test Message"))
        chats.postValue(messages)

        for (i in 0 until 10)
            onView(RecyclerViewMatcher(R.id.rv_chats).atPosition(i, R.id.tv_message))
                .apply {
                    check(ViewAssertions.matches(withText("Test Message ${i + 1}")))
                }
    }

    @Test
    fun testSearch() {
        onView(withId(R.id.et_chat_search)).perform(typeText("search term"), pressImeActionButton())
        verify(viewModel).setSearchTerm("search term")
    }

    @Test
    fun testConversationClick() {
        Intents.init()

        val messages = ArrayList<BluetoothMessage>()
        messages.add(
            TestUtil.createMessage(
                id = 1L,
                message = "Test Message",
                BluetoothMessage.TYPE_SENT
            )
        )
        chats.postValue(messages)

        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, null);
        intending(toPackage("com.ycagri.bluetooth")).respondWith(result)
        onView(withId(R.id.rv_chats))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

        intended(
            allOf(
                hasComponent(BluetoothChatActivity::class.qualifiedName),
                hasExtra("device_address", "sender_address")
            )
        )

        Intents.release()
    }
}