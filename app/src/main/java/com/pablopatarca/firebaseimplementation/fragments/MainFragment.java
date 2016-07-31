package com.pablopatarca.firebaseimplementation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pablopatarca.firebaseimplementation.R;
import com.pablopatarca.firebaseimplementation.models.User;

/**
 * Created by Pablo on 30/7/16.
 */
public class MainFragment extends BaseFragment {

    DatabaseReference myDBRef;

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myDBRef = database.getReference("users");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText userName = (EditText) view.findViewById(R.id.userName);

        Button button = (Button) view.findViewById(R.id.sendData);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = userName.getText().toString();

                if (name.length() > 0){

                    User user = new User();
                    user.userName = name;

                    // Write a message to the database
                    myDBRef.setValue(user);
                }
            }
        });

        // Read from the database
        myDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User value = dataSnapshot.getValue(User.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
