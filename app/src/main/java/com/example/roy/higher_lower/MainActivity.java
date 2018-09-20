package com.example.roy.higher_lower;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int[] imageNames;
    private int currentScore = 0;
    private int highScore = 0;
    private int currentRoll;
    private int nextRoll;
    private List<String> rollHistory;
    private Button upBtn;
    private Button downBtn;
    private ImageView diceView;
    private TextView scoreView;
    private TextView hScoreView;
    private ListView historyView;
    private ArrayAdapter<String> Adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upBtn = findViewById(R.id.upBtn);
        downBtn = findViewById(R.id.downBtn);
        diceView = findViewById(R.id.diceView);
        scoreView = findViewById(R.id.scoreView);
        hScoreView = findViewById(R.id.hScoreView);
        historyView = findViewById(R.id.historyView);

        imageNames = new int[]{R.drawable.dice_1, R.drawable.dice_2, R.drawable.dice_3, R.drawable.dice_4, R.drawable.dice_5, R.drawable.dice_6};

        rollHistory = new ArrayList<>();

        //initiate first roll
        diceRoll();

        //listener for the up(higher) button
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentRoll > nextRoll) {
                    String result = " wrong";
                    currentScore = 0;

                    result(result, nextRoll);

                } else {
                    String result = " right";
                    currentScore++;

                    result(result, nextRoll);

                }
            }
        });

        // listener for the down(lower) button
        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentRoll < nextRoll) {
                    String result = " wrong";
                    currentScore = 0;

                    result(result, nextRoll);

                } else {
                    String result = " right";
                    currentScore++;

                    result(result, nextRoll);
                }
            }
        });
    }

    private void result(String result, int nextRoll) {

        //Inform player about result
        String message = "The next roll was " + nextRoll + " you were " + result;
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

        //update score
        scoreView.setText("Score " + currentScore);

        //update highscore
        if (currentScore > highScore){
            highScore = currentScore;
            hScoreView.setText("Highscore: " + highScore);

        }


        //update history listview
        rollHistory.add("Roll was "+ String.valueOf(nextRoll));
        updateUI();

        //diceRoll to start a new game
        diceRoll();

    }

    private void diceRoll() {
        Random rand = new Random();

        //Generate random numbers
        currentRoll = rand.nextInt(6) + 1;
        nextRoll = rand.nextInt(6) + 1;

        //If the numbers are equal we need to reroll one
        if (currentRoll == nextRoll) {
            nextRoll = rand.nextInt(6) + 1;
        }
        //Set the corresponding image of the currentroll
        diceView.setImageResource(imageNames[currentRoll - 1]);

    }

    private void updateUI() {
        if (Adapter == null) {
            Adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rollHistory);
            historyView.setAdapter(Adapter);

        } else {
            Adapter.notifyDataSetChanged();
        }

    }
}
