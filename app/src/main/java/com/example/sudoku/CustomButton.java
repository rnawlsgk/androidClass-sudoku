package com.example.sudoku;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class CustomButton extends FrameLayout {

    int col;
    int row;
    int value;
    boolean isConflict = false;

    private TextView textView;
    LayoutInflater memoLayoutInflater;
    TextView[] memoTextView = new TextView[9];
    TableLayout memoTableLayout;

    public CustomButton(@NonNull Context context) {
        super(context);
    }

    public CustomButton(Context context, int row, int col) {
        super(context);

        this.col = col;
        this.row = row;

        textView = new TextView(context);

        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);

        addView(textView);

        setClickable(true);
        setBackgroundResource(R.drawable.button_selector);

        //Memo
        memoLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        memoTableLayout = (TableLayout) memoLayoutInflater.inflate(R.layout.layout_memo, null);
        addView(memoTableLayout);

        int k=0;
        for (int i=0;i<3;i++) {
            TableRow memoTableRow = (TableRow) memoTableLayout.getChildAt(i);
            for (int j=0;j<3;j++, k++) {
                memoTextView[k] = (TextView) memoTableRow.getChildAt(j);
                memoTextView[k].setVisibility(INVISIBLE);
            }
        }

    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getValue() {
        return value;
    }

    public void set(int a) {
        String stringValue = Integer.toString(a);
        textView.setText(stringValue);

        this.value = a;

        if (a == 0) {
            textView.setVisibility(INVISIBLE);
            setBackgroundColor(Color.WHITE);
        } else {
            textView.setVisibility(VISIBLE);
        }
    }

    //game rule
    public void setIsConflict(boolean conflict) {
        isConflict = conflict;
    }

    public boolean isConflict() {
        return isConflict;
    }

    public void setConflict(boolean conflict) {
        setBackgroundColor(Color.RED);
        isConflict = conflict;
    }

    //memo
    public void deleteMemo() {
        for (int i=0;i<9;i++) {
                memoTextView[i].setVisibility(INVISIBLE);
        }
    }

    public void okMemo(boolean[] setMemo) {
        for (int i=0;i<9;i++) {
            if (setMemo[i]) {
                memoTextView[i].setVisibility(VISIBLE);
            } else {
                memoTextView[i].setVisibility(INVISIBLE);
            }
        }
    }
}
