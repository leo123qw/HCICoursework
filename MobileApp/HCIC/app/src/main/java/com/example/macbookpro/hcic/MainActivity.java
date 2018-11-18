package com.example.macbookpro.hcic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences shPreferences;
    private Editor editor;
    private long bootStartTime;
    private boolean flag =false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bootStartTime = System.currentTimeMillis();
        TextView tv=(TextView) findViewById(R.id.text);
        shPreferences = getApplicationContext().getSharedPreferences("boocount", Context.MODE_PRIVATE);
        editor =shPreferences.edit();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(!shPreferences.getString("todaytime","").equals(format.format(System.currentTimeMillis()))){
            flag = true; // a new day
            editor.putString("todaytime", format.format(System.currentTimeMillis()));
        }
        if (shPreferences.getInt("bootcountnum", 0)>= 1){
            int x =shPreferences.getInt("bootcountnum", 0);
            tv.setText("open times" + x);
            editor.putInt("bootcountnum", x+1);
        }else{
            flag =false;
            editor.putInt("bootcountnum",1);
        }
        editor.putLong("bootStartTime",bootStartTime);
        editor.commit();
        tv.setText("opening times: "+shPreferences.getInt("bootcountnum", 0) );
        /******************************       button         ************************/
        Button draw =(Button)findViewById(R.id.button2);
        draw.setOnClickListener(listener);
    }
     private View.OnClickListener listener =new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent =new Intent();
             intent.setClass(MainActivity.this,draw.class);
             startActivity(intent);
         }
     };


}
