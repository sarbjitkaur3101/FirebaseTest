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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText MName,MEmail,MPassword,Mmobile,CPassword;
    Button MRegister;
    TextView AC;
    FirebaseAuth auth;
    FirebaseUser curUser;
    FirebaseFirestore db;
    String email,name,mobile,pass,cpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CPassword= findViewById(R.id.edt_passRegister);
        MName = findViewById(R.id.Name);
        MEmail= findViewById(R.id.Email);
        MPassword= findViewById(R.id.Password);
        Mmobile= findViewById(R.id.Mobile);
        MRegister= findViewById(R.id.button);
        auth=FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        AC= findViewById(R.id.Ac);
        AC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });

        MRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cpassword=CPassword.getText().toString().trim();
             mobile= Mmobile.getText().toString().trim();
                 email= MEmail.getText().toString().trim();
                 pass= MPassword.getText().toString().trim();
                name=MName.getText().toString().trim();

                 if (TextUtils.isEmpty(name)) {
                    MName.requestFocus();
                    MName.setError("Name is required");
                    return;
                }
                else if(TextUtils.isEmpty(email)){
                    MEmail.requestFocus();
                    MEmail.setError("Email is required");
                    return;
                }
                else if(TextUtils.isEmpty(pass)){
                    MPassword.requestFocus();
                    MPassword.setError("Password is required");

                    return;

                }
                 else if (pass.length()<6){
                     MPassword.getText().clear();
                     MPassword.requestFocus();
                     MPassword.setError("password must have 6 characters");
                     return;
                 }
                else if(TextUtils.isEmpty(cpassword)){

                    CPassword.requestFocus();
                    CPassword.setError("cant be empty");

                    return;
                 }
                else if(!pass.equals(cpassword)){
                    CPassword.getText().clear();
                    MPassword.getText().clear();
                    CPassword.setError("password doesn't match");
                    MPassword.requestFocus();
                    return;

                }


               else if(TextUtils.isEmpty(mobile)){
                    Mmobile.requestFocus();
                    Mmobile.setError("mobile is required");
                    return;
                }
                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            saveUserData();

                            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(intent);
                        }
                        else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthUserCollisionException e) {

                                MEmail.getText().clear();
                                MPassword.getText().clear();
                                MEmail.requestFocus();
                                MEmail.setError("Email already exist");

                            }catch (Exception e){
                                Log.e("Register", e.getMessage());
                            }

                            Toast.makeText(getApplicationContext(),"user not created",Toast.LENGTH_LONG).show();
                            Log.d("Register","not ="+task.getException());
                        }
                    }
                });

            }
        });
    }

    private void saveUserData() {
        Map<String, String> user = new HashMap<>();
        user.put("Email",email);
        user.put("Name",name);
        user.put("Mobile No",mobile);

        curUser=auth.getCurrentUser();
        db.collection("Users").document(curUser.getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"user created",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        curUser=auth.getCurrentUser();
        if (curUser!=null){
            Toast.makeText(getApplicationContext(),"User Already login",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
        }
    }
}