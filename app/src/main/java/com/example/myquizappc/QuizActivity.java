package com.example.myquizappc;

import static com.example.myquizappc.LoginActivity.questionsList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {
    /*step1: declaration*/
    ArrayList<Question> allQuestionList;
    RadioGroup rg;
    RadioButton rb1,rb2;
    Button next;
    Question question;
    TextView qstText,qzText;
    int score;
    int nbQuiz=1;
    int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        /*step2: recuperation Ids*/
        qstText = findViewById(R.id.textViewQst);
        rg = findViewById(R.id.rg);
        rb1 = findViewById(R.id.rB1);
        rb2 = findViewById(R.id.rB2);
        qzText = findViewById(R.id.textView2);

        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        next = findViewById(R.id.button2);

        allQuestionList = questionsList;

        //permutation au hasard des éléments de la liste
        Collections.shuffle(allQuestionList);
        question = questionsList.get(index);

        //Desactiver bouton next
        next.setClickable(false);
        //modifier le contenu de la 1ere question
        setAllData();

    }

    private void setAllData(){
        qstText.setText(question.getQuestion());
        rb1.setText(question.getOption1());
        rb2.setText(question.getOption2());
    }
    private void initialColor(){
        rb1.setBackground(getResources().getDrawable(R.drawable.rectangle));
        rb2.setBackground(getResources().getDrawable(R.drawable.rectangle));
    }

    public void Disabled(){
        rb1.setClickable(false);
        rb2.setClickable(false);
    }
    public void Enabled(){
        rb1.setClickable(true);
        rb2.setClickable(true);
    }

    private void finishQuiz(){
        finish();
    }

    public void isIncorrect(){
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index<questionsList.size()-1){
                    index++;
                    question=questionsList.get(index);
                    initialColor();
                    setAllData();
                    Enabled();
                    nbQuiz++;
                    qzText.setText("Quiz "+nbQuiz);
                    next.setClickable(false);
                }else{
                    Intent i=new Intent(QuizActivity.this,ResultActivity.class);
                    i.putExtra("score",score);
                    startActivity(i);
                    finishQuiz();
                }
            }
        });
    }
    public void isCorrect(){
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index<questionsList.size()-1){
                    index++;
                    score++;
                    question=questionsList.get(index);
                    initialColor();
                    setAllData();
                    Enabled();
                    nbQuiz++;
                    qzText.setText("Quiz "+nbQuiz);
                    next.setClickable(false);
                }else{
                    Intent i=new Intent(QuizActivity.this, ResultActivity.class);
                    i.putExtra("score",score);
                    startActivity(i);
                    finishQuiz();
                }

            }
        });

    }
    public void Option1Click(View view) {
        next.setClickable(true);
        Disabled();
        if (question.getOption1().equals(question.getAnswer())) {
            rb1.setBackgroundColor(getResources().getColor(R.color.green));
            isCorrect();
        }else{
            rb1.setBackgroundColor(getResources().getColor(R.color.red));
            isIncorrect();
        }
    }


    public void Option2Click(View view) {
        next.setClickable(true);
        Disabled();
        if (question.getOption2().equals(question.getAnswer())) {
            rb2.setBackgroundColor(getResources().getColor(R.color.green));
            isCorrect();
        }else{
            rb2.setBackgroundColor(getResources().getColor(R.color.red));
            isIncorrect();
        }
    }
}