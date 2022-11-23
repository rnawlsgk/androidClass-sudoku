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

        CustomButton buttons[][] = new CustomButton[9][9];

        TableRow.LayoutParams tableRowLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
        TableLayout.LayoutParams tableLayoutLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, 0, 1.0f);

        tableRowLayoutParams.setMargins(12, 12,12,12);

        BoardGenerator boardGenerator = new BoardGenerator();

        for (int i=0;i<9;i++)   {
            TableRow tableRow = new TableRow(this);
            tableRow.setBackgroundColor(Color.DKGRAY);

            if (i == 2 || i == 5) {
                tableRow.setPadding(0, 0, 0, 15);
            } else {
                tableRow.setPadding(0, 0, 0, 0);
            }

            for (int j=0;j<9;j++){
                buttons[i][j] = new CustomButton(this, i, j);

                int number = boardGenerator.get(i, j);
                double rand = Math.random() * 10;
                if (rand < 6) {
                    buttons[i][j].set(number);
                }
                tableRow.addView(buttons[i][j], tableRowLayoutParams);
            }
            tableLayout.addView(tableRow, tableLayoutLayoutParams);
        }

        //==================number Pad=====================


    }

}