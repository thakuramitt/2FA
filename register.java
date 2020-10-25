package com.example.twofactorauth;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class register extends AppCompatActivity {
    EditText name,mobile,email,password,repassword;
    Button signup ;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name=findViewById(R.id.name);
        mobile=findViewById(R.id.mobile);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        repassword=findViewById(R.id.repassword);
        signup=findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String firstNameStr =name.getText().toString().trim();
                final String mobileNumberStr = mobile.getText().toString().trim();
                final String emailIdStr = email.getText().toString().trim();
                final String pass = password.getText().toString().trim();
                final String rePass = repassword.getText().toString().trim();
                if (TextUtils.isEmpty(emailIdStr)) {
                    Toast.makeText(register.this, "please enter email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(register.this, "please enter password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(rePass)) {
                    Toast.makeText(register.this, "please reenter password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pass.length() < 8) {
                    Toast.makeText(register.this, "enter atleast 8 characters", Toast.LENGTH_LONG).show();
                    return;

                }
                if (pass.equals(rePass)) {
                    mAuth.createUserWithEmailAndPassword(emailIdStr, pass)
                            .addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(register.this, "Successfully Registered, \n Updating data, Please wait...", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(register.this,Login1.class));
                                    } else {
                                        String msg = Objects.requireNonNull(task.getException()).getMessage();
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(register.this, msg, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
        });

    }


}
