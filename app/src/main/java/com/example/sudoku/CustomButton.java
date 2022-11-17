package com.example.sudoku;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomButton extends FrameLayout {

    int row;
    int col;
    int value;
    private TextView textView;

    public CustomButton(Context context, int row, int col) {
        super(context);

        row = this.row;
        col = this.col;

        textView = new TextView(context);

        textView.setTextSize(20);
        textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);

        addView(textView);

        setClickable(true);
        setBackgroundResource(R.drawable.button_selector);
    }

    public void set(int a) {
        String stringValue = Integer.toString(a);
        textView.setText(stringValue);
    }
}
