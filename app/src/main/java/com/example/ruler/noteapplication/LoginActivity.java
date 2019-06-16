package com.example.ruler.noteapplication;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
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
import com.example.ruler.noteapplication.Data.Note;
import com.example.ruler.noteapplication.Data.NoteDao;
import com.example.ruler.noteapplication.Data.TestUser2;
import com.example.ruler.noteapplication.Data.TestUser2Dao;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private Button mBtnRegister;
    private Context context;

    private TestUser2Dao testUser2Dao;
    private static final String KEY_USERNAME = "key_username";
    private static final String KEY_PASSWORD = "key_password";

//    private UserBiz mUserBiz = new UserBiz();

    @Override
    protected void onResume() {
        super.onResume();
//        CookieJarImpl cookieJar = (CookieJarImpl) OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
//        cookieJar.getCookieStore().removeAll();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "lenve.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        testUser2Dao=daoSession.getTestUser2Dao();


        mEtUsername = (EditText) findViewById(R.id.input_user);
        mEtPassword = (EditText) findViewById(R.id.input_pwd);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnRegister = (Button) findViewById(R.id.btn_register);

        context=this;

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

        initIntent();


        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TestUser2 result=new TestUser2();
                      result=testUser2Dao.queryBuilder().where(TestUser2Dao.Properties.UserName.eq(mEtUsername.getText().toString())).unique();
                Log.d("login user","login user id: "+result.getId());
                BmobUser user=new BmobUser();
                final Long greenUserId=result.getId();
                EventBus.getDefault().post(mEtUsername.getText().toString());
                user.setUsername(mEtUsername.getText().toString());
                user.setPassword(mEtPassword.getText().toString());
                user.login(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser user, BmobException e) {
                        if (e == null) {
                           // Snackbar.make(view, "注册成功", Snackbar.LENGTH_LONG).show();
                            Log.d("UserLogin","user login success");
                            toMainActivity(user.getObjectId(),user.getUsername(),greenUserId);
                        } else {
                            //Snackbar.make(view, "尚未失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                            Log.e("UserLogin","user login error because of:"+e.getMessage()+"error code="+e.getErrorCode());

                            Toast.makeText(context,"login error:"+e.getErrorCode()+"because :"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
//                String username = mEtUsername.getText().toString();
//                String password = mEtPassword.getText().toString();
//
//                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
//                    T.showToast("账号或者密码不能为空");
//                    return;
//                }
//                startLoadingProgress();
//                mUserBiz.login(username, password, new CommonCallback<User>() {
//                    @Override
//                    public void onError(Exception e) {
//                        stopLoadingProgress();
//                        T.showToast(e.getMessage());
//                    }
//
//                    @Override
//                    public void onSuccess(User user) {
//                        stopLoadingProgress();
//                        T.showToast("登录成功");
//                        UserCache.getInstance().setUser(user);
//                        toOrderActivity();
//                        finish();
//                    }
//                });
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRegisterActivity();
            }
        });

    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra(KEY_USERNAME);
            if (username != null) {
                mEtUsername.setText(username);
            }
            String password = intent.getStringExtra(KEY_PASSWORD);
            if (password != null) {
                mEtPassword.setText(password);
            }
        }
    }

    private void toMainActivity(String objectId, String username, Long id){
        Intent intent=new Intent(this,MainActivity.class);
        Log.d("toMainActivity","greenId:"+id+" username:"+username);
        Bundle bundle = new Bundle();
        bundle.putString("USER_NAME",username );
        bundle.putString("USER_ID",objectId);
        bundle.putLong("GREEN_USER_ID",id);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void toRegisterActivity(){
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public static void launch(Context context, String username, String password){
        Intent intent=new Intent(context,LoginActivity.class);
        intent.putExtra(KEY_USERNAME,username);
        intent.putExtra(KEY_PASSWORD,password);
        context.startActivity(intent);
    }
}
