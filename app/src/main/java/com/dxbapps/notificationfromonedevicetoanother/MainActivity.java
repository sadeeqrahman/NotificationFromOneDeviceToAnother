package com.dxbapps.notificationfromonedevicetoanother;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private EditText title, message, token;
    private Button sendall, single;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        FirebaseMessaging.getInstance().subscribeToTopic("all");

        title = findViewById(R.id.title);
        message = findViewById(R.id.message);
        token = findViewById(R.id.token);

        sendall = findViewById(R.id.send_all_devices);
        single = findViewById(R.id.send_single_devices);

        sendall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all",
                        title.getText().toString(), message.getText().toString(),
                        getApplicationContext(), MainActivity.this);
                notificationsSender.SendNotifications();
            }
        });



        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {

                            return;
                        }

                        // Get new FCM registration token
                        String user_token = task.getResult();
                        token.setText(user_token);
                        // Log and toast

                    }
                });




        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(token.getText().toString(),
                        title.getText().toString(), message.getText().toString(),
                        getApplicationContext(), MainActivity.this);
                notificationsSender.SendNotifications();
            }
        });

    }
}