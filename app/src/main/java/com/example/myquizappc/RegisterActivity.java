package com.example.myquizappc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    //Step1: Declaration
    Button button;
    EditText etLogin;
    EditText etPassword;
    EditText etPasswords;
    EditText etName;
    TextView tvLogin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Step2: Recuperation Ids & initialisation

        etLogin=(EditText)findViewById(R.id.etLogin);
        etPassword=(EditText)findViewById(R.id.etPassword);
        etPasswords=(EditText) findViewById(R.id.etPasswords);
        etName=(EditText)findViewById(R.id.etName);
        button=(Button)findViewById(R.id.button);
        tvLogin= (TextView)findViewById(R.id.tvRegister);
        auth=FirebaseAuth.getInstance();

        //Step3 associatio de listeners

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Step 4: traitement

                String login=etLogin.getText().toString();
                String password=etPassword.getText().toString();
                String password1=etPasswords.getText().toString();

                if(TextUtils.isEmpty(login)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password1)){
                    Toast.makeText(getApplicationContext(),"Please confirm your password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()<6){
                    Toast.makeText(getApplicationContext(),"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(password1)){
                    Toast.makeText(getApplicationContext(),"Please enter correct password",Toast.LENGTH_SHORT).show();
                    return;
                }

                //creation User
                auth.createUserWithEmailAndPassword(login,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Registration Successful!  inscription réussi! التسجيل ناجح! 註冊成功  ",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"Registration Error!"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

        });

        tvLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

    }

}