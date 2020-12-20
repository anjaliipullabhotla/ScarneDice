package com.example.scarmesdice;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public int userScore = 0;
    public int userScoreOnTurn = 0;
    public int computerScore = 0;
    public int computerScoreOnTurn = 0;
    Random random = new Random();
    public int numRounds = random.nextInt(6) + 1;
    ImageView diceImage;
    TextView scoreText;
    Drawable drawable;
    Button roll, hold;
    android.os.Handler handler = new android.os.Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        diceImage = (ImageView) findViewById(R.id.imageView);
        scoreText = (TextView) findViewById(R.id.textView);
        roll = (Button) findViewById(R.id.button);
        hold = (Button) findViewById(R.id.button2);
    }

    public int rollDice() {
        int diceNum = random.nextInt(6) + 1;
        return diceNum;
    }

    public void rollDiceView(View view) {
        int diceNum = rollDice();
        switch (diceNum) {
            case 1:
                userScoreOnTurn = 0;
                diceImage.setImageDrawable(getResources().getDrawable(R.drawable.dice1));
                roll.setEnabled(false);
                hold.setEnabled(false);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        computerTurn();
                        numRounds += random.nextInt(6) + 1;
                    }
                },1000);
                break;
            case 2:
                userScoreOnTurn += diceNum;
                drawable = getResources().getDrawable(R.drawable.dice2);
                break;
            case 3:
                userScoreOnTurn += diceNum;
                drawable = getResources().getDrawable(R.drawable.dice3);
                break;
            case 4:
                userScoreOnTurn += diceNum;
                drawable = getResources().getDrawable(R.drawable.dice4);
                break;
            case 5:
                userScoreOnTurn += diceNum;
                drawable = getResources().getDrawable(R.drawable.dice5);
                break;
            case 6:
                userScoreOnTurn += diceNum;
                drawable = getResources().getDrawable(R.drawable.dice6);
                break;
        }
        scoreText.setText(String.format("Your Score: %d, Computer Score: %d, Your Turn Score: %d", userScore, computerScore, userScoreOnTurn));
        if (diceNum != 1) {
            diceImage.setImageDrawable(drawable);
            checkForWinner();
        }
    }

    public void holdDiceView(View view) {
        userScore += userScoreOnTurn;
        scoreText.setText(String.format("Your Score: %d, Computer Score: %d, Your Turn Score: %d", userScore, computerScore, userScoreOnTurn));
        userScoreOnTurn = 0;
        checkForWinner();
        if (!checkForWinner()) {
            roll.setEnabled(false);
            hold.setEnabled(false);
            computerTurn();
            numRounds = random.nextInt(6) + 1;
        }
        checkForWinner();
    }

    public void resetDiceView(View view) {
        roll.setEnabled(true);
        hold.setEnabled(true);
        userScore = 0;
        userScoreOnTurn = 0;
        computerScore = 0;
        computerScoreOnTurn = 0;
        scoreText.setText(String.format("Your Score: %d, Computer Score: %d", userScore, computerScore));
        diceImage.setImageDrawable(getResources().getDrawable(R.drawable.dice1));
    }

    public void computerTurn() {
        int diceNum = rollDice();
        numRounds -= 1;
        switch (diceNum) {
            case 1:
                computerScoreOnTurn = 0;
                computerScore += computerScoreOnTurn;
                scoreText.setText(String.format("Your Score: %d, Computer Score: %d, Computer Turn Score: %d", userScore, computerScore, computerScoreOnTurn));
                diceImage.setImageDrawable(getResources().getDrawable(R.drawable.dice1));
                roll.setEnabled(true);
                hold.setEnabled(true);
                break;
            case 2:
                computerScoreOnTurn += diceNum;
                scoreText.setText(String.format("Your Score: %d, Computer Score: %d, Computer Turn Score: %d", userScore, computerScore, computerScoreOnTurn));
                diceImage.setImageDrawable(getResources().getDrawable(R.drawable.dice2));
                break;
            case 3:
                computerScoreOnTurn += diceNum;
                scoreText.setText(String.format("Your Score: %d, Computer Score: %d, Computer Turn Score: %d", userScore, computerScore, computerScoreOnTurn));
                diceImage.setImageDrawable(getResources().getDrawable(R.drawable.dice3));
                break;
            case 4:
                computerScoreOnTurn += diceNum;
                scoreText.setText(String.format("Your Score: %d, Computer Score: %d, Computer Turn Score: %d", userScore, computerScore, computerScoreOnTurn));
                diceImage.setImageDrawable(getResources().getDrawable(R.drawable.dice4));
                break;
            case 5:
                computerScoreOnTurn += diceNum;
                scoreText.setText(String.format("Your Score: %d, Computer Score: %d, Computer Turn Score: %d", userScore, computerScore, computerScoreOnTurn));
                diceImage.setImageDrawable(getResources().getDrawable(R.drawable.dice5));
                break;
            case 6:
                computerScoreOnTurn += diceNum;
                scoreText.setText(String.format("Your Score: %d, Computer Score: %d, Computer Turn Score: %d", userScore, computerScore, computerScoreOnTurn));
                diceImage.setImageDrawable(getResources().getDrawable(R.drawable.dice6));
                break;
        }
        if (numRounds > 0 && diceNum != 1) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    computerTurn();
                }
            },1000);
        } else {
            computerScore += computerScoreOnTurn;
            scoreText.setText(String.format("Your Score: %d, Computer Score: %d, Computer Turn Score: %d", userScore, computerScore, computerScoreOnTurn));
            checkForWinner();
            if (!checkForWinner()) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        computerScoreOnTurn = 0;
                        roll.setEnabled(true);
                        hold.setEnabled(true);
                    }
                }, 1000);
            }
        }
    }

    public boolean checkForWinner() {
        if (computerScore >= 100) {
            scoreText.setText(String.format("Your Score: %d, Computer Score: %d\nComputer has won!", userScore, computerScore));
            roll.setEnabled(false);
            hold.setEnabled(false);
            return true;
        }
        if (userScore >= 100) {
            scoreText.setText(String.format("Your Score: %d, Computer Score: %d\nYou have won!", userScore, computerScore));
            roll.setEnabled(false);
            hold.setEnabled(false);
            return  true;
        }
        return false;
    }
}