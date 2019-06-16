package com.example.ruler.noteapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        System.out.println("START THE STARTACTIVITY");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final LinearLayout tv_lin= (LinearLayout) findViewById(R.id.text_lin);//要显示的字体
        final LinearLayout tv_hide_lin= (LinearLayout) findViewById(R.id.text_hide_lin);//所谓的布
//        final ImageView logo= (ImageView) findViewById(R.id.image);
        Animation animation = animation=AnimationUtils.loadAnimation(StartActivity.this,R.anim.app_open3);
        tv_lin.startAnimation(animation);
//        logo.startAnimation(animation);
//        animation=AnimationUtils.loadAnimation(StartActivity.this,R.anim.app_open1);
//        logo.startAnimation(animation);
//        animation=AnimationUtils.loadAnimation(StartActivity.this,R.anim.app_open2);
//        logo.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                animation=AnimationUtils.loadAnimation(StartActivity.this,R.anim.app_open4);
                tv_hide_lin.startAnimation(animation);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    public void startActivity(){
        Intent intent=new Intent(StartActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
