package com.pablopatarca.firebaseimplementation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pablopatarca.firebaseimplementation.R;
import com.pablopatarca.firebaseimplementation.app.AppSettings;
import com.pablopatarca.firebaseimplementation.models.User;

/**
 * Created by Pablo on 30/7/16.
 */
public class OnboardingActivity extends FragmentActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AppSettings.getUser() != null){
            startActivity(new Intent(getBaseContext(), MainActivity.class));
            finish();
        }

        setContentView(R.layout.login_layout);

        final EditText userET = (EditText) findViewById(R.id.userName);
        Button enterButton = (Button) findViewById(R.id.enter);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = userET.getText().toString();
                if (userName.length() > 0){
                    User user = new User();
                    user.userName = userName;
                    AppSettings.setUserData(user);

                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                }
            }
        });

    }
}
