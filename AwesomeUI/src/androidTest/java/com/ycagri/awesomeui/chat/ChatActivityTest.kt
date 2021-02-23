package com.ycagri.awesomeui.chat

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ycagri.awesomeui.chat.mvvm.ChatViewModel
import com.ycagri.awesomeui.example.BasicChatActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.*
import kotlin.collections.ArrayList

@RunWith(AndroidJUnit4::class)
class ChatActivityTest {

    @get:Rule var rule = activityScenarioRule<BasicChatActivity>()

    @Mock
    private lateinit var viewModel: ChatViewModel<BasicChatActivity.TestMessage>

    private val messagesLiveData = MutableLiveData<List<BasicChatActivity.TestMessage>>()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`(viewModel.getChatMessages()).thenReturn(messagesLiveData)
    }

    @Test
    fun test() {
        rule.scenario.onActivity {
            it.adapter = BasicChatActivity.TestAdapter(ArrayList())
            it.layoutManager = LinearLayoutManager(
                InstrumentationRegistry.getInstrumentation().context,
                LinearLayoutManager.VERTICAL,
                false
            )
            it.viewModel = viewModel
        }

        rule.scenario.moveToState(Lifecycle.State.CREATED)
        publishMessages()
    }

    private fun publishMessages() {
        val liveData = ArrayList<BasicChatActivity.TestMessage>()
        liveData.apply {
            val now = Date()
            this.add(BasicChatActivity.TestMessage("Hi", now.time, true))
            this.add(BasicChatActivity.TestMessage("Hello", now.time + 60 * 1000, false))
            this.add(BasicChatActivity.TestMessage("What's up?", now.time + 2 * 60 * 1000, true))
            this.add(BasicChatActivity.TestMessage("Not much!", now.time + 3 * 60 * 1000, false))
        }
    }

}