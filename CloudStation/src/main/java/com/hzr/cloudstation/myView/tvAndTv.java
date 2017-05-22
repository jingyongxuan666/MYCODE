package com.hzr.cloudstation.myView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzr.cloudstation.R;

/**
 * Created by 静永萱 on 2017/5/11.
 */
public class tvAndTv extends LinearLayout{
    private TextView tv_itemName;
    private TextView tv_itemContext;
    public tvAndTv(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myView = inflater.inflate(R.layout.textview_and_textview,this);
        tv_itemName = (TextView) myView.findViewById(R.id.tv_tv1);
        tv_itemContext = (TextView) myView.findViewById(R.id.tv_tv2);

    }
    public void setTv1Text(CharSequence context){
        tv_itemName.setText(context);
    }
    public void setTv2Text(String context){
        tv_itemContext.setText(context);
    }
}
