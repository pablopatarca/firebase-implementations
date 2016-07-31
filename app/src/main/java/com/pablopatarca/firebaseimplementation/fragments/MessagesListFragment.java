package com.pablopatarca.firebaseimplementation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.pablopatarca.firebaseimplementation.R;
import com.pablopatarca.firebaseimplementation.adapters.MessagesAdapter;
import com.pablopatarca.firebaseimplementation.app.AppSettings;
import com.pablopatarca.firebaseimplementation.models.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * Created by Pablo on 30/7/16.
 */
public class MessagesListFragment extends BaseFragment {

    List<Message> mMessages;
    DatabaseReference myDBRef;

    public static MessagesListFragment newInstance() {

        Bundle args = new Bundle();

        MessagesListFragment fragment = new MessagesListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMessages = new ArrayList<>();
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myDBRef = database.getReference("messages");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMessages);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new MessagesAdapter(mMessages));

        final EditText msgEditText = (EditText) view.findViewById(R.id.textArea);
        ImageButton button = (ImageButton) view.findViewById(R.id.sendMessage);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg = msgEditText.getText().toString();
                if (msg.length() > 0){
                    msgEditText.setText("");
                    Message messageNew = new Message(AppSettings.getUser().userName, msg);
                    // Write a message to the database
                    myDBRef.push().setValue(messageNew);
                }
            }
        });

        // Read from the database
        myDBRef.orderByChild("date").limitToLast(50).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<Map<String, Message>> genericTypeIndicator =
                        new GenericTypeIndicator<Map<String, Message>>() {};

//                HashMap<String, Message> values = dataSnapshot.getValue(genericTypeIndicator);
                Map<String, Message> values = dataSnapshot.getValue(genericTypeIndicator);
                Log.d(TAG, "Value is: " + values);

                if (values != null) {
                    mMessages.clear();
                    mMessages.addAll(values.values());
                    Collections.sort(mMessages, new Comparator<Message>() {
                        @Override
                        public int compare(Message message, Message t1) {
                            return (int) (message.date - t1.date);
                        }
                    });
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
