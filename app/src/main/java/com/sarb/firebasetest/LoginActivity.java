package com.sarb.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    TextView TX,txt_forget;
    EditText edt_email,edt_Pass;
    Button login;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txt_forget= findViewById(R.id.txt_forgetPass);
        txt_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b=new Intent(getApplicationContext(),ForgetActivity.class);
                startActivity(b);
            }
        });
        auth = FirebaseAuth.getInstance();
        TX= findViewById(R.id.txt_loginNew);
        TX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(a);
            }
        });
        edt_email= findViewById(R.id.edt_loginEmail);
        edt_Pass= findViewById(R.id.edt_loginPass);
        login= findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = edt_email.getText().toString().trim();
                String Pass = edt_Pass.getText().toString().trim();
                if (TextUtils.isEmpty(Email)) {
                    edt_email.requestFocus();
                    edt_email.setError("Email can't be empty");
                    return;
                } else if (TextUtils.isEmpty(Pass)) {
                    edt_Pass.requestFocus();
                    edt_Pass.setError("Can't be empty");
                    return;
                } else if (Pass.length() < 6) {
                    edt_Pass.getText().clear();
                    edt_Pass.requestFocus();
                    edt_Pass.setError("Can't be less than 6");
                    return;
                } else {
                    auth.signInWithEmailAndPassword(Email, Pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "User loged in", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(intent);
                                    } else {

                                        try {
                                            throw task.getException();
                                        }catch (FirebaseAuthInvalidUserException e){
                                            edt_email.getText().clear();
                                            edt_Pass.getText().clear();
                                            edt_email.setError("User not exist");
                                            edt_email.requestFocus();
                                        }
                                        catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                }
                            });
                }
            }
            });




}
}
