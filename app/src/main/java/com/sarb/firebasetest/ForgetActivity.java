package com.sarb.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetActivity extends AppCompatActivity {

    EditText edt_email;
    TextView txt_Login;
    Button btn_send;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        txt_Login= findViewById(R.id.txt_forgetLogin);
        txt_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent d= new Intent(getApplicationContext(),LoginActivity.class);

                        startActivity(d);
            }
        });
        edt_email= findViewById(R.id.edt_forget);
        auth=FirebaseAuth.getInstance();
        btn_send = findViewById(R.id.btn_forgetpassword);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   String email=edt_email.getText().toString().trim();
                   if(TextUtils.isEmpty(email)){
                       edt_email.setError("Email is empty");
                    }else {
                       auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if (task.isSuccessful()) {
                                   Toast.makeText(getApplicationContext(), "password is sent to your email", Toast.LENGTH_LONG).show();
                                    Intent d= new Intent(getApplicationContext(),LoginActivity.class);
                                    startActivity(d);
                               }
                           }
                       });
                   }

            }
        });
    }
    }