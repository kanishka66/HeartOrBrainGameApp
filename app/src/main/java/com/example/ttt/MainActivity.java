package com.example.ttt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[3][3];
    private boolean player1turn = true;
    private int roundcount;
    private int player1point;
    private int player2point;
    private TextView tvp1;
    private TextView tvp2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvp1=findViewById(R.id.textp1);
        tvp2=findViewById(R.id.textp2);

        for(int i=0; i<3; i++) {
            for(int j =0; j<3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset = findViewById(R.id.Button_Reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }
        if(player1turn) {
            ((Button) view).setText("💙");
        }else {
            ((Button) view).setText("🧠");
        }
        roundcount++;

        if(checkforwin()){
            if (player1turn)
            {
                player1Wins();
            } else {
                player2Wins();
            }
        }else if (roundcount == 9) {
            draw();
        }else {
            player1turn = !player1turn;
        }
    }

    private boolean checkforwin() {
        String[][] field =new String[3][3];

        for(int i=0; i<3; i++) {
            for(int j=0; j< 3; j++)  {
                field[i][j] =buttons[i][j].getText().toString();
            }
        }
        for (int i=0; i<3; i++) {
            if(field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i=0; i<3; i++) {
            if(field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if(field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        if(field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return  false;

    }

    private void player1Wins() {
        player1point++;
        Toast.makeText(this,"Heart wins",Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        player2point++;
        Toast.makeText(this,"Brain wins",Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this,"Draw",Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText(){
        tvp1.setText("player 1 :" + player1point);
        tvp2.setText("player 2: "+ player2point);
    }

    private  void resetBoard(){
        for (int i=0; i < 3; i++) {
            for (int j =0; j <3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundcount =0;
        player1turn =true;
    }

    private void resetGame() {
        player1point =0;
        player2point =0;
        updatePointsText();
        resetBoard();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundcount);
        outState.putInt("player1ponts",player1point);
        outState.putInt("player2ponts",player2point);
        outState.putBoolean("player1Turn",player1turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundcount = savedInstanceState.getInt("roundCount");
        player1point = savedInstanceState.getInt("player1point");
        player2point = savedInstanceState.getInt("player2point");
        player1turn= savedInstanceState.getBoolean("player1turn");
    }
}