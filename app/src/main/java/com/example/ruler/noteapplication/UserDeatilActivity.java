package com.example.ruler.noteapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ruler.noteapplication.Bean.IMUser;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class UserDeatilActivity extends AppCompatActivity {
    private TextView mobilePhone;
    private TextView nickName;
    private EditText inputPhone;
    private EditText inputName;
    private Button editPhone;
    private Button editNickName;
    private int mode_markdown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_deatil);
        mobilePhone=(TextView)findViewById(R.id.mobilePhoneNumber);
        nickName=(TextView)findViewById(R.id.nick_Name);
        IMUser imUser= BmobUser.getCurrentUser(IMUser.class);
        mobilePhone.setText(imUser.getMobilePhoneNumber());
        nickName.setText(imUser.getNickName());
//        btn_mark_switch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mode_markdown==0){
//                    mode_markdown=1;
//                    mobilePhone.setVisibility(View.GONE);
//                    nickName.setVisibility(View.GONE);
//                    inputPhone.setVisibility(View.VISIBLE);
//                    inputName.setVisibility(View.VISIBLE);
//                    inputPhone.setText(mobilePhone.getText());
//
//                    Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.save);
//                    .set(bitmap);
//                }else {
//                    mode_markdown=0;
//                    inputPhone.setVisibility(View.GONE);
//                    inputName.setVisibility(View.GONE);
//                    mobilePhone.setVisibility(View.VISIBLE);
//                    nickName.setVisibility(View.VISIBLE);
//
//
//                    btn_mark_switch.setImageBitmap(bitmap);
//                }
//            }
//        });
    }
}
