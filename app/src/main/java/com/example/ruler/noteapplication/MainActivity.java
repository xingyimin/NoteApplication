package com.example.ruler.noteapplication;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.font.FontAwesome;
import com.example.ruler.noteapplication.Adapter.ListNoteAdapter;
import com.example.ruler.noteapplication.Adapter.TabAdapter;
import com.example.ruler.noteapplication.Bean.IMUser;
import com.example.ruler.noteapplication.Data.Customer;
import com.example.ruler.noteapplication.Data.CustomerDao;
import com.example.ruler.noteapplication.Data.DaoMaster;
import com.example.ruler.noteapplication.Data.DaoSession;
import com.example.ruler.noteapplication.Data.JoinNoteTag;
import com.example.ruler.noteapplication.Data.JoinNoteTagDao;
import com.example.ruler.noteapplication.Data.JoinTagTest2User;
import com.example.ruler.noteapplication.Data.JoinTagTest2UserDao;
import com.example.ruler.noteapplication.Data.Note;
import com.example.ruler.noteapplication.Data.NoteBook;
import com.example.ruler.noteapplication.Data.NoteBookDao;
import com.example.ruler.noteapplication.Data.NoteDao;
import com.example.ruler.noteapplication.Data.Order;
import com.example.ruler.noteapplication.Data.OrderDao;
import com.example.ruler.noteapplication.Data.Tag;
import com.example.ruler.noteapplication.Data.TagDao;
import com.example.ruler.noteapplication.Dialog.DialogAddNote;
import com.example.ruler.noteapplication.Dialog.DialogAddTag;
import com.example.ruler.noteapplication.Dialog.DialogDeleteTag;
import com.example.ruler.noteapplication.TabView.TabFragment;
import com.example.ruler.noteapplication.TabView.TabFragment2;
import com.example.ruler.noteapplication.Utils.BmobUtils;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.query.QueryBuilder;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;

public class MainActivity extends AppCompatActivity {



        //private ListView list_note;
        @BindView(R.id.layout_tab) TabLayout tabLayout;
        @BindView(R.id.viewpager) ViewPager viewPager;
        @BindView(R.id.toolbar) Toolbar toolbar;
        @BindView(R.id.img_add_tag) ImageView img_add;
        @BindView(R.id.list_note) ExpandableListView list_note;
//        @BindView(R.id.btn_delete_tag) ImageView img_delete_tag;
        private ImageView img_add_note;
        private ImageView img_delete_note;
        private TabFragment fragment;
        private TabFragment2 fragment2;

        private List<Fragment> list;
        private com.example.ruler.noteapplication.MainActivity.MyAdapter adapter;
        private String[] titles = {"笔记编辑", "用户", "页面3"};
        private String userId;
        private String userName;
        private Long greenUserId;
        public final static int MENU_ADD = Menu.FIRST;
        public final static int MENU_DELETE = Menu.FIRST + 1;


        private NoteDao noteDao;
        private NoteBookDao noteBookDao;
        private TagDao tagDao;
        private OrderDao orderDao;
        private CustomerDao customerDao;

        private JoinNoteTagDao joinNoteTagDao;
        private JoinTagTest2UserDao joinTagTest2UserDao;
        private List<List<Note>>list_notes;
        private List<JoinTagTest2User>userTagMaps;
        private List<Note>notes;
        private List<String>tags;
        private List<Tag>user_tags;
        private View tag_view;
        private View note_view;
        private TabAdapter tabAdapter;
        private Bundle bundle;
        private Context context;

        private int groupPosition;

        private List<com.example.ruler.noteapplication.BmobBean.Tag> bmobTagList;
        private Long data_tags_id;

        private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==101){
                    int id= (int) msg.obj;
                    list_note.collapseGroup(id);
                    list_note.expandGroup(id);
                    list_note.notifyAll();
                    list_note.deferNotifyDataSetChanged();
                    //更新数据 super.handleMessage(msg);

                }
            }

        };



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            LayoutInflaterCompat.setFactory2(getLayoutInflater(),new IconicsLayoutInflater2(getDelegate()));
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_main);
            BmobUpdateAgent.setUpdateOnlyWifi(false);
            BmobUpdateAgent.update(this);
            BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {

                @Override
                public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                    // TODO Auto-generated method stub
                    //根据updateStatus来判断更新是否成功
                    Log.d("Update app","updateStatus:"+updateStatus+" updateInfo:version:"+updateInfo.version+" updateInfo:version_i"+updateInfo.version_i);
                }
            });

            context=this;
            bmobTagList=new ArrayList<>();
            userTagMaps=new ArrayList<>();

            userId=getIntent().getStringExtra("USER_ID");
            userName=getIntent().getStringExtra("USER_NAME");
            greenUserId=getIntent().getLongExtra("GREEN_USER_ID",-1);
            Log.d("get greenUserId","get greenUserId:"+greenUserId);
            if(userName!=null) {
                Toast.makeText(context, "welcome:" + userName, Toast.LENGTH_LONG).show();
            }
            ButterKnife.bind(this);//绑定注解解activity
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

            selectAllTag();


            String TAG="columnname";
            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "lenve.db", null);
            DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
            DaoSession daoSession = daoMaster.newSession();
            noteBookDao=daoSession.getNoteBookDao();
            noteDao= daoSession.getNoteDao();
            tagDao= daoSession.getTagDao();
            customerDao=daoSession.getCustomerDao();
            orderDao=daoSession.getOrderDao();
            joinNoteTagDao=daoSession.getJoinNoteTagDao();
            joinTagTest2UserDao=daoSession.getJoinTagTest2UserDao();
            setUserTagMaps();
            final Handler update_progress_bar = new Handler()
            {
                @Override
                public void handleMessage(Message msg) {
                    if(msg.what==101){
                        Log.i(this.getClass().toString(),"enter the method handleMessage");
                        tabAdapter.notifyDataSetChanged();

                    }
                }

            };

            initExpandData();

            Runnable msghandle=new Runnable() {
                @Override
                public void run() {
                    Message msg=update_progress_bar.obtainMessage();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    if(msg.what==101){
                        System.out.println("get groupPosition is "+msg.obj);
                    }
                }
            };
            update_progress_bar.post(msghandle);

            initView();

            Date date=new Date();
//        noteDao.deleteAll();
//        tagDao.deleteAll();
//        joinNoteTagDao.deleteAll();
//        Note note=new Note(null,"str2","str2","str2",date,(long)1,(long)1);
//        noteDao.insert(note);
//
//        Tag tag=new Tag(null,"tag2",(long)1);
//        tagDao.insert(tag);

//        JoinNoteTag joinNoteTag=new JoinNoteTag(null, note.getId(),tag.getId());
//        joinNoteTagDao.insert(joinNoteTag);

//        List<Tag>item_tags1=tagDao.loadAll();
//        List<Note>item_notes1=noteDao.loadAll();
//        Tag item_tag=item_tags1.get(1);
//        Note item_note=item_notes1.get(1);
//        List<Tag>tags_from_note=item_note.getTags();
//        List<Note>notes_from_tag=item_tag.getNotes();
//        System.out.println("get the note from tag size is:"+notes_from_tag.size()+" /n "+"get the tag from note size is:"+tags_from_note.size());
//        List<Tag>item_tags1= tagDao.loadAll();
//        Tag item_tag1 =item_tags1.get(1);
//        daoSession.delete(item_tag1);

            notes=noteDao.loadAll();

            for(int i=0;i<notes.size();i++){
                Note item=notes.get(i);
                System.out.println("note author:"+item.getAuthor());
                System.out.println("note content:"+item.getContent());
                System.out.println("note id:"+item.getId());
                System.out.println("tag id:"+item.getId());
                System.out.println("tags size:"+item.getTags().size());
            }

            List<JoinNoteTag>joinNoteTags=new ArrayList<>();
            joinNoteTags=joinNoteTagDao.loadAll();
            for (int i=0;i<joinNoteTags.size();i++){
                JoinNoteTag item=joinNoteTags.get(i);
                System.out.println("get join_note_id:"+item.getId());
                System.out.println("get join_tag_id:"+item.getTagId());
            }

            List<Tag>item_tags=new ArrayList<>();
            item_tags= tagDao.loadAll();
            for(int i=0;i<item_tags.size();i++){
                Tag item=item_tags.get(i);
                System.out.println("tag id:"+item.getId());
                System.out.println("tag name:"+item.getName());
                System.out.println("note"+item.getNoteId());
            }


            tabAdapter=new TabAdapter(this,tags,list_notes,userTagMaps);
            for(int i=0;i<tags.size();i++){
                Log.d("setTabAdapter","tagName:"+tags.get(i));
            }
            list_note.setAdapter(tabAdapter);
            tabAdapter.getInstance(list_note,tabAdapter);//向adapter传入instance实体
//        ListNoteAdapter listNoteAdapter=new ListNoteAdapter(this,notes);
//        list_note.setAdapter(listNoteAdapter);

            adapter = new com.example.ruler.noteapplication.MainActivity.MyAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setTabsFromPagerAdapter(adapter);
            for(int i=0;i<tabLayout.getTabCount();i++){
                TabLayout.Tab tab=tabLayout.getTabAt(i);
                Drawable d=null;
                switch (i){
                    case 0:
                        d=getResources().getDrawable(R.drawable.form);
                        break;
                    case 1:
                        d=getResources().getDrawable(R.drawable.user);
                        break;
                    default:
                        break;
                }
                tab.setIcon(d);
            }

            final DialogAddNote dialog=new DialogAddNote(this);
            final DialogAddTag dialogAddTag=new DialogAddTag(this);
            final DialogDeleteTag dialogDeleteTag=new DialogDeleteTag(this);


        }

        private void setUserTagMaps() {

            userTagMaps=joinTagTest2UserDao.queryBuilder().where(JoinTagTest2UserDao.Properties.UserId.eq(greenUserId)).list();
            Log.d("setUserTagMaps","get list's size:"+userTagMaps.size());
        }

        @Override
        protected void onResume() {
            super.onResume();
            Log.d("MainActivity","Activity Resume");
            //   initExpandData();
            //fragment.setTabText();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.menu_main,menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item){
            int id=item.getItemId();
            if(id==R.id.action_settings){
                return true;
            };
            return super.onOptionsItemSelected(item);
        }
        @Override
        protected void attachBaseContext(Context newBase) {
            super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
        }

//        @OnClick(R.id.btn_delete_tag) void clickDeleteTag(){
//            final DialogDeleteTag dialogDeleteTag=new DialogDeleteTag(this);
//            View view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_dialog_delete_tag,null);
//            dialogDeleteTag.show();
//            dialogDeleteTag.setHintText("input the position of tag:");
//            dialogDeleteTag.setLeftButton("Cancel", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialogDeleteTag.dismiss();
//                }
//            });
//            dialogDeleteTag.setRightButton("OK", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String group_tag=dialogDeleteTag.getInput_tag_delete().getText().toString();
//                    System.out.println("input tag group:"+group_tag);
//                    int id_group=Integer.parseInt(group_tag);
//                    List<Tag>delete_tags=tagDao.loadAll();
//
//                    Integer bmobTagId=Integer.parseInt(group_tag);
//                    Log.d("clickDeleteTag","get the bmobTagId="+bmobTagId);
//                    //       deleteBmobTag(bmobTagId);
//
//                    Tag delete_tag=delete_tags.get(id_group);
//                    List<Note>delete_notes=delete_tag.getNotes();
//                    for(int i=0;i<delete_notes.size();i++){
//                        Note delete_item=delete_notes.get(i);
//                        noteDao.delete(delete_item);
//                    }
//                    tagDao.delete(delete_tag);
//                    if(list_notes.size()>0){
//                        list_notes.remove(id_group-1);
//                    }
//                    tags.remove(id_group-1);
//                    tabAdapter.notifyDataSetChanged();
//                    dialogDeleteTag.dismiss();
//                }
//            });
//        }

        @OnClick(R.id.img_add_tag) void clickAddTag(){
            final DialogAddTag dialogAddTag=new DialogAddTag(this);
            dialogAddTag.show();
            dialogAddTag.setTitle("");
            dialogAddTag.setHintText("input the tag name  in here:");
            dialogAddTag.setLeftButton("cancel", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("you chose the option 'cancel'");
                    dialogAddTag.dismiss();
                }
            });
            dialogAddTag.setRightButton("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date date=new Date();
                    Tag newtag=new Tag(null,dialogAddTag.getInput_tag_name().getText().toString(),(long)0);
                    Log.d("add tagName","add Tag name:"+newtag.getName());
//                data_tags=tagDao.loadAll();
//                data_tags_id=(long)0;
//                if(data_tags.size()>0){
//                    data_tags_id=data_tags.get(bmobTagList.size()).getId()+1;
//                }
//                addBmobTag(Integer.valueOf(data_tags_id.toString()),dialogAddTag.getInput_tag_name().getText().toString());

//                Log.d("add newTag","newTag id:"+newtag.getId());
                    tagDao.insert(newtag);
                    user_tags.add(newtag);
                    tags.add(newtag.getName());
                    list_notes.add(new ArrayList<Note>());
//                initExpandData();
                    Log.d("add tagId","add tag id:"+user_tags.get(user_tags.size()-1).getId()+" add tag name:"+user_tags.get(user_tags.size()-1).getName());
                    JoinTagTest2User joinTagTest2User=new JoinTagTest2User();
                    joinTagTest2User.setTagId(user_tags.get(user_tags.size()-1).getId());
                    joinTagTest2User.setUserId(greenUserId);
                    Log.d("add tagId","add tag id:"+joinTagTest2User.getTagId()+" add user id:"+joinTagTest2User.getUserId());
                    joinTagTest2UserDao.insert(joinTagTest2User);
                    userTagMaps.add(joinTagTest2User);
                    System.out.println("get the dialog author"+dialogAddTag.getInput_tag_name().getText().toString());
                    tabAdapter.notifyDataSetChanged();
                    dialogAddTag.dismiss();

                }
            });
        }


        public void initExpandData(){

            user_tags=new ArrayList<>();
            list_notes=new ArrayList<>();
            tags=new ArrayList<>();
            for(int i=0;i<userTagMaps.size();i++){
                Tag init_tag=tagDao.queryBuilder().where(TagDao.Properties.Id.eq(userTagMaps.get(i).getTagId())).unique();
                Log.d("initExpandList","tagId:"+init_tag.getId()+" tagName:"+init_tag.getName());
                user_tags.add(init_tag);
            }

            for(int i=0;i<user_tags.size();i++){
                addExpandData(user_tags.get(i).getName(),user_tags.get(i).getNotes());
            }
        }

        public void addExpandData(String tag_name,List<Note>notes1){
            tags.add(tag_name);
            Log.d("addExpandData","tag_name:"+tag_name);
            list_notes.add(notes1);
//        System.out.println("the list size:"+notes.size());
        }

        public void initView(){
            View view= LayoutInflater.from(this).inflate(R.layout.layout_note_tag,null);
            note_view=LayoutInflater.from(this).inflate(R.layout.layout_list_note,null);
            img_delete_note=(ImageView)note_view.findViewById(R.id.img_delete);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            list=new ArrayList<>();
            fragment=new TabFragment();
            list.add(fragment);
            fragment2=new TabFragment2();
            list.add(fragment2);
            Bundle bundle = new Bundle();
            bundle.putString("USER_NAME",userName );
            bundle.putString("USER_ID",userId);
            fragment.setArguments(bundle);
            fragment2.setArguments(bundle);

            list_note.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, final int groupPosition, final int childPosition, long id) {
                    list_note.collapseGroup(groupPosition);
                    list_note.expandGroup(groupPosition);
                    List<Tag>tags_child=user_tags;
                    Tag tag_child=tags_child.get(groupPosition);
                    List<Note>notes_child=tag_child.getNotes();
                    Note note_child=notes_child.get(childPosition);

//
//                BmobQuery<com.example.ruler.noteapplication.BmobBean.Note> notesQuery=new BmobQuery<>();
//                notesQuery.addWhereEqualTo("tagName",tag_child.getName());
//                notesQuery.findObjects(new FindListener<com.example.ruler.noteapplication.BmobBean.Note>() {
//                    @Override
//                    public void done(List<com.example.ruler.noteapplication.BmobBean.Note> list, BmobException e) {
//                        if(e==null){
//                            List<com.example.ruler.noteapplication.BmobBean.Note>bmob_notes;
//                            bmob_notes=list;
//                            com.example.ruler.noteapplication.BmobBean.Note bmob_note=bmob_notes.get(childPosition);
//
//
//                        }else {
//                            Log.e("selectAllNote","bmobError:"+e.getMessage());
//                        }
//                    }
//                });

                    String content=note_child.getContent();

                    fragment.setTabText(content,note_child);
                    fragment.setFileName(note_child.getAuthor());
                    return true;
                }
            });
        }



        public void selectAllTag(){
            BmobQuery<com.example.ruler.noteapplication.BmobBean.Tag> categoryBmobQuery = new BmobQuery<>();
            // categoryBmobQuery.addWhereEqualTo("name", "service8");
            categoryBmobQuery.findObjects(new FindListener<com.example.ruler.noteapplication.BmobBean.Tag>() {
                @Override
                public void done(List<com.example.ruler.noteapplication.BmobBean.Tag> object, BmobException e) {
                    if(e==null){
                        List<com.example.ruler.noteapplication.BmobBean.Tag>selecttags=object;
                        // bmobTagList=selecttags;
                        Log.i("addBmobTag","select all tag success!object size:"+selecttags.size());

                        setBmobTags(object);
                    }else{
                        Log.e("addBmobTag","insert error,the message is:"+e.getMessage());
                    }
                }
            });
        }
        public void selectTag(Integer id){
            com.example.ruler.noteapplication.BmobBean.Tag target=new com.example.ruler.noteapplication.BmobBean.Tag();
            BmobQuery<com.example.ruler.noteapplication.BmobBean.Tag>tagBmobQuery=new BmobQuery<>();
            tagBmobQuery.addWhereEqualTo("id",id);
            final List<com.example.ruler.noteapplication.BmobBean.Tag>tags=new ArrayList<>();
            tagBmobQuery.findObjects(new FindListener<com.example.ruler.noteapplication.BmobBean.Tag>() {
                @Override
                public void done(List<com.example.ruler.noteapplication.BmobBean.Tag> list, BmobException e) {
                    if(e==null){
                        setBmobTags(list);
                    }else {
                        Log.e("selectTag","select error ,the message is:"+e.getMessage());
                    }
                }
            });

        }

        public void addBmobTag(Integer id, final String name){
            com.example.ruler.noteapplication.BmobBean.Tag bmobTag=new com.example.ruler.noteapplication.BmobBean.Tag();
            bmobTag.setId(id);
            bmobTag.setName(name);
            bmobTag.save(new SaveListener<String>(){

                @Override
                public void done(String s, BmobException e) {
                    if(e!=null){
                        Log.i("addBmobTag","insert tag success,the name is:"+name);
                    }else{
                        Log.e("addBmobTag","insert error,the message is:"+e.getMessage());
                    }
                }
            });
            bmobTagList.add(bmobTag);
        }

        public void deleteBmobTag(final Integer bmobTagId){

            com.example.ruler.noteapplication.BmobBean.Tag bmobTag=new com.example.ruler.noteapplication.BmobBean.Tag();

            {
//            final String bmobObjectId=bmobTagList.get(bmobTagId-1).getObjectId();
                bmobTag.setObjectId(bmobTagList.get(bmobTagId).getObjectId());
                bmobTag.delete(new UpdateListener(){
                    @Override
                    public void done(BmobException e) {
                        if(e!=null){
                            bmobTagList.remove(bmobTagList.get(bmobTagId));
                            Log.i("deleteBmobTag","delete tag success,the objectId is:");
                        }else{
                            Log.e("deleteBmobTag","delete error,the message is:"+e.getMessage());
                        }
                    }
                });

            }
        }

        @Subscribe(threadMode = ThreadMode.MAIN)
        public void updateRevert(Note receiveNote){
            noteDao.update(receiveNote);
            Log.d("MainActivityReceive","get the message is"+receiveNote.getContent().toString());

        }

        public void setBmobTags(List<com.example.ruler.noteapplication.BmobBean.Tag>bmobTags){

            this.bmobTagList=bmobTags;
        }

        class MyAdapter extends FragmentPagerAdapter {

            public MyAdapter(FragmentManager fm) {
                super(fm);
            }
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        }

}






