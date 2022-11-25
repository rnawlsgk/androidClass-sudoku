package com.example.sudoku;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomButton extends FrameLayout {

    int irow;
    int jcol;
    int value;
    private TextView textView;

    public CustomButton(@NonNull Context context) {
        super(context);
    }

    public CustomButton(Context context, int row, int col) {
        super(context);

        irow = row;
        jcol = col;

        textView = new TextView(context);

        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);

        addView(textView);

        setClickable(true);
        setBackgroundResource(R.drawable.button_selector);
    }

    public int getIrow() {
        return irow;
    }

    public int getJcol() {
        return jcol;
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
        }
    }
}
