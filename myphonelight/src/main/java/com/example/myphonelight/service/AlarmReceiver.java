package com.example.myphonelight.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by  on 2017/3/15.
 */
public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        int longOfTime = intent.getIntExtra("longOfTime",0);
        //Toast.makeText(context,"long:"+longOfTime,Toast.LENGTH_SHORT).show();
        final android.hardware.Camera camera = android.hardware.Camera.open();
        android.hardware.Camera.Parameters ps = camera.getParameters();
        ps.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(ps);
        camera.startPreview();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                camera.stopPreview();
                camera.release();
            }
        }, longOfTime*60*1000);
    }
}
