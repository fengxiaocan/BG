package com.evil.bg;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity
        extends AppCompatActivity
        implements View.OnClickListener
{

    private BGView          mBgView;
    private Button          mBt;
    private SensorManager   mSensorManager;// 传感器管理对象
    private Sensor          mOrientationSensor;// 传感器对象
    private LocationManager mLocationManager;// 位置管理对象
    private String          mLocationProvider;// 位置提供者名称，GPS设备还是网络
    private float           mTargetDirection;// 目标浮点方向
    private TextView        mTvNowDate;
    private TextView        mTvOldDate;
    private Button          select;
    private EditText        year;
    private EditText        month;
    private EditText        day;
    private Random mRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化传感器
        initServices();
        //初始化控件
        initView();
        initData();
        mRandom = new Random();
        mBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(3000);
                        int i = mRandom.nextInt(10);

                    }
                }).start();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        String time = TimeUtils.formatTime(System.currentTimeMillis(), TimeUtils.DATE_TYPE6);
        mTvNowDate.setText(time);
        int    nowYear  = TimeUtils.getNowYear();
        int    nowMonth = TimeUtils.getNowMonth();
        int    nowDate  = TimeUtils.getNowDate();
        String lunar    = Lauar.getLunar(nowYear + "", nowMonth + "", nowDate + "");
        mTvOldDate.setText(lunar);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mBgView = (BGView) findViewById(R.id.bgview);
        mBt = (Button) findViewById(R.id.bt);
        mTvNowDate = (TextView) findViewById(R.id.tv_now_date);
        mTvOldDate = (TextView) findViewById(R.id.tv_old_date);
        select = (Button) findViewById(R.id.select);
        select.setOnClickListener(this);
    }

    /**
     * 在恢复的生命周期里判断、启动位置更新服务和传感器服务
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (mLocationProvider != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocationManager.requestLocationUpdates(mLocationProvider, 2000, 10, mLocationListener);// 2秒或者距离变化10米时更新一次地理位置
        } else {
            Toast.makeText(this, "不能获取位置信息", Toast.LENGTH_SHORT)
                 .show();
        }
        if (mOrientationSensor != null) {
            mSensorManager.registerListener(mOrientationSensorEventListener, mOrientationSensor, SensorManager.SENSOR_DELAY_GAME);
        } else {
            Toast.makeText(this, "不能获取传感器", Toast.LENGTH_SHORT)
                 .show();
        }
    }

    /**
     * 在暂停的生命周期里注销传感器服务和位置更新服务
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (mOrientationSensor != null) {
            mSensorManager.unregisterListener(mOrientationSensorEventListener);
        }
        if (mLocationProvider != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocationManager.removeUpdates(mLocationListener);
        }
    }

    // 初始化传感器和位置服务
    private void initServices() {
        // sensor manager
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mOrientationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        // location manager
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();// 条件对象，即指定条件过滤获得LocationProvider
        criteria.setAccuracy(Criteria.ACCURACY_FINE);// 较高精度
        criteria.setAltitudeRequired(true);// 是否需要高度信息
        criteria.setBearingRequired(false);// 是否需要方向信息
        criteria.setCostAllowed(true);// 是否产生费用
        criteria.setPowerRequirement(Criteria.POWER_LOW);// 设置电耗最高的
        mLocationProvider = mLocationManager.getBestProvider(criteria, true);// 获取条件最好的Provider

    }

    // 把经纬度转换成度分秒显示
    private String getLocationString(double input) {
        int du   = (int) input;
        int fen  = (((int) ((input - du) * 3600))) / 60;
        int miao = (((int) ((input - du) * 3600))) % 60;
        return String.valueOf(du) + "°" + String.valueOf(fen) + "′" + String.valueOf(miao) + "″";
    }

    // 方向传感器变化监听
    private SensorEventListener mOrientationSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float direction = event.values[0] * -1.0f;
            mTargetDirection = normalizeDegree(direction);// 赋值给全局变量，让指南针旋转
            mBgView.setDirectionFix(mTargetDirection);
        }

        //方向发生改变
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    // 调整方向传感器获取的值
    private float normalizeDegree(float degree) {
        return (degree + 720) % 360;
    }

    // 位置信息更新监听
    LocationListener mLocationListener = new LocationListener() {
        // Provider的在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (status != LocationProvider.OUT_OF_SERVICE) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            } else {
                Toast.makeText(MainActivity.this, "不能获取位置信息", Toast.LENGTH_SHORT)
                     .show();
            }
        }

        //  Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select:
                //选择生日
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View inflate = View.inflate(this, R.layout.time_layout, null);
                builder.setView(inflate);

                year = (EditText) inflate.findViewById(R.id.year);
                month = (EditText) inflate.findViewById(R.id.month);
                day = (EditText) inflate.findViewById(R.id.day);

                builder.setTitle("请设置生日");
                builder.setNeutralButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String submit = submit();
                        Toast.makeText(MainActivity.this, submit, Toast.LENGTH_SHORT)
                             .show();
                    }
                });
                builder.setCancelable(false);
                builder.show();
                break;
        }
    }

    private String submit() {
        // validate
        String yearString = year.getText()
                                .toString()
                                .trim();
        if (TextUtils.isEmpty(yearString)) {
            Toast.makeText(this, "请输入年份", Toast.LENGTH_SHORT)
                 .show();
            return yearString;
        }

        String monthString = month.getText()
                                  .toString()
                                  .trim();
        if (TextUtils.isEmpty(monthString)) {
            Toast.makeText(this, "请输入月份", Toast.LENGTH_SHORT)
                 .show();
            return yearString;
        }

        String dayString = day.getText()
                              .toString()
                              .trim();
        if (TextUtils.isEmpty(dayString)) {
            Toast.makeText(this, "请输入日", Toast.LENGTH_SHORT)
                 .show();
            return yearString;
        }

        // TODO validate success, do something
        if (Integer.valueOf(monthString) < 1 || Integer.valueOf(monthString) > 12){
            Toast.makeText(MainActivity.this, "月份不合法", Toast.LENGTH_SHORT)
                 .show();
            return yearString;
        }
        if (Integer.valueOf(dayString) < 1 || Integer.valueOf(dayString) > 31){
            Toast.makeText(MainActivity.this, "日期不合法", Toast.LENGTH_SHORT)
                 .show();
            return yearString;
        }

        return yearString + monthString + dayString;
    }
}
