package com.example.beautifulmind;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public final static int GO_GAME_REQUEST_CODE = 2;

    private RadioGroup opponentTypeBtnGroup = null;
    private Button btnGenerate = null;

    private TextView numberGamePlayedText = null;
    //private int numberGamePlayed = 0;
    private TextView myTotalScoreCell = null;
    private TextView opponentTotalScoreCell = null;
    private Button btnStartOver = null;
    private ImageView nashImage = null;

    RadioButton opponentTypeRadioBtnSelected = null;

    private int myTotalScore = 0;
    private int opponentTotalScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialization of different widgets
        initWidget();

        opponentTypeBtnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                numberGamePlayedText.setText("0");
                myTotalScoreCell.setText("0");
                opponentTotalScoreCell.setText("0");

                opponentTypeRadioBtnSelected = radioGroup.findViewById(checkedId);
                if (radioGroup.indexOfChild(opponentTypeRadioBtnSelected) == 3){
                    nashImage.setImageResource(R.drawable.john_forbes_nash);
                }else {
                    nashImage.setImageResource(0);
                }
            }
        });

        //clicking on generate button
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedRadioBtnId = opponentTypeBtnGroup.getCheckedRadioButtonId();

                if(checkedRadioBtnId == -1) {
                    Toast.makeText(MainActivity.this, "Select an opponent type first!",
                            Toast.LENGTH_LONG).show();
                }else{
                    String opponentType = opponentTypeRadioBtnSelected.getText().toString();

                    Intent startGameIntent = new Intent(getApplicationContext(),Game.class);
                    startGameIntent.putExtra("opponentType",opponentType);
                    startActivityForResult(startGameIntent, GO_GAME_REQUEST_CODE);

                }
            }
        });

        //Clicking start over button
        btnStartOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberGamePlayedText.setText("0");
                myTotalScoreCell.setText("0");
                opponentTotalScoreCell.setText("0");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GO_GAME_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                int numberGamePlayed =  Integer.parseInt((String) numberGamePlayedText.getText()) + data.getIntExtra("game_played",1);
                numberGamePlayedText.setText(String.valueOf(numberGamePlayed));

                int myObtain = Integer.parseInt((String) myTotalScoreCell.getText()) + data.getIntExtra("my_obtain",0);
                int opponentObtain = Integer.parseInt((String) opponentTotalScoreCell.getText())+ data.getIntExtra("opponent_obtain",0);

                myTotalScoreCell.setText(String.valueOf(myObtain));
                opponentTotalScoreCell.setText(String.valueOf(opponentObtain));

            }
        }
    }


    //Initialize all widget of activity
    private void  initWidget(){
        btnGenerate = findViewById(R.id.btnGenerate);
        opponentTypeBtnGroup = findViewById(R.id.opponentTypeBtnGroup);

        numberGamePlayedText = findViewById(R.id.numberGamesPlayed);
        myTotalScoreCell = findViewById(R.id.myTotalScoreCell);
        opponentTotalScoreCell = findViewById(R.id.opponentTotalScoreCell);

        nashImage = findViewById(R.id.nash_image);

        btnStartOver = findViewById(R.id.btnStartOver);
    }
}