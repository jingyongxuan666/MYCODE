package com.program.snookerscoretracker;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.program.snookerscoretracker.com.program.snookerscoretracker.entities.Balls;
import com.program.snookerscoretracker.com.program.snookerscoretracker.entities.Players;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton ibRed;
    private ImageButton ibBlack;
    private ImageButton ibYellow;
    private ImageButton ibGreen;
    private ImageButton ibBrown;
    private ImageButton ibBlue;
    private ImageButton ibPink;
    private Button end;
    private Button foul;
    private Button switchButton;
    private TextView numOfRed;
    private TextView score1;
    private TextView score2;
    private EditText player1;
    private EditText player2;

    private int currentPlayer = Players.PLAYER1;
    private int lastPottedBall = Balls.COLOREDBALL;
    private String nextPottedBall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize views
        initView();

        initalStatus();
        //setListeners
        setListeners();
    }
    /*
        initialize views
     */
    private void initView(){
        //ImageButton
        ibBlack = (ImageButton) findViewById(R.id.black);
        ibBlue  = (ImageButton) findViewById(R.id.blue);
        ibBrown = (ImageButton) findViewById(R.id.brown);
        ibGreen = (ImageButton) findViewById(R.id.green);
        ibPink = (ImageButton) findViewById(R.id.pink);
        ibYellow = (ImageButton) findViewById(R.id.yellow);
        ibRed = (ImageButton) findViewById(R.id.red);
        //Button
        end = (Button) findViewById(R.id.end);
        foul = (Button) findViewById(R.id.foul);
        switchButton = (Button) findViewById(R.id.switchbutton);
        //TextView
        score1 = (TextView) findViewById(R.id.score1);
        score2 = (TextView) findViewById(R.id.score2);
        numOfRed = (TextView) findViewById(R.id.numOfRed);
        //EditText
        player1 = (EditText) findViewById(R.id.player1);
        player2 = (EditText) findViewById(R.id.player2);

    }
    private void setListeners(){
        //onClickListener
        ibPink.setOnClickListener(this);
        ibRed.setOnClickListener(this);
        ibGreen.setOnClickListener(this);
        ibYellow.setOnClickListener(this);
        ibBrown.setOnClickListener(this);
        ibBlack.setOnClickListener(this);
        ibBlue.setOnClickListener(this);

        end.setOnClickListener(this);
        switchButton.setOnClickListener(this);
        foul.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.red:
                if (Balls.redBall > 0){
                    if (lastPottedBall == Balls.COLOREDBALL){
                        count(Balls.scoreRed);
                        Balls.redBall--;
                        lastPottedBall = Balls.REDBALL;
                    }else{
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Tip")
                                .setMessage("Now the current player should pot a Colored ball")
                                .setPositiveButton("OK",null)
                                .show();
                    }

                }else{
                    Toast.makeText(getBaseContext(),"There is no Red Ball",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.yellow:
                countColoredBall(Balls.scoreYellow,"yellow",0);
                break;
            case R.id.green:
                countColoredBall(Balls.scoreGreen,"green",1);
                break;
            case R.id.brown:
                countColoredBall(Balls.scoreBrown,"brown",2);
                break;
            case R.id.blue:
                countColoredBall(Balls.scoreBlue,"blue",3);
                break;
            case R.id.pink:
                countColoredBall(Balls.scorePink,"pink",4);
                break;
            case R.id.black:
                countColoredBall(Balls.scoreBlack,"black",5);
                break;
            case R.id.end:
                String winMessage;
                String winner = theWinner();
                if (winner.equals("tie")){
                    winMessage = "The score was tied";
                }else{
                    winMessage = "The Winner is "
                            + "<font color = 'red'><strong>"+winner+"</strong></font>";
                }
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Result")
                        .setMessage(Html.fromHtml(winMessage))
                        .setPositiveButton("OK", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                initalStatus();
                            }
                        })
                        .show();

                break;
            case R.id.switchbutton:
                switchPlayer();
                break;
            case R.id.foul:
                if(currentPlayer == Players.PLAYER1){
                    Players.player2Score += 4;
                }else{
                    Players.player1Score += 4;
                }
                switchPlayer();
                break;
        }
        score1.setText(Players.player1Score+"");
        score2.setText(Players.player2Score+"");
        numOfRed.setText(Balls.redBall+"");

    }
    //游戏结束，判断赢家
    private String theWinner(){
        Players.player1Name = player1.getText().toString();
        Players.player2Name = player2.getText().toString();
        if (Players.player1Score > Players.player2Score){
            return Players.player1Name;
        }else if(Players.player1Score < Players.player2Score){
            return Players.player2Name;
        }else{
            return "tie";
        }
    }
    //初始状态
    private void initalStatus(){
        Balls.redBall = 15;
        Players.player1Score = 0;
        Players.player2Score = 0;
        currentPlayer = Players.PLAYER1;
        lastPottedBall = Balls.COLOREDBALL;
        nextPottedBall = Balls.shouldBePottedBall[0];
        player1.setBackgroundColor(Color.BLUE);
        player2.setBackgroundColor(Color.TRANSPARENT);
        score1.setText(Players.player1Score+"");
        score2.setText(Players.player2Score+"");
        numOfRed.setText(Balls.redBall+"");
        player1.setText("Name1");
        player2.setText("Name2");

    }
    //计算分数
    private void count(int pottedBall){
        if(currentPlayer == Players.PLAYER1){
            Players.player1Score += pottedBall;
        }else{
            Players.player2Score += pottedBall;
        }
    }

    private void countColoredBall(int coloredBall,String nextBall,int i){
        if (lastPottedBall == Balls.REDBALL && Balls.redBall != 0){
            count(coloredBall);
            lastPottedBall = Balls.COLOREDBALL;
        }else if(Balls.redBall == 0){
            //红球没有的时候，按顺序打彩球
            if (nextBall.equals(nextPottedBall)){
                count(coloredBall);
                nextPottedBall = Balls.shouldBePottedBall[i+1];
            }else{
                String ballMsg;
                if(!nextPottedBall.equals("no")){
                    ballMsg = "Now the current player should pot a "
                            +nextPottedBall
                            +" ball";
                }else{
                    ballMsg = "There is no any Ball";
                }
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Tip")
                        .setMessage(ballMsg)
                        .setPositiveButton("OK",null)
                        .show();
            }
        }else{
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Tip")
                    .setMessage("Now the current player should pot a Red ball")
                    .setPositiveButton("OK",null)
                    .show();
        }
    }
    private void switchPlayer(){
        lastPottedBall = Balls.COLOREDBALL;
        if(currentPlayer == Players.PLAYER1){
            currentPlayer = Players.PLAYER2;
            player2.setBackgroundColor(Color.BLUE);
            player1.setBackgroundColor(Color.TRANSPARENT);
        }else{
            currentPlayer = Players.PLAYER1;
            player1.setBackgroundColor(Color.BLUE);
            player2.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
