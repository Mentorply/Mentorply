package com.example.mentorply;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mentorply.adapters.ChatAdapter;
import com.example.mentorply.models.Message;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChatActivity extends AppCompatActivity {

    static final String TAG = ChatActivity.class.getSimpleName();

    static final String USER_ID_KEY = "userId";
    static final String BODY_KEY = "body";
    static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;

    RecyclerView rvChat;
    ArrayList<Message> mMessages;
    ChatAdapter mAdapter;
    // Keep track of initial load to scroll to the bottom of the ListView
    boolean mFirstLoad;

    EditText etMessage;
    ImageButton btSend;
    ParseUser recipient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setupMessagePosting();
    }


    // Setup button event handler which posts the entered message to Parse
    void setupMessagePosting() {
        // Find the text field and button
        etMessage = (EditText) findViewById(R.id.etMessage);
        btSend = (ImageButton) findViewById(R.id.btSend);
        rvChat = (RecyclerView) findViewById(R.id.rvChat);
        recipient = Parcels.unwrap(getIntent().getParcelableExtra(ParseUser.class.getSimpleName()));
        mMessages = new ArrayList<>();
        mFirstLoad = true;
        final String userId = ParseUser.getCurrentUser().getObjectId();
        mAdapter = new ChatAdapter(ChatActivity.this, userId, mMessages);
        rvChat.setAdapter(mAdapter);

        // associate the LayoutManager with the RecylcerView
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
        linearLayoutManager.setReverseLayout(true);
        rvChat.setLayoutManager(linearLayoutManager);

        // When send button is clicked, create message object on Parse
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etMessage.getText().toString();
                Message message = new Message();
                message.setBody(data);
                message.setFromUser(ParseUser.getCurrentUser());
                message.setToUser(recipient);
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(ChatActivity.this, "Successfully created message on Parse",
                                Toast.LENGTH_SHORT).show();
                        refreshMessages();
                    }
                });
                etMessage.setText(null);
            }
        });
    }
    void refreshMessages() {
        // Construct query to execute
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        // Configure limit and sort order
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);

        // get the latest 50 messages, order will show up newest to oldest of this group
        query.orderByDescending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    mMessages.clear();
                    mMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    if (mFirstLoad) {
                        rvChat.scrollToPosition(0);
                        mFirstLoad = false;
                    }
                } else {
                    Log.e("message", "Error Loading Messages" + e);
                }
            }
        });
    }
    // Create a handler which can run code periodically
    static final long POLL_INTERVAL = TimeUnit.SECONDS.toMillis(3);
    Handler myHandler = new android.os.Handler();
    Runnable mRefreshMessagesRunnable = new Runnable() {
        @Override
        public void run() {
            refreshMessages();
            myHandler.postDelayed(this, POLL_INTERVAL);
        }
    };


    @Override
    protected void onResume() {
        super.onResume();

        // Only start checking for new messages when the app becomes active in foreground
        myHandler.postDelayed(mRefreshMessagesRunnable, POLL_INTERVAL);
    }

    @Override
    protected void onPause() {
        // Stop background task from refreshing messages, to avoid unnecessary traffic & battery drain
        myHandler.removeCallbacksAndMessages(null);
        super.onPause();
    }
}
