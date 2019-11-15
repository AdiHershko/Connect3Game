package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int turn=0; //yellow = 0, red = 1
    Piece[] board = new Piece[9];
    int[][] winningPositions= {{0,1,2},{0,3,6},{0,4,8},{3,4,5},{6,7,8},{2,5,8},{2,4,6},{1,4,7}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0;i<9;i++)
            board[i]=new Piece(Color.Black);

    }

    public void dropin(View view)
    {
        if (board[Integer.parseInt(view.getTag().toString())].color==Color.Black) {
            turn = turn ^ 1;
            ImageView piece = (ImageView) view;
            piece.setTranslationY(-1000f);
            if (turn == 0) {
                piece.setImageResource(R.drawable.yellow);
                board[Integer.parseInt(view.getTag().toString())] = new Piece(Color.Yellow);
            } else {
                piece.setImageResource(R.drawable.red);
                board[Integer.parseInt(view.getTag().toString())] = new Piece(Color.Red);
            }
            piece.animate().translationYBy(1000f).rotation(3600).setDuration(1000);
            if (didWin())
            {
                LinearLayout resetLayout = findViewById(R.id.playAgainLayout);
                resetLayout.setVisibility(View.VISIBLE);
                TextView t = findViewById(R.id.winView);
                t.setText("Player " + turn + " Won!");
            }
        }

    }
    public void PlayAgain(View view)
    {
        LinearLayout resetLayout = findViewById(R.id.playAgainLayout);
        resetLayout.setVisibility(View.INVISIBLE);
        GridLayout g = findViewById(R.id.grid);
        for (int i = 0 ;i<g.getChildCount();i++)
            ((ImageView)g.getChildAt(i)).setImageResource(0);
        turn =0;
        for (int i = 0;i<9;i++)
            board[i]=new Piece(Color.Black);

    }

    private boolean didWin()
    {
        if (board[0].color==board[1].color&&board[0].color==board[2].color&&board[0].color!=Color.Black) return true;
        if (board[0].color==board[3].color&&board[0].color==board[6].color&&board[0].color!=Color.Black) return true;
        if (board[0].color==board[4].color&&board[0].color==board[8].color&&board[0].color!=Color.Black) return true;
        if (board[3].color==board[4].color&&board[3].color==board[5].color&&board[3].color!=Color.Black) return true;
        if (board[6].color==board[7].color&&board[6].color==board[8].color&&board[6].color!=Color.Black) return true;
        if (board[2].color==board[5].color&&board[2].color==board[8].color&&board[2].color!=Color.Black) return true;
        if (board[2].color==board[4].color&&board[2].color==board[6].color&&board[2].color!=Color.Black) return true;
        if (board[1].color==board[4].color&&board[1].color==board[7].color&&board[1].color!=Color.Black) return true;
        return false;
    }


    private class Piece {
        Color color;
        public Piece(Color color)
        {
            this.color=color;
        }//
    }

    enum Color { Red,Yellow,Black }
}
