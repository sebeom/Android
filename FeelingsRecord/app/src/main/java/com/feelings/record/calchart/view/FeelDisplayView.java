package com.feelings.record.calchart.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.feelings.record.R;

public class FeelDisplayView extends LinearLayout {

    TextView text;
    TextView title;
    LinearLayout chartBar;

    public FeelDisplayView(Context context) {
        super(context);
        init();
    }

    public FeelDisplayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        getAttrs(attrs);
    }

    public FeelDisplayView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs);
        init();
        getAttrs(attrs, defStyle);
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.feelchart_view, this, false);
        text = v.findViewById(R.id.chartText);
        chartBar = v.findViewById(R.id.chartBar);
        title = v.findViewById(R.id.chartTitle);
        addView(v);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FeelDisplayView);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int def) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FeelDisplayView, def, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {
        String str = typedArray.getString(R.styleable.FeelDisplayView_chartTitle);
        title.setText(str);

        int color = typedArray.getColor(R.styleable.FeelDisplayView_chartColor, 0);
        text.setTextColor(color);
        title.setTextColor(color);
        chartBar.setBackgroundColor(color);

        typedArray.recycle();

    }

    public void setFeelText(String msg) {
        text.setText(msg);
    }
}
