package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int turn=0; //yellow = 0, red = 1
    Piece[] board = new Piece[9];
    // int[][] winningPositions= {{0,1,2},{0,3,6},{0,4,8},{3,4,5},{6,7,8},{2,5,8},{2,4,6},{1,4,7}}; these are the positions on board which trigger a win
    MediaPlayer[] sounds = new MediaPlayer[3]; //sounds are stored in this array
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0;i<9;i++) //set board to blank pieces
            board[i]=new Piece(Color.Black);
        // ---------creating media ---------
        sounds[0] = MediaPlayer.create(this,R.raw.chip);
        sounds[1]=MediaPlayer.create(this,R.raw.win);
        sounds[2]=MediaPlayer.create(this,R.raw.draw);


    }

    public void dropin(View view) //drops a chip into the board
    {
        if (board[Integer.parseInt(view.getTag().toString())].color==Color.Black) { //if the place is not held by other piece
            turn = turn ^ 1; //switch turns
            sounds[0].start();
            ImageView piece = (ImageView) view; //imageview of chip
            piece.setTranslationY(-1000f); //move chip upwards so we can make it fall
            if (turn == 0) { //set chip color and place color on board
                piece.setImageResource(R.drawable.yellow);
                board[Integer.parseInt(view.getTag().toString())] = new Piece(Color.Yellow);
            } else {
                piece.setImageResource(R.drawable.red);
                board[Integer.parseInt(view.getTag().toString())] = new Piece(Color.Red);
            }
            piece.animate().translationYBy(1000f).rotation(2500).setDuration(600); //makes chip fall and rotate
            if (didWin()) //if win condition is triggered show win layout and play a sound
            {
                sounds[1].start();
                LinearLayout resetLayout = findViewById(R.id.playAgainLayout);
                resetLayout.setVisibility(View.VISIBLE);
                GridLayout grid =findViewById(R.id.grid);
                grid.setVisibility(View.INVISIBLE); //hide board layout
                TextView t = findViewById(R.id.winView);
                if (turn==1)
                    t.setText("Red player Won!");
                else
                    t.setText("Yellow player Won!");
            }
            if (isDraw()) //if draw condition is triggered show draw layout and play a sound
            {
                sounds[2].start();
                LinearLayout resetLayout = findViewById(R.id.playAgainLayout);
                resetLayout.setVisibility(View.VISIBLE);
                GridLayout grid =findViewById(R.id.grid);
                grid.setVisibility(View.INVISIBLE); //hide board layout
                TextView t = findViewById(R.id.winView);
                t.setText("Its a draw!");
            }
        }

    }
    public void PlayAgain(View view) //restart button onclick
    {
        LinearLayout resetLayout = findViewById(R.id.playAgainLayout);
        resetLayout.setVisibility(View.INVISIBLE); //hide win/draw layout
        GridLayout g = findViewById(R.id.grid); //board layout
        for (int i = 0 ;i<g.getChildCount();i++) //resetting images of chips
            ((ImageView)g.getChildAt(i)).setImageResource(0);
        turn =0;
        for (int i = 0;i<9;i++) //resetting board to blanks
            board[i]=new Piece(Color.Black);

        GridLayout grid =findViewById(R.id.grid);
        grid.setVisibility(View.VISIBLE); //show board layout
    }

    private boolean didWin() //scans if there are pieces of the same kind in the win positions
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

    private boolean isDraw() //if there is a blank piece, there is no draw.
    {
        for (int i = 0;i<9;i++)
            if (board[i].color==Color.Black)
                return false;
        return true;
    }


    private class Piece {
        Color color;
        public Piece(Color color)
        {
            this.color=color;
        }
    }

    enum Color { Red,Yellow,Black } //black color is "blank" piece
}
