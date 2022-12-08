package com.example.sudoku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    CustomButton[][] buttons = new CustomButton[9][9];
    CustomButton clickedCustomButton;

    BoardGenerator boardGenerator;

    //numberPad
    FrameLayout frameLayout;
    FrameLayout.LayoutParams numberPadFrameLayoutLayoutParams;

    TableLayout tableLayout, numberPadTableLayout;
    TableLayout.LayoutParams tableLayoutLayoutParams;
    TableRow.LayoutParams tableRowLayoutParams;
    TableRow.LayoutParams numPadTableRowLayoutParams;

    TextView numberPadTextView;

    //Memo Implementation
    LayoutInflater memoDialogLayoutInflater;
    LinearLayout memoDialogLinearLayout;
    Button btnMemoCancel, btnMemoDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        numberPadTableLayout = new TableLayout(this);

        tableLayoutLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, 0, 1.0f);
        tableRowLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
        tableRowLayoutParams.setMargins(12, 12, 12, 12);

        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        //Memo Implementation
        memoDialogLayoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        memoDialogLayoutInflater.inflate(R.layout.dialog_memo, frameLayout, true);
        memoDialogLinearLayout = (LinearLayout) findViewById(R.id.memoDialogLinearLayout);
        memoDialogLinearLayout.setVisibility(View.INVISIBLE);

        //board
        boardGenerator = new BoardGenerator();

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
                final int clickedRow = i;
                final int clickedCol = j;
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                        public void onClick(View view) {
                            clickedCustomButton = buttons[clickedRow][clickedCol];
                            numberPadTableLayout.setVisibility(View.VISIBLE);
                    }
                });

                //Memo implementation
                buttons[i][j].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        clickedCustomButton = buttons[clickedRow][clickedCol];
                        memoDialogLinearLayout.setVisibility(View.VISIBLE);
                        return true;
                    }
                });

            }
            tableLayout.addView(tableRow, tableLayoutLayoutParams);
        }

        btnMemoCancel = (Button) findViewById(R.id.memoBtnCancel);
        btnMemoCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memoDialogLinearLayout.setVisibility(View.INVISIBLE);
            }
        });
        btnMemoDelete = (Button) findViewById(R.id.memoBtnDelete);
        btnMemoDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedCustomButton.deleteMemo();
                memoDialogLinearLayout.setVisibility(View.INVISIBLE);
            }
        });

        //==================number Pad=====================


        numberPadFrameLayoutLayoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        numberPadFrameLayoutLayoutParams.gravity = Gravity.CENTER;

        numberPadTableLayout.setLayoutParams(numberPadFrameLayoutLayoutParams);
        numberPadTableLayout.setBackgroundColor(Color.WHITE);
        numberPadTableLayout.setPadding(10, 10, 10, 10);

        numberPadTextView = new TextView(this);
        numberPadTextView.setText("Input Number");
        numberPadTextView.setGravity(Gravity.CENTER);
        numberPadTextView.setTextSize(20);
        numberPadTableLayout.addView(numberPadTextView);

        numPadTableRowLayoutParams = new TableRow.LayoutParams(
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
                String numberPadValueString = (String) numberPadButtons.getText();
                final int numberPadValueInt = Integer.parseInt(numberPadValueString);

                numberPadButtons.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickedCustomButton.set(numberPadValueInt);
                        numberPadTableLayout.setVisibility(View.INVISIBLE);
//                        checkRules(clickedCustomButton);
                    }
                });

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
                            numberPadButtons.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    clickedCustomButton.set(0);
                                    checkRules(clickedCustomButton);
                                    numberPadTableLayout.setVisibility(View.INVISIBLE);
                                }
                            });
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
        }
        frameLayout.addView(numberPadTableLayout);
        numberPadTableLayout.setVisibility(View.INVISIBLE);
    }

    //=================Game Rules Implementation=================
    public void checkRules(@NonNull CustomButton checkButton) {
        int checkButtonValue = checkButton.getValue();
        int checkButtonCol = checkButton.getCol();
        int checkButtonRow = checkButton.getRow();
    }

    public void onToggleClick(View v) {

    }
}