package com.example.myquizappc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ResultActivity extends AppCompatActivity {
    /*step1: declaration */
    Button bLogout,bTry;
    ProgressBar progressBar;
    TextView tvScore;
    FirebaseAuth auth;
    int score;
    Button mapS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        /*step2: recuperation des Ids*/
        tvScore =(TextView) findViewById(R.id.tvScore);
        Intent intent=getIntent();
        score=intent.getIntExtra("score",0) ;
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(100*score/7);
        tvScore.setText(100*score/7+"%");
        bLogout=(Button)findViewById(R.id.bLogout);
        mapS=(Button) findViewById(R.id.map);

        /*step3*/
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*step4*/
                Toast.makeText(getApplicationContext(), "Merci de votre Participation !", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(ResultActivity.this,LoginActivity.class);
                startActivity(i);
                auth.signOut();

            }
        });

        bTry=(Button)findViewById(R.id.bTry);
        bTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ResultActivity.this,QuizActivity.class);
                startActivity(i);
            }
        });

        mapS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ResultActivity.this,MapsActivity.class);
                intent.putExtra("score",score);
                startActivity(intent);
            }
        });
    }

}