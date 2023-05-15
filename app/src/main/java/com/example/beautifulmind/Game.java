package com.example.beautifulmind;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Game extends AppCompatActivity {

    private String opponentType = "";
    private final static int MIN_MATRIX = -5;
    private final static int MAX_MATRIX = 5;
    private TextView utility11 = null;
    private TextView utility12 = null;
    private TextView utility21 = null;
    private TextView utility22 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();
        opponentType = extras.getString("opponentType","Random");

        initWidgets();

        //Toast.makeText(getApplicationContext(),generateRandomUtilityMatrix(),Toast.LENGTH_SHORT).show();
        fillGameTable();


    }

    //To generate the utility matrix
    private String generateRandomUtilityMatrix(){
        Random r = new Random();

        int x = r.nextInt((MAX_MATRIX - MIN_MATRIX) +1) + MIN_MATRIX;

        int y = r.nextInt((MAX_MATRIX - MIN_MATRIX) +1) + MIN_MATRIX;

        return String.valueOf(x)+" / "+String.valueOf(y);
    }

    // this function fill the utility matrix to each cell of table
    private void fillGameTable(){
        String[] matrixTable = new String[4];
        TextView[] utilityCell = new TextView[4];

        String matrix =  generateRandomUtilityMatrix();
        matrixTable[0] = matrix;

        for (int i = 1; i < 4; i++) {
             matrix =  generateRandomUtilityMatrix();
                 for (String matrixCell : matrixTable) {
                     if (matrixCell == matrix) {
                         matrix = generateRandomUtilityMatrix();
                     }
                 }
                 matrixTable[i] = matrix;
        }

        utilityCell[0] = utility11;
        utilityCell[1] = utility12;
        utilityCell[2] = utility21;
        utilityCell[3] = utility22;

        for(int i = 0; i < 4; i++){
            utilityCell[i].setText(matrixTable[i]);
        }

    }

    //Initialize all widget of activity
    private void initWidgets(){
        utility11 = (TextView) findViewById(R.id.utility11);
        utility12 = (TextView) findViewById(R.id.utility12);
        utility21 = (TextView) findViewById(R.id.utility21);
        utility22 = (TextView) findViewById(R.id.utility22);
    }
}