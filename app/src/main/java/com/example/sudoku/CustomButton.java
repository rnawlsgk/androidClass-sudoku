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

public class CustomButton extends FrameLayout {

    int col;
    int row;
    int value;

    private TextView textView, b;
    LayoutInflater memoLayoutInflater;

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
        TableLayout memoTableLayout = (TableLayout) memoLayoutInflater.inflate(R.layout.layout_memo, null);
        addView(memoTableLayout);
//        memoTableLayout.setVisibility(INVISIBLE);
//        이거 왜 숫자 몇개만 골라서 보여주는거 안도이???
//        b = (TextView) memoTableLayout.findViewById(R.id.textViewMemo2);
//        b.setVisibility(VISIBLE);
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
}
