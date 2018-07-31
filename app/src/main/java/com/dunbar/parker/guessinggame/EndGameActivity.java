package com.dunbar.parker.guessinggame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        int totalScore = getIntent().getIntExtra("score", 0);
        if(totalScore < 0) {
            totalScore = 0;
        }
        TextView score = (TextView) findViewById(R.id.text_score);
        score.setText(totalScore + " / 50");
    }
}