package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
//        TableLayout numberPadTableLayout = new TableLayout(this);
        TableLayout numberPadTableLayout = findViewById(R.id.numberPadTableLayout);

        CustomButton buttons[][] = new CustomButton[9][9];

        TableLayout.LayoutParams tableLayoutLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, 0, 1.0f);
        TableRow.LayoutParams tableRowLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
        tableRowLayoutParams.setMargins(12, 12, 12, 12);

        //borad
        BoardGenerator boardGenerator = new BoardGenerator();

        for (int i = 0; i < 9; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setBackgroundColor(Color.DKGRAY);

            if (i == 2 || i == 5) {
                tableRow.setPadding(0, 0, 0, 15);
            } else {
                tableRow.setPadding(0, 0, 0, 0);
            }

            for (int j = 0; j < 9; j++) {
                buttons[i][j] = new CustomButton(this, i, j);

                int boardNumber = boardGenerator.get(i, j);
                buttons[i][j].set(boardNumber);

                double rand = Math.random() * 10;
                if (rand < 4) {
                    buttons[i][j].set(0);
                }
                tableRow.addView(buttons[i][j], tableRowLayoutParams);

                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        numberPadTableLayout.setVisibility(View.VISIBLE);

                    }
                });
            }
            tableLayout.addView(tableRow, tableLayoutLayoutParams);
        }

        //==================number Pad=====================
        FrameLayout numberPadFrameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        Button btn_cancel = findViewById(R.id.btn_cancel);

        /*FrameLayout.LayoutParams numberPadFrameLayoutLayoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        numberPadFrameLayoutLayoutParams.gravity = Gravity.CENTER;

        numberPadTableLayout.setLayoutParams(numberPadFrameLayoutLayoutParams);
        numberPadTableLayout.setBackgroundColor(Color.WHITE);
        numberPadTableLayout.setPadding(10, 10, 10, 10);

        TextView numberPadTextView = new TextView(this);
        numberPadTextView.setText("Input Number");
        numberPadTextView.setGravity(Gravity.CENTER);
        numberPadTextView.setTextSize(20);
        numberPadTableLayout.addView(numberPadTextView);

        TableRow.LayoutParams numPadTableRowLayoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1.0f
        );

        int number = 1;
        for (int i = 0; i < 4; i++) {
            TableRow numberPadTableRow = new TableRow(this);
            for (int j = 0; j < 3; j++) {
                Button numberPadButtons = new Button(this);

                String numberSet = Integer.toString(number);
                numberPadButtons.setText(numberSet);
                number++;

                if (i==3) {
                    switch (j) {
                        case 0:
                            numberPadButtons.setVisibility(View.INVISIBLE);
                            break;
                        case 1:
                            numberPadButtons.setText("CANCEL");
                            numberPadButtons.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    numberPadTableLayout.setVisibility(View.INVISIBLE);
                                }
                            });
                            break;
                        case 2:
                            numberPadButtons.setText("DEL");
                            break;
                        default:
                            break;
                    }
                }
                numberPadButtons.setLayoutParams(numPadTableRowLayoutParams);
                numberPadTableRow.addView(numberPadButtons);
            }
            numberPadTableRow.setLayoutParams(tableLayoutLayoutParams);
            numberPadTableLayout.addView(numberPadTableRow);
        }*/

//        numberPadFrameLayout.addView(numberPadTableLayout);
        numberPadTableLayout.setVisibility(View.INVISIBLE);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPadTableLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

}