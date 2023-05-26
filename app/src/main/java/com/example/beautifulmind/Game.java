package com.example.beautifulmind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Game extends AppCompatActivity {

    private String opponentType = "";
    private final static int MIN_MATRIX = -5;
    private final static int MAX_MATRIX = 5;
    private TextView utilityAA = null;
    private TextView utilityAB = null;
    private TextView utilityBA = null;
    private TextView utilityBB = null;

    String[] matrixTable = new String[4];

    //buttons of different actions
    private Button aActionBtn = null;
    private Button bActionBtn = null;

    //Know if all buttons of action is clicking
    private boolean opponentActionIsChoosed = false;
    private int numberOfActionsButtonClicked = 0;

    private String myAction = "_";
    private String opponentAction = "_";

    private TextView myChoosedAction =null;
    private TextView opponentChoosedAction = null;

    private String myObtain = "";
    private String opponentObtain = "";

    private TextView myPoint = null;
    private TextView opponentPoint = null;

    private Button dismissButton = null;

    private int equilibriumNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();
        opponentType = extras.getString("opponentType","Random");
        TextView strategyName = (TextView) findViewById(R.id.strategy_name);
        strategyName.setText(opponentType);


        initWidgets();

        fillGameTable();

        aActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOfActionsButtonClicked++;
                if(!opponentActionIsChoosed){
                    myAction = "A";
                    if(opponentType.equals("Nash")){

                        for (String utility : matrixTable) {
                            String[] parts = utility.split("/");
                            if (parts[0].trim().equals(parts[1].trim())){
                                equilibriumNumber++;
                            }
                        }
                        if (equilibriumNumber > 1){
                            multipleEquilibriumToast();
                        }else{
                            opponentRandomAction();
                        }

                    }else{
                        opponentRandomAction();
                    }

                }

                if (opponentActionIsChoosed && numberOfActionsButtonClicked == 2){
                    aActionBtn.setEnabled(false);
                    bActionBtn.setEnabled(false);

                    myChoosedAction.setText("Action "+myAction);
                    opponentChoosedAction.setText("Action "+opponentAction);

                        String[] utility = null;
                        if (myAction.equals("A") ){
                            if (opponentAction.equals("A")){
                                utilityAA.setBackgroundColor(getResources().getColor(R.color.OBTAIN_CELL_BG));
                                utility = utilityAA.getText().toString().split("/");
                                myObtain = utility[0].trim();
                                opponentObtain = utility[1].trim();

                            } else if(opponentAction.equals("B")){
                                utilityAB.setBackgroundColor (getResources().getColor(R.color.OBTAIN_CELL_BG));
                                utility = utilityAB.getText().toString().split("/");
                                myObtain = utility[0].trim();
                                opponentObtain = utility[1].trim();
                            }
                        }

                        uniqueEqulibriumToast();

                    }

                    if (!myObtain.isEmpty() && !opponentObtain.isEmpty()){
                        myPoint.setText(myObtain+" points");
                        opponentPoint.setText(opponentObtain+" points");
                    }

            }
        });

        bActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOfActionsButtonClicked++;
                if(!opponentActionIsChoosed){
                    myAction = "B";

                    if(opponentType.equals("Nash")){

                        for (String utility : matrixTable) {
                            String[] parts = utility.split("/");
                            if (parts[0].trim().equals(parts[1].trim())){
                                equilibriumNumber++;
                            }
                        }
                        if (equilibriumNumber > 1){
                            multipleEquilibriumToast();
                        }else{
                            opponentRandomAction();
                        }

                    }else{
                        opponentRandomAction();
                    }
                }
                if (opponentActionIsChoosed && numberOfActionsButtonClicked == 2){
                    aActionBtn.setEnabled(false);
                    bActionBtn.setEnabled(false);

                    myChoosedAction.setText("Action "+myAction);
                    opponentChoosedAction.setText("Action "+opponentAction);

                        String[] utility = null;
                        if (myAction.equals("B") ){
                            if (opponentAction.equals("A")){
                                utilityBA.setBackgroundColor(getResources().getColor(R.color.OBTAIN_CELL_BG));
                                utility = utilityBA.getText().toString().split("/");
                                myObtain = utility[0].trim();
                                opponentObtain = utility[1].trim();

                            } else if(opponentAction.equals("B")){
                                utilityBB.setBackgroundColor(getResources().getColor(R.color.OBTAIN_CELL_BG));
                                utility = utilityBB.getText().toString().split("/");
                                myObtain = utility[0].trim();
                                opponentObtain = utility[1].trim();
                            }
                        }
                    uniqueEqulibriumToast();
                }

                if (!myObtain.isEmpty() && !opponentObtain.isEmpty()){
                    myPoint.setText(myObtain+" points");
                    opponentPoint.setText(opponentObtain+" points");
                }
            }
        });

        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultForMainIntent = new Intent(getApplicationContext(),MainActivity.class);

                if(myObtain.isEmpty() && opponentObtain.isEmpty()){
                    resultForMainIntent.putExtra("game_played",0);
                    Toast.makeText(getApplicationContext(),"The game could not be finished", Toast.LENGTH_LONG).show();
                }else{
                    resultForMainIntent.putExtra("game_played",1);
                    resultForMainIntent.putExtra("my_obtain",Integer.parseInt(myObtain));
                    resultForMainIntent.putExtra("opponent_obtain",Integer.parseInt(opponentObtain));
                }
                setResult(RESULT_OK, resultForMainIntent);
                finish();
            }
        });


    }

    //Display a unique nash equilibrium Toast
    private void uniqueEqulibriumToast(){
        if (!opponentAction.equals("_")){
            if (opponentType.equals("Nash")){
                String equilibriumNash = "There is a unique Nash equilibrium in this game: \n" +
                        "My action: "+myAction +
                        "\nOpponent action: "+opponentAction;
                Toast.makeText(getApplicationContext(),equilibriumNash,Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Mutiple nash equilibrium setting and Toast
    private void multipleEquilibriumToast(){
        aActionBtn.setEnabled(false);
        bActionBtn.setEnabled(false);

        String equilibriumNash = "There are multiple Nash equilibrium in this game: \n" +
                "Opponent can not choose action: \n" +
                "Game can not be finished: ";
        Toast.makeText(getApplicationContext(),equilibriumNash,Toast.LENGTH_SHORT).show();
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
        //String[] matrixTable = new String[4];
        TextView[] utilityCell = new TextView[4];

        String matrix =  generateRandomUtilityMatrix();
        this.matrixTable[0] = matrix;

        for (int i = 1; i < 4; i++) {
             matrix =  generateRandomUtilityMatrix();
                 for (String matrixCell : matrixTable) {
                     if (matrixCell == matrix) {
                         matrix = generateRandomUtilityMatrix();
                     }
                 }
                 this.matrixTable[i] = matrix;
        }

        utilityCell[0] = this.utilityAA;
        utilityCell[1] = this.utilityAB;
        utilityCell[2] = this.utilityBA;
        utilityCell[3] = this.utilityBB;

        for(int i = 0; i < 4; i++){
            utilityCell[i].setText(matrixTable[i]);
        }

    }

    // opponent random action's choosing
    private void opponentRandomAction(){
        opponentActionIsChoosed = true;
        int randomNumber = new Random().nextInt((2 - 1) + 1) +1;
        if (randomNumber == 1){
            aActionBtn.performClick();
            opponentAction = "A";
        }else if (randomNumber == 2){
            bActionBtn.performClick();
            opponentAction = "B";
        }

    }

    //Initialize all widget of activity
    private void initWidgets(){
        // Initialisation of the cells of table game
        utilityAA = findViewById(R.id.utilityAA);
        utilityAB = findViewById(R.id.utilityAB);
        utilityBA = findViewById(R.id.utilityBA);
        utilityBB = findViewById(R.id.utilityBB);

        //All action's Button initialisation
        aActionBtn = findViewById(R.id.actionABtn);
        bActionBtn = findViewById(R.id.actionBBtn);

        myChoosedAction = findViewById(R.id.myAction);
        opponentChoosedAction = findViewById(R.id.opponentAction);

        myPoint = findViewById(R.id.myPoint);
        opponentPoint = findViewById(R.id.opponentPoint);

        dismissButton = findViewById(R.id.dismissBtn);

    }
}