package com.example.myphonelight;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myphonelight.service.AlarmReceiver;
import com.example.myphonelight.utils.UtilsOfSharePreferences;

import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private android.hardware.Camera camera;
    private Button btn_test;
    private Button btn_timing;
    private TextView tv_timemsg;
    private boolean isLightOn = false;
    private boolean shortPress = false;
    private SensorManager sensorManager = null;//传感器（传感器）
    private Vibrator vibrator = null;//加速度传感器
    private Switch sh_shake;
    private Switch sh_light;
    private Calendar c;
    private int myHour;
    private int myMin;
    private int ADAY = 24*60*60*1000;
    private int longOfTime = -1;
    private boolean Timed = false;

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            shortPress = false;
//            Toast.makeText(this, "longPress", Toast.LENGTH_LONG).show();
            turnLightOn();
            return true;
        }
        //Just return false because the super call does always the same (returning false)
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                event.startTracking();
                if (event.getRepeatCount() == 0) {
                    shortPress = true;
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            if (shortPress) {
                //Toast.makeText(this, "shortPress", Toast.LENGTH_LONG).show();
            } else {
                //Don't handle longpress here, because the user will have to get his finger back up first
                turnLightOff();
            }
            shortPress = false;
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_timing = (Button) findViewById(R.id.btn_timing);
        btn_test = (Button) findViewById(R.id.btn_test);
        tv_timemsg = (TextView) findViewById(R.id.tv_timemsg);

        btn_test.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //Toast.makeText(MainActivity.this, "按下", Toast.LENGTH_LONG).show();
                        turnLightOn();
                        break;
                    case MotionEvent.ACTION_UP:
                        //Toast.makeText(MainActivity.this, "抬起", Toast.LENGTH_LONG).show();
                        turnLightOff();
                        break;
                }
                return false;
            }
        });
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        sh_shake = (Switch) findViewById(R.id.sh_shake);
        sh_light = (Switch) findViewById(R.id.sh_light);
//        sh_light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(!sh_light.isChecked()){
//                    if (isLightOn){
//                        turnLightOff();
//                    }
//                }
//            }
//        });
//        sh_shake.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (!sh_shake.isChecked()) {
//                    if (isLightOn) {
//                        turnLightOff();
//                    }
//                }
//            }
//        });
        btn_timing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Timed){
                    c = Calendar.getInstance();
                    c.setTimeInMillis(System.currentTimeMillis());
                    c.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                    myHour = c.get(Calendar.HOUR_OF_DAY);
                    myMin = c.get(Calendar.MINUTE);

                    new TimePickerDialog(MainActivity.this,android.R.style.Theme_Holo_Light_Dialog,new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                        {
                            myHour = hourOfDay;
                            myMin = minute;
                            showMyDialog(MainActivity.this);

                        }
                    }, myHour, myMin, true).show();
                }else{
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("提示")
                            .setMessage("确定取消定时吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    boolean isClear = UtilsOfSharePreferences.clearTimeInfo(MainActivity.this);
                                    if (isClear){
                                        noTime();
                                        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                                        PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this,
                                                0, intent, 0);

                                        // 取消闹铃
                                        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
                                        am.cancel(sender);
                                    }
                                }
                            }).show();

                }

            }
        });

    }
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this).setTitle("提示")
                .setMessage("确定退出吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("取消",null).show();
        //super.onBackPressed();
    }
    @Override
    protected void onStart() {
        super.onStart();
        //检测是否已定时
        Map<String,String> timeInfo = UtilsOfSharePreferences.getTimeInfo(this);
        if (timeInfo != null){
            timed(timeInfo.get("hour"),timeInfo.get("min"),timeInfo.get("longOfTime"));
        }else{
            noTime();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);// 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();

        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            float[] values = event.values;//values[0]:x轴;values[1]:y轴;values[2]:z轴
            if (sh_shake.isChecked()) {
                if (Math.abs(values[0]) > 18 || Math.abs(values[1]) > 18 || Math.abs(values[2]) > 18) {
                    vibrator.vibrate(200);
                    if (!isLightOn) {
                        turnLightOn();
                    } else {
                        turnLightOff();
                    }
                }
            }
        } else if (sensorType == Sensor.TYPE_LIGHT) {
            float value = event.values[0];//values[0]:光强值
            if (sh_light.isChecked()) {
                if (value < 0.01f){
                    turnLightOn();
                }else{
                    turnLightOff();
                }
                //Toast.makeText(MainActivity.this, "光强："+value, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void turnLightOn() {
        if (!isLightOn) {
            camera = android.hardware.Camera.open();
            android.hardware.Camera.Parameters ps = camera.getParameters();
            ps.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(ps);
            camera.startPreview();
            isLightOn = true;
        } else {
            //Toast.makeText(MainActivity.this, "手电筒正在运行", Toast.LENGTH_SHORT).show();
        }

    }

    private void turnLightOff() {
        if (isLightOn) {
            camera.stopPreview();
            camera.release();
            isLightOn = false;
        } else {
            //Toast.makeText(MainActivity.this, "手电筒是关闭状态", Toast.LENGTH_SHORT).show();
        }

    }
    private void showMyDialog(final Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        final View setTime = inflater.inflate(R.layout.time_set,null);
        final EditText et_time_set = (EditText) setTime.findViewById(R.id.et_time_set);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("请设置手电筒唤醒时长(分钟)");
        builder.setView(setTime);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    longOfTime = Integer.parseInt(et_time_set.getText().toString().trim());
                    //Toast.makeText(getBaseContext(),"定时成功:"+longOfTime,Toast.LENGTH_SHORT).show();
                    if (longOfTime != -1){
                        boolean isSuccess = UtilsOfSharePreferences.saveTimeInfo(MainActivity.this,myHour,myMin,longOfTime);
                        if (isSuccess){
//                            Toast.makeText(getBaseContext(),"定时成功",Toast.LENGTH_SHORT).show();
                            timed(myHour+"",myMin+"",longOfTime+"");
                            Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                            intent.putExtra("longOfTime",longOfTime);
                            PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                            long firstTime = SystemClock.elapsedRealtime();	// 开机之后到现在的运行时间(包括睡眠时间)
                            long systemTime = System.currentTimeMillis();

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(System.currentTimeMillis());
                            calendar.setTimeZone(TimeZone.getTimeZone("GMT+8")); // 这里时区需要设置一下，不然会有8个小时的时间差
                            calendar.set(Calendar.MINUTE, myMin);
                            calendar.set(Calendar.HOUR_OF_DAY, myHour);
                            calendar.set(Calendar.SECOND, 0);
                            calendar.set(Calendar.MILLISECOND, 0);

                            // 选择的每天定时时间
                            long selectTime = calendar.getTimeInMillis();

                            // 如果当前时间大于设置的时间，那么就从第二天的设定时间开始
                            if(systemTime > selectTime) {
                                Toast.makeText(MainActivity.this, "设置的时间小于当前时间,将在明天开启", Toast.LENGTH_SHORT).show();
                                calendar.add(Calendar.DAY_OF_MONTH, 1);
                                selectTime = calendar.getTimeInMillis();
                            }

                            // 计算现在时间到设定时间的时间差
                            long time = selectTime - systemTime;
                            firstTime += time;

                            // 进行闹铃注册
                            AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
                            manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                    firstTime, ADAY, sender);

//                            Log.i(TAG, "time ==== " + time + ", selectTime ===== "
//                                    + selectTime + ", systemTime ==== " + systemTime + ", firstTime === " + firstTime);

                            Toast.makeText(MainActivity.this, "定时成功! ", Toast.LENGTH_LONG).show();


                        }else{
                            Toast.makeText(getBaseContext(),"定时失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context,"输入有误",Toast.LENGTH_SHORT).show();
                }
            }

        });
        builder.show();
    }
    private void timed(String hour,String min,String longOfTime){
        btn_timing.setText("取消定时");
        Timed = true;
        tv_timemsg.setText("手电筒将在"+hour+":"+min+"亮起\n持续"+longOfTime+"分钟");
    }
    private void noTime(){
        btn_timing.setText("定时");
        Timed = false;
        tv_timemsg.setText("");
    }
}
