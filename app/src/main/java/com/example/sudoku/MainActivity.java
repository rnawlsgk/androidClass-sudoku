package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    CustomButton[][] buttons = new CustomButton[9][9];
    CustomButton clickedCustomButton;

    BoardGenerator boardGenerator;

    TableLayout.LayoutParams tableLayoutLayoutParams;
    TableRow.LayoutParams tableRowLayoutParams;

    //numberPad
    FrameLayout frameLayout;
    FrameLayout.LayoutParams numberPadFrameLayoutLayoutParams;
    TableLayout tableLayout, numberPadTableLayout;
    TableRow.LayoutParams numPadTableRowLayoutParams;
    TextView numberPadTextView;

    //Game Rule Implementation
    class Dir {
        int n, m;
        public Dir(int n, int m) {
           this.n = n;
           this.m = m;
        }
    }

    //Memo Implementation
    LayoutInflater memoDialogLayoutInflater;
    LinearLayout memoDialogLinearLayout;
    TableLayout memoTableLayout;
    Button btnMemoCancel, btnMemoDelete, btnMemoOk;
    boolean[] checkedMemoNumber = new boolean[9];
    ToggleButton[] memoToggleButton = new ToggleButton[9];

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
    //=====================Memo Implementation=====================
        memoDialogLayoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        memoDialogLayoutInflater.inflate(R.layout.dialog_memo, frameLayout, true);
        memoDialogLinearLayout = (LinearLayout) findViewById(R.id.memoDialogLinearLayout);
        memoDialogLinearLayout.setVisibility(View.INVISIBLE);

    //=====================board=====================
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
                            memoDialogLinearLayout.setVisibility(View.INVISIBLE);
                    }
                });

    //=====================Memo implementation=====================
                buttons[i][j].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        clickedCustomButton = buttons[clickedRow][clickedCol];
                        memoDialogLinearLayout.setVisibility(View.VISIBLE);
                        numberPadTableLayout.setVisibility(View.INVISIBLE);
                        return true;
                    }
                });
            }
            tableLayout.addView(tableRow, tableLayoutLayoutParams);
        }

    //=====================Memo implementation=====================
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

        memoTableLayout = (TableLayout) findViewById(R.id.memoTableLayout);
        int k=0;
        for (int i=0;i<3;i++) {
            TableRow memoTableRow = (TableRow) memoTableLayout.getChildAt(i);
            for (int j=0;j<3;j++, k++) {
                final int checked = k;
                memoToggleButton[k] = (ToggleButton) memoTableRow.getChildAt(j);
                memoToggleButton[k].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        checkedMemoNumber[checked] = isChecked;
                    }
                });
            }
        }

        btnMemoOk = (Button) findViewById(R.id.memoBtnOk);
        btnMemoOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0;i<9;i++) {
                    clickedCustomButton.okMemo(checkedMemoNumber);
                    memoDialogLinearLayout.setVisibility(View.INVISIBLE);
                }
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

                        //gameRule
                        verticalCompare();
                        horizontalCompare();
                        boxCompareAll();
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
                                    numberPadTableLayout.setVisibility(View.INVISIBLE);
                                    //gameRule
                                    verticalCompare();
                                    horizontalCompare();
                                    boxCompareAll();
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
    private void boxCompareAll() {
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                boxCompare(i, j);
            }
        }
        //이게 마지막 체크라서 체크 후 isRed 초기화
        for (int n = 0; n < 9; n++) {
            for (int m = 0; m < 9; m++) {
                buttons[n][m].setIsConflict(false);
            }
        }
    }

    private void horizontalCompare() {
        for (int i = 0; i < 9; i++) {
            Map<Integer, Dir> map = new HashMap<>();
            for (int j = 0; j < 9; j++) {
                Integer value = buttons[j][i].getValue();
                if (map.containsKey(value) && value != 0) {
                    Dir dir = map.get(value);
                    buttons[dir.n][dir.m].setConflict(true);
                    buttons[j][i].setConflict(true);
                } else {
                    map.put(value, new Dir(j, i));
                    if (!buttons[j][i].isConflict()) {
                        buttons[j][i].setBackgroundColor(Color.WHITE);
                    }
                }
            }
        }
    }

    private void verticalCompare() {
        for (int i = 0; i < 9; i++) {
            Map<Integer, Dir> map = new HashMap<>();
            for (int j = 0; j < 9; j++) {
                Integer value = buttons[i][j].getValue();
                if (map.containsKey(value) && value != 0) {
                    Dir dir = map.get(value);
                    buttons[dir.n][dir.m].setConflict(true);
                    buttons[i][j].setConflict(true);
                } else {
                    map.put(value, new Dir(i, j));
                    buttons[i][j].setBackgroundColor(Color.WHITE);
                }
            }
        }
    }

    private void boxCompare(int ni, int mj) {
        Map<Integer, Dir> map = new HashMap<>();
        for (int i = ni; i < ni + 3; i++) {
            for (int j = mj; j < mj + 3; j++) {
                Integer value = buttons[i][j].getValue();
                if (map.containsKey(value) && value != 0) {
                    Dir dir = map.get(value);
                    buttons[dir.n][dir.m].setBackgroundColor(Color.RED);
                    buttons[i][j].setBackgroundColor(Color.RED);
                } else {
                    map.put(value, new Dir(i, j));
                    if (!buttons[i][j].isConflict) {
                        buttons[i][j].setBackgroundColor(Color.WHITE);
                    }
                }
            }
        }
    }

}