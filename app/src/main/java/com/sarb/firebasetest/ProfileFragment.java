package com.sarb.firebasetest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {

    TextView txt_email;
    EditText edt_name,edt_mob;
    Button btn_update,btn_logout,btn_delete;
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser curUser;

    String name,mobile;
    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt_email=view.findViewById(R.id.txt_emailProfile);
        edt_mob=view.findViewById(R.id.edt_mobProfile);
        edt_name=view.findViewById(R.id.edt_nameProfile);
        btn_logout=view.findViewById(R.id.btn_signoutProfile);
        btn_update=view.findViewById(R.id.btn_updateProfile);
        btn_delete=view.findViewById(R.id.btn_deleteProfile);
        btn_delete.setOnClickListener(delete);
        btn_logout.setOnClickListener(logout);
        btn_update.setOnClickListener(update);

        loadData();
    }


    View.OnClickListener delete=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            curUser=auth.getCurrentUser();

            db.collection("Users").document(curUser.getUid()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        curUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getActivity().getApplicationContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getActivity().getApplicationContext(),LoginActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }
            });
        }
    };


    private void loadData() {

        curUser=auth.getCurrentUser();
        db.collection("Users").document(curUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                txt_email.setText(documentSnapshot.get("Email").toString());
                edt_name.setText(documentSnapshot.get("Name").toString());
                edt_mob.setText(documentSnapshot.get("Mobile No").toString());
            }
        });

    }


    View.OnClickListener logout=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            auth.signOut();
            Intent intent=new Intent(getActivity().getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }
    };


    View.OnClickListener update=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            name=edt_name.getText().toString().trim();
            mobile=edt_mob.getText().toString().trim();

            Map<String,Object> user=new HashMap<>();
            user.put("Name",name);
            user.put("Mobile No",mobile);
            curUser=auth.getCurrentUser();
            db.collection("Users").document(curUser.getUid()).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getActivity().getApplicationContext(), "Data updated", Toast.LENGTH_SHORT).show();
                }
            });

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}