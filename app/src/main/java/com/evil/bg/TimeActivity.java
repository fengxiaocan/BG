package com.evil.bg;

import android.app.Activity;
import android.os.Bundle;

/*
 *  @项目名：  BG 
 *  @包名：    com.evil.bg
 *  @文件名:   TimeActivity
 *  @创建者:   冯小灿
 *  @创建时间:  2016/8/25 18:28
 *  @描述：    时间选择
 */
public class TimeActivity extends Activity {
    private static final String TAG = "TimeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.time_layout);
    }
}
