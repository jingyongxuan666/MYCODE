package com.hzr.cloudstation.myView;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.hzr.cloudstation.R;


/**
 * Created by hzr on 2017/3/28.
 * 自定义的带清空按钮的edittext
 */
public class ClearEditText extends LinearLayout{
    private EditText et_myEditText;
    private ImageButton ib_clear;
    private String hintText;
    private int typeOfInput;
    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray type = context.obtainStyledAttributes(attrs, R.styleable.ClearEditText);
        hintText = type.getString(R.styleable.ClearEditText_myHint);
        typeOfInput = type.getInt(R.styleable.ClearEditText_myInputType,2);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myView = inflater.inflate(R.layout.clear_edittext,this);
        et_myEditText = (EditText) myView.findViewById(R.id.et_myEditText);
        ib_clear = (ImageButton) myView.findViewById(R.id.ib_clear);
        ib_clear.setVisibility(GONE);
        //设置输入类型
        whichType(typeOfInput);
        et_myEditText.setHint(hintText);
        et_myEditText.addTextChangedListener(watcher);
        ib_clear.setOnClickListener(clickThis);

    }

    private void whichType(int type) {
        switch (type){
            case 1:
                et_myEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
        }
    }


    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0){
                ib_clear.setVisibility(VISIBLE);
            }else{
                ib_clear.setVisibility(GONE);
            }
        }
    };

    OnClickListener clickThis = new OnClickListener() {
        @Override
        public void onClick(View v) {

            et_myEditText.setText("");
            ib_clear.setVisibility(GONE);
        }
    };

    public ClearEditText(Context context) {
        super(context);
    }

    public String getText(){
        return et_myEditText.getText().toString();
    }
    public void addTextChangedListener(TextWatcher watcher){
        et_myEditText.addTextChangedListener(watcher);
    }
    public void setText(String text){
        et_myEditText.setText(text);
    }
    public void setMaxLines(int lines){
        et_myEditText.setMaxLines(lines);
    }
    public void setSelection(int length){
        et_myEditText.setSelection(length);
    }
}
