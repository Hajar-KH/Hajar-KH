package com.example.myquizappc;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    /*step1: declaration*/
    EditText etLogin,etPassword;
    TextView tvRegister;
    FirebaseAuth auth;
    Button button;

    public static ArrayList<Question> questionsList;
    //declaration d'une reference de base de donnée firebase
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*Step2: recuperation des Ids & initialisation*/

        questionsList=new ArrayList<>();
        //Obtenir une référence de base de données
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Question");

        etLogin=(EditText) findViewById(R.id.etLogin);
        etPassword=(EditText) findViewById(R.id.etPassword);
        tvRegister= (TextView)findViewById(R.id.tvRegister);
        button=(Button)findViewById(R.id.button);

        auth=FirebaseAuth.getInstance();

        /*Step3: associatio de listeners*/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Step 4: traitement*/
                String login=etLogin.getText().toString();
                String password=etPassword.getText().toString();
                if(TextUtils.isEmpty(login)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()<6){
                    Toast.makeText(getApplicationContext(),"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
                    return;
                }

                //initialisation de la list et la charger une seule fois  et recuperation des données à partir de la base database
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    //DataSnapshot contient les données lus d'après la base
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            Question question=dataSnapshot.getValue(Question.class);
                            questionsList.add(question);
                        }
                    }
                    //si la lecture est annulée
                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

                //Authentification
                auth.signInWithEmailAndPassword(login,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"User Logged in Successfully!  connexion réussi! الدخول ناجح! 註冊成功  ",Toast.LENGTH_LONG).show();
                            //Intent intent = new Intent(LoginActivity.this, QuizActivity.class);
                            Intent intent = new Intent(LoginActivity.this, QuizActivity.class);
                            startActivity(intent);
                            //startActivity(new Intent(LoginActivity.this,QuizActivity.class));
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"Log in Error!"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });





            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}