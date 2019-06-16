package com.example.ruler.noteapplication.TabView;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruler.noteapplication.Adapter.SettingAdapter;
import com.example.ruler.noteapplication.Bean.CercleImageView;
import com.example.ruler.noteapplication.Bean.IMUser;
import com.example.ruler.noteapplication.Bean.UserSetting;
import com.example.ruler.noteapplication.Data.Note;
import com.example.ruler.noteapplication.HistoryDetialActivity;
import com.example.ruler.noteapplication.HistoryListActivity;
import com.example.ruler.noteapplication.MusicListActivity;
import com.example.ruler.noteapplication.R;
import com.example.ruler.noteapplication.Tencent.BaseUIListener;
import com.example.ruler.noteapplication.UserDeatilActivity;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.bmob.v3.update.BmobUpdateAgent;

public class TabFragment2 extends Fragment{
    private View view;
    private static final String KEY2 = "title2";
    private TextView tvContent;
    private ImageView img_fragment2;
    private ListView settingList;
    private Button btnUserName;
    private Context context;
    private CercleImageView cercleImageView;

    private final String APPID="1106916740";
    private final String SCOPE ="get_simple_userinfo,add_topic";

    private Tencent mytencent;

    private List<UserSetting>settings=new ArrayList<>();
    private String userName;
    private String userId;
    private String imagepath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.layout_tab_fragment2,container,false);
//        tvContent = (TextView) view.findViewById(R.id.txt_fragmentni);
//        String string =getArguments().getString(KEY2);;
//        tvContent.setText(string);
//        tvContent.setTextColor(Color.BLUE);
//        tvContent.setTextSize(30);
        userName = getArguments().getString("USER_NAME");
        userId = getArguments().getString("USER_ID");
        EventBus.getDefault().register(this);
        initView();

        return view;
    }

//    private void doLogin(){
//        IUiListener listener=new BaseUIListener(){
//          @Override
//          protected void doComplete(JSONObject vavlue){
//
//          }
//        };
//        mytencent.login((Activity) this.getContext(),SCOPE,listener);
//    }

    private Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    System.out.println("111");
                    Bitmap bmp=(Bitmap)msg.obj;
                    cercleImageView.setImageBitmap(bmp);
                    break;
            }
        };
    };

    public void initView(){

//        mytencent=Tencent.createInstance(APPID,this.getActivity());
        context=this.getActivity();
        UserSetting item = new UserSetting();
        btnUserName=(Button)view.findViewById(R.id.btn_user_name);
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.star);
        String name="你的收藏";
        item.setName(name);
        item.setBitmap(bitmap);
        settings.add(item);
        UserSetting item1 = new UserSetting();
        bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.history);
        name="查看浏览记录";
        item1.setName(name);
        item1.setBitmap(bitmap);
        settings.add(item1);
        UserSetting item2 = new UserSetting();

        IMUser imUser=new IMUser();
        imUser= BmobUser.getCurrentUser(IMUser.class);
        final String imUserName=imUser.getUsername().toString();

        bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.user);
        name="关于你";
        item2.setName(name);
        item2.setBitmap(bitmap);
        settings.add(item2);
        UserSetting item3 = new UserSetting();
        bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.music_tabfragment2);
        name="音乐播放";
        item3.setName(name);
        item3.setBitmap(bitmap);
        UserSetting item4 = new UserSetting();
        settings.add(item3);
        bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.set);
        name="数据更新";
        item4.setName(name);
        item4.setBitmap(bitmap);
        settings.add(item4);

        settingList=(ListView)view.findViewById(R.id.list_setting);
        cercleImageView=(CercleImageView)view.findViewById(R.id.icon_user);
        SettingAdapter adapter=new SettingAdapter(settings,this.getContext());
        settingList.setAdapter(adapter);
        settingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        break;
                    case 1:
                        Intent intent=new Intent(context,HistoryListActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("USER_NAME",userName );
                        bundle.putString("USER_ID",userId);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 2:
                        Intent intent1=new Intent(context, UserDeatilActivity.class);
                        startActivity(intent1);
                        break;
                    case 3:
                        //BmobUpdateAgent.initAppVersion();
                        Intent intent2=new Intent(context, MusicListActivity.class);
                        startActivity(intent2);
                        break;
                    case 4:
                        BmobUpdateAgent.initAppVersion();
                        break;
                    default:
                        break;
                }
            }
        });

            btnUserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(imUserName);
                }
            });

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                IMUser imUser=new IMUser();
                imUser= BmobUser.getCurrentUser(IMUser.class);

                if (imUser.getImageHeaders()!=null){
                    final String userHeadersUrl=imUser.getImageHeaders().getFileUrl();
                Bitmap bmp = getURLimage(userHeadersUrl);
                Message msg = new Message();
                msg.what = 0;
                msg.obj = bmp;
                System.out.println("000");
                handle.sendMessage(msg);
                }
            }
        }).start();

        cercleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUserName(String userName){
        Log.d("fragment2Receive","get the message is"+userName);
        btnUserName.setText(userName);
    }

    public Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK)
        {
            /**
             * 当选择的图片不为空的话，在获取到图片的途径
             */
            Uri uri = data.getData();

            Log.e("get local picture","uri="+uri);
            try {
                String[] pojo = {MediaStore.Images.Media.DATA};

                Cursor cursor = this.getActivity().managedQuery(uri, pojo, null, null,null);
                if(cursor!=null)
                {
                    ContentResolver cr = this.getActivity().getContentResolver();
                    int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String path = cursor.getString(colunm_index);
                    /***
                     * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，这样的话，我们判断文件的后缀名
                     * 如果是图片格式的话，那么才可以
                     */
                    if(path.endsWith("jpg")||path.endsWith("png"))
                    {
                        imagepath = path;
                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        cercleImageView.setImageBitmap(bitmap);
                        System.out.println("image has change");
                    }else{
                        alert();
                    }
                    final BmobFile bmobFile=new BmobFile(new File(path));
                    bmobFile.uploadblock(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                Log.d("ImageHeadersUpload", "ImageHeadersUpload Success path:"+bmobFile.getFileUrl());

                                IMUser imUser=new IMUser();
                                imUser= BmobUser.getCurrentUser(IMUser.class);
                                imUser.setImageHeaders(bmobFile);
                                bmobFile.getUrl();
                                imUser.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e==null){
                                            Log.d("UserUpload", "UserUpload Success path:"+bmobFile.getFileUrl());
                                        }else {
                                            Log.e("UserUpload", "UserUpload Fail");
                                        }
                                    }
                                });
                            }else {
                                Log.e("ImageHeadersUpload", "ImageHeadersUpload Fail");
                            }
                        }
                    });
                }else{
                    alert();
                }

            } catch (Exception e) {
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void alert()
    {
        Dialog dialog = new AlertDialog.Builder(this.getActivity())
                .setTitle("警告")
                .setMessage("所选图片无效")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                imagepath = null;
                            }
                        })
                .create();
        dialog.show();
    }
}
