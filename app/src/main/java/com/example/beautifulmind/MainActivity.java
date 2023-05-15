package com.example.beautifulmind;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnStartOver = null;
    private RadioGroup opponentTypeBtnGroup = null;
    private Button btnGenerate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialization of different widgets
        initWidget();

        // On clicking on generate button
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(opponentTypeBtnGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(MainActivity.this, "Select an opponent type first!",
                            Toast.LENGTH_LONG).show();
                }else{
                    RadioButton opponentTypeSelected = (RadioButton) findViewById(opponentTypeBtnGroup.getCheckedRadioButtonId());
                    String opponentType = opponentTypeSelected.getText().toString();

                    Intent intent = new Intent(getApplicationContext(),Game.class);
                    intent.putExtra("opponentType",opponentType);
                    startActivity(intent);
                    finish();

                }
            }
        });
    }

    //Initialize all widget of activity
    private void  initWidget(){
        btnGenerate = (Button) findViewById(R.id.btnGenerate);
        btnStartOver = (Button) findViewById(R.id.btnStartOver);
        opponentTypeBtnGroup = (RadioGroup) findViewById(R.id.opponentTypeBtnGroup);
    }
}