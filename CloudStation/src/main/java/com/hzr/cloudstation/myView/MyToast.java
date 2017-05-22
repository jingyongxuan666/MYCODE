package com.hzr.cloudstation.myView;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzr.cloudstation.R;

/**
 * Created by hzr on 2017/4/7.
 * 自定义toast
 */
public class MyToast {
    private static Toast toast;
    private static TextView tv_toast;
    private static ImageView iv_warn;

    public static void MyToast(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_toast, null);
        tv_toast = (TextView) view.findViewById(R.id.tv_toast);
        iv_warn = (ImageView) view.findViewById(R.id.iv_warn);
        if (toast == null) {
            toast = new Toast(context);
            tv_toast.setText(msg);
        } else {
            tv_toast.setText(msg);
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.setView(view);
        if (!msg.equals("登录成功!") && !msg.equals("注册成功，请登录!")
                && !msg.equals("更新资料成功") && !msg.equals("添加成功")
                && !msg.equals("删除成功") && !msg.equals("编辑成功")
                && !msg.equals("添加成功") && !msg.equals("更新成功")) {
            iv_warn.setImageResource(android.R.drawable.stat_notify_error);
        } else {
            iv_warn.setVisibility(View.INVISIBLE);
        }
        toast.show();
    }
}
