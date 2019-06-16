package com.example.ruler.noteapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ruler.noteapplication.Data.DaoMaster;
import com.example.ruler.noteapplication.Data.DaoSession;
import com.example.ruler.noteapplication.Data.TestUser2;
import com.example.ruler.noteapplication.Data.TestUser2Dao;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtRePassword;
    private Button mBtnRegister;
    private Context context;
//    private UserBiz mUserBiz;

    private TestUser2Dao testUser2Dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context=this;

        setContentView(R.layout.activity_register);
        //   setUpToolbar();
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "lenve.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();

        testUser2Dao=daoSession.getTestUser2Dao();
        BmobConfig config =new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId("7877af9deb684b999b5ce42302e3a081")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024*1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);
        initView();
        initEvent();
//        mUserBiz = new UserBiz();
    }

    private void initEvent() {

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();
                String repassword = mEtRePassword.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Log.d("RegisterActivity","Register success");
                    return;
                }

                if (!password.equals(repassword)) {

                    return;
                }

                BmobUser user=new BmobUser();
                user.setUsername(username);
                user.setPassword(password);
                user.signUp(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser o, BmobException e) {
                        if (e == null) {
                            // Snackbar.make(view, "注册成功", Snackbar.LENGTH_LONG).show();
                            Log.d("UserRegister","user register success");
                            finish();
                        } else {
                            //Snackbar.make(view, "尚未失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                            Log.e("UserRegister","user register error because of:"+e.getMessage());
                            Toast.makeText(context,"register error:"+e.getErrorCode()+"  because:"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
                TestUser2 testUser=new TestUser2(null,user.getObjectId(),username,password);
                testUser2Dao.insert(testUser);

//                startLoadingProgress();
//
//                mUserBiz.register(username, password, new CommonCallback<User>() {
//                    @Override
//                    public void onError(Exception e) {
//                        T.showToast(e.getMessage());
//
//                        Log.d("ee", e.getMessage());
//                        e.printStackTrace();
//                        stopLoadingProgress();
//                    }
//
//                    @Override
//                    public void onSuccess(User user) {
//                        stopLoadingProgress();
//                        T.showToast("注册成功，用户名为：" + user.getUsername());
//                        LoginActivity.launch(RegisterActivity.this,
//                                user.getUsername(), user.getPassword());
//                        finish();
//                    }
//                });
            }


        });

    }


    private void initView() {
        mEtUsername = (EditText) findViewById(R.id.register_phone);
        mEtPassword = (EditText) findViewById(R.id.register_pwd);
        mEtRePassword = (EditText) findViewById(R.id.register_re_pwd);
        mBtnRegister = (Button) findViewById(R.id.register_submit);

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        mUserBiz.onDestory();
    }
}
