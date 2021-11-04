package com.flowershop.shopping;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private Button CreateAccountButton;
    private EditText InputUsername,InputName, InputPhoneNumber, InputPassword;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputUsername = (EditText) findViewById(R.id.register_username_input);
        InputName = (EditText) findViewById(R.id.register_fullname);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        loadingBar = new ProgressDialog(this);
        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }
    private void CreateAccount(){
        String username = InputUsername.getText().toString();
        String password = InputPassword.getText().toString();
        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();

        if (TextUtils.isEmpty(username))
        {
            Toast.makeText(this, "Please write your Username...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your Password...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please write your Name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your Phone Number...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatephoneNumber(username, password, name, phone);
        }

    }

    private void ValidatephoneNumber(final String username, final String password, final String name, final String phone) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(username).exists())){
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("username", username);
                    userdataMap.put("password", password);
                    userdataMap.put("name", name);
                    userdataMap.put("phone", phone);
                    RootRef.child("Users").child(username).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful())
                            {
                                Toast.makeText(RegisterActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(RegisterActivity.this, com.flowershop.shopping.LoginActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                }
                else {
                    Toast.makeText(RegisterActivity.this, "This " + username + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try again using another username.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, com.flowershop.shopping.MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
