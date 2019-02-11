package com.example.braintrainner;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTimeCounter;
    private TextView mCorrectness;
    private TextView mQuestions;
    private TextView mTrueOrFalse;
    private TextView mAnswer1;
    private TextView mAnswer2;
    private TextView mAnswer3;
    private TextView mAnswer4;
    private Button mPlayAgain;
    private Boolean mIsGameStart = false;
    private int correctAnswer = 0;
    private int totalQuestions = 0;
    private int correctAnswerTag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimeCounter = (TextView) findViewById(R.id.time);
        mCorrectness = (TextView) findViewById(R.id.correct);
        mQuestions = (TextView) findViewById(R.id.questions);
        mTrueOrFalse = (TextView) findViewById(R.id.true_or_false);
        mAnswer1 = (TextView) findViewById(R.id.answer1);
        mAnswer2 = (TextView) findViewById(R.id.answer2);
        mAnswer3 = (TextView) findViewById(R.id.answer3);
        mAnswer4 = (TextView) findViewById(R.id.answer4);
        mPlayAgain = (Button) findViewById(R.id.play_again);

        mTimeCounter.setText("0 : 30");
        mCorrectness.setText("0 / 0");

        mPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mIsGameStart) {
                    startGame();
                } else {
                    resetGame();
                }
            }
        });

        mAnswer1.setOnClickListener(onAnswerClicked);
        mAnswer1.setEnabled(false);
        mAnswer2.setOnClickListener(onAnswerClicked);
        mAnswer2.setEnabled(false);
        mAnswer3.setOnClickListener(onAnswerClicked);
        mAnswer3.setEnabled(false);
        mAnswer4.setOnClickListener(onAnswerClicked);
        mAnswer4.setEnabled(false);
    }

    private void startCounter(boolean startGame) {
        CountDownTimer count = new CountDownTimer(30 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                mTimeCounter.setText(convertTime((int) l / 1000));
            }

            @Override
            public void onFinish() {
                mTrueOrFalse.setText("Time Up!" + "\n" + "Your score: " + String.valueOf(correctAnswer) + " / " + String.valueOf(totalQuestions));
                mAnswer1.setEnabled(false);
                mAnswer2.setEnabled(false);
                mAnswer3.setEnabled(false);
                mAnswer4.setEnabled(false);
                mIsGameStart = false;
            }
        };
        if (startGame) {
            count.start();
        } else {
            count.cancel();
        }
    }

    private void startGame() {
        startCounter(true);
        mPlayAgain.setText("Reset Game!");
        mAnswer1.setEnabled(true);
        mAnswer2.setEnabled(true);
        mAnswer3.setEnabled(true);
        mAnswer4.setEnabled(true);
        mQuestions.setVisibility(View.VISIBLE);
        mTrueOrFalse.setVisibility(View.VISIBLE);
        showQuestion();
    }

    private void resetGame() {
        mTimeCounter.setText("0 : 30");
        mCorrectness.setText("0 / 0");
        startCounter(false);
        mIsGameStart = false;
        mPlayAgain.setText("START!");
        mQuestions.setVisibility(View.INVISIBLE);
        mTrueOrFalse.setVisibility(View.INVISIBLE);
    }

    private void showAddQuestions() {
        int i = (int) (Math.random() * 100 + 1);
        int j = (int) (Math.random() * 100 + 1);
        int sum = i + j;
        mQuestions.setText(String.valueOf(i) + " + " + String.valueOf(j) + " = ?");
        showAnswers(sum, 0);
    }

    private void showMinusQuestions() {
        int i = (int) (Math.random() * 100 + 1);
        int j = (int) (Math.random() * 100 + 1);
        int sum = i - j;
        mQuestions.setText(String.valueOf(i) + " - " + String.valueOf(j) + " = ?");
        showAnswers(sum, 1);
    }

    private void showTimeQuestions() {
        int i = (int) (Math.random() * 10 + 1);
        int j = (int) (Math.random() * 10 + 1);
        int sum = i * j;
        mQuestions.setText(String.valueOf(i) + " * " + String.valueOf(j) + " = ?");
        showAnswers(sum, 2);
    }

    private void showQuestion() {
        int type = (int) (Math.random() * 3);
        Log.d("Brain trainer", "random number is " + type);
        switch (type) {
            case 0:
                showAddQuestions();
                break;
            case 1:
                showMinusQuestions();
                break;
            case 2:
                showTimeQuestions();
                break;
        }
    }

    private void showAnswers(int answer, int type) {
        int tag = (int) (Math.random() * 4);
        switch (tag) {
            case 0:
                mAnswer1.setText(String.valueOf(answer));
                mAnswer2.setText(generateAnotherAnswer(answer, type));
                mAnswer3.setText(generateAnotherAnswer(answer, type));
                mAnswer4.setText(generateAnotherAnswer(answer, type));
                correctAnswerTag = 0;
                break;
            case 1:
                mAnswer2.setText(String.valueOf(answer));
                mAnswer1.setText(generateAnotherAnswer(answer, type));
                mAnswer3.setText(generateAnotherAnswer(answer, type));
                mAnswer4.setText(generateAnotherAnswer(answer, type));
                correctAnswerTag = 1;
                break;
            case 2:
                mAnswer3.setText(String.valueOf(answer));
                mAnswer1.setText(generateAnotherAnswer(answer, type));
                mAnswer2.setText(generateAnotherAnswer(answer, type));
                mAnswer4.setText(generateAnotherAnswer(answer, type));
                correctAnswerTag = 2;
                break;
            case 3:
                mAnswer4.setText(String.valueOf(answer));
                mAnswer1.setText(generateAnotherAnswer(answer, type));
                mAnswer2.setText(generateAnotherAnswer(answer, type));
                mAnswer3.setText(generateAnotherAnswer(answer, type));
                correctAnswerTag = 3;
                break;
        }
    }

    private String generateAnotherAnswer(int answer, int type) {
        int anotherAnswer = 0;

        switch (type) {
            case 0:
                do{
                    anotherAnswer = (int) (Math.random() * 100 + 1) + (int) (Math.random() * 100 + 1);
                } while (anotherAnswer == answer);
            case 1:
                do{
                    anotherAnswer = (int) (Math.random() * 100 + 1) - (int) (Math.random() * 100 + 1);
                } while (anotherAnswer == answer);
            case 2:
                do{
                    anotherAnswer = (int) (Math.random() * 10 + 1) * (int) (Math.random() * 10 + 1);
                } while (anotherAnswer == answer);
        }
        return String.valueOf(anotherAnswer);
    }

    private String convertTime(int i) {
        String second;
        if (i < 10) {
            second = "0" + String.valueOf(i);
        } else {
            second = String.valueOf(i);
        }
        return "0 : " + second;
    }

    private View.OnClickListener onAnswerClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (correctAnswerTag == Integer.parseInt(view.getTag().toString())) {
                correctAnswer++;
                totalQuestions++;
                mTrueOrFalse.setText("Correct!!");
            } else {
                totalQuestions++;
                mTrueOrFalse.setText("Wrong!!");
            }
            moveToNextQuestion();
        }
    };

    private void moveToNextQuestion() {
        showQuestion();
        mCorrectness.setText(String.valueOf(correctAnswer) + " / " + String.valueOf(totalQuestions));
    }
}
