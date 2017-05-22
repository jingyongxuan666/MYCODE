package com.hzr.cloudstation.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hzr.cloudstation.AddExpressActivity;
import com.hzr.cloudstation.ManageExpressActivity;
import com.hzr.cloudstation.MyExpressActivity;
import com.hzr.cloudstation.R;
import com.hzr.cloudstation.entities.User;

/**
 * Created by hzr on 2017/4/10.
 */
public class ExpressFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Button btn_myExpress;
    private Button btn_addExpress;
    private Button btn_manageExpress;

    private User user;
    public ExpressFragment() {
        super();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        if (!user.getUserName().equals("admin")){
            btn_manageExpress.setVisibility(View.GONE);
        }
        btn_myExpress.setOnClickListener(this);
        btn_addExpress.setOnClickListener(this);
        btn_manageExpress.setOnClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view != null){
            container = (ViewGroup) view.getParent();
            if (container != null){
                container.removeView(view);
            }
        }else{
            view = inflater.inflate(R.layout.fragment_express,null);
        }
        return view;
    }
    private void initView(){
        btn_addExpress = (Button) view.findViewById(R.id.btn_add_express);
        btn_manageExpress = (Button) view.findViewById(R.id.btn_manage_express);
        btn_myExpress = (Button) view.findViewById(R.id.btn_my_express);
    }
    private void initData(){
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        user = (User) bundle.getSerializable("user");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_express:
                intentToOther(getActivity(), AddExpressActivity.class);
                break;
            case R.id.btn_my_express:
                intentToOther(getActivity(), MyExpressActivity.class);
                break;
            case R.id.btn_manage_express:
                intentToOther(getActivity(), ManageExpressActivity.class);
                break;
        }
    }
    private void intentToOther(Context context, Class<?> class1){
        Intent intent = new Intent(context,class1);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",user);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
