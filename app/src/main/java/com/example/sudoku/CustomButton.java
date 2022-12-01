package com.example.sudoku;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomButton extends FrameLayout {

    int jcol;
    int irow;
    int value;
    private TextView textView;

    public CustomButton(@NonNull Context context) {
        super(context);
    }

    public CustomButton(Context context, int row, int col) {
        super(context);

        jcol = col;
        irow = row;

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
        String stringValue = textView.getText().toString();
        value = Integer.parseInt(stringValue);
        return value;
    }

    public void set(int a) {
        String stringValue = Integer.toString(a);
        textView.setText(stringValue);

        this.value = a;

        if (a == 0) {
            textView.setVisibility(INVISIBLE);
        } else {
            textView.setVisibility(VISIBLE);
        }
    }

    public void setConflick() {
        setBackgroundColor(Color.RED);
    }

    public void setUnConflict() {
        setBackgroundColor(Color.WHITE);
    }
}
