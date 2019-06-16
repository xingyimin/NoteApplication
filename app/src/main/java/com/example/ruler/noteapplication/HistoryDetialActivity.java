package com.example.ruler.noteapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ruler.noteapplication.BmobBean.AmendantRecord;
import com.example.ruler.noteapplication.Data.DaoMaster;
import com.example.ruler.noteapplication.Data.DaoSession;
import com.example.ruler.noteapplication.Data.EditRecord;
import com.example.ruler.noteapplication.Data.JoinTagTest2User;
import com.example.ruler.noteapplication.Data.JoinTagTest2UserDao;
import com.example.ruler.noteapplication.Data.Note;
import com.example.ruler.noteapplication.Data.NoteDao;
import com.example.ruler.noteapplication.Data.Tag;
import com.example.ruler.noteapplication.Data.TagDao;
import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.RxMDTextView;
import com.yydcdut.rxmarkdown.RxMarkdown;
import com.yydcdut.rxmarkdown.callback.OnLinkClickCallback;
import com.yydcdut.rxmarkdown.loader.DefaultLoader;
import com.yydcdut.rxmarkdown.syntax.text.TextFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobDate;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HistoryDetialActivity extends AppCompatActivity {
    private RxMDTextView beforeContent;
    private RxMDTextView afterContent;
    private Button btnRevert;
    private EditRecord editRecord;
    private Long nodeId;
    private AmendantRecord amendantRecord;
    private String[] before_markdown;
    private String[] after_markdown;
    private NoteDao noteDao;
    private List<List<Note>>list_notes;
    private List<String>tags;
    private List<JoinTagTest2User>userTagMaps;
    private List<Tag>user_tags;
    private Note detailEntity;
    private Context context;
    private TagDao tagDao;
    private String userName;
    private String userId;
    private JoinTagTest2UserDao joinTagTest2UserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "lenve.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        noteDao=daoSession.getNoteDao();
        joinTagTest2UserDao=daoSession.getJoinTagTest2UserDao();
        context=this;
        editRecord=new EditRecord();
        amendantRecord=new AmendantRecord();


        setContentView(R.layout.activity_history_detial);



        before_markdown = new String[]{"```"+ "java\n" +
                "class Test:\n" +
                "    def prt(self):\n" +
                "        print(self)\n" +
                "        print(self.__class__)\n" +
                " \n" +
                "t = Test()\n" +
                "t.prt()\n"+
                "```"};
        after_markdown = new String[]{"```"+ "java\n" +
                "class Test:\n" +
                "    def prt(self):\n" +
                "        print(self)\n" +
                "        print(self.__class__)\n" +
                " \n" +
                "t = Test()\n" +
                "t.prt()\n"+
                "```"};
        final Intent intent=getIntent();
//        amendantRecord.setLastUpdateAt(BmobDate(intent.getStringExtra("UPDATE_DATE"));
        Log.d("HistoryDetail","update_date:"+intent.getStringExtra("UPDATE_DATE"));
        amendantRecord.setBoforeUpdateContent(intent.getStringExtra("BEFORE_CONTENT"));
        Log.d("HistoryDetail","before_content:"+intent.getStringExtra("BEFORE_CONTENT"));
        amendantRecord.setAfterUpdateContent(intent.getStringExtra("AFTER_CONTENT"));
        Log.d("HistoryDetail","before_content:"+intent.getStringExtra("AFTER_CONTENT"));
        nodeId=intent.getLongExtra("NOTE_ID",-1);
        userId=intent.getStringExtra("USER_ID");
        amendantRecord.setNoteId(new Integer(nodeId.toString()));
        amendantRecord.setObjectId(intent.getStringExtra("EDIT_ID"));
        detailEntity=noteDao.load(Long.valueOf(amendantRecord.getNoteId()));
        detailEntity.setContent(amendantRecord.getBoforeUpdateContent());

        userTagMaps=joinTagTest2UserDao.queryBuilder().where(JoinTagTest2UserDao.Properties.UserId.eq(userId)).list();

        initView();
        beforeContent.setText(amendantRecord.getBoforeUpdateContent());
        afterContent.setText(amendantRecord.getAfterUpdateContent());
        before_markdown[0] ="```java\n" +amendantRecord.getBoforeUpdateContent()+"\n"+"```";

        doBeforeMarkdown(before_markdown[0]);
        after_markdown[0]="```java\n" +amendantRecord.getAfterUpdateContent()+"\n"+"```";
        doAfterMarkdown(after_markdown[0]);
        btnRevert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(context).positiveText("确定").negativeText("取消").content("是否进行笔记内容回滚")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // TODO
                                noteDao.update(detailEntity);
                                Log.d("roll back","roll back note content success");
                                Intent intentMainActivity=new Intent(HistoryDetialActivity.this,MainActivity.class);
                                //initExpandData();
                                EventBus.getDefault().post(detailEntity);
                                startActivity(intentMainActivity);
                            }
                       })
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // TODO
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // TODO
                            }
                        })
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // TODO
                            }
                        }).show();


            }
        });
    }

    private void initView() {
        beforeContent=(RxMDTextView)findViewById(R.id.rx_text_before);
        afterContent=(RxMDTextView)findViewById(R.id.rx_text_after);
        btnRevert=(Button)findViewById(R.id.btn_revert);

    }


    public void doBeforeMarkdown(String content){
        RxMDConfiguration rxMDConfiguration = new RxMDConfiguration.Builder(this)
                .setDefaultImageSize(100, 100)//default image width & height
                .setBlockQuotesColor(Color.LTGRAY)//default color of block quotes
                .setHeader1RelativeSize(1.6f)//default relative size of header1
                .setHeader2RelativeSize(1.5f)//default relative size of header2
                .setHeader3RelativeSize(1.4f)//default relative size of header3
                .setHeader4RelativeSize(1.3f)//default relative size of header4
                .setHeader5RelativeSize(1.2f)//default relative size of header5
                .setHeader6RelativeSize(1.1f)//default relative size of header6
                .setHorizontalRulesColor(Color.RED)//default color of horizontal rules's background
                .setInlineCodeBgColor(Color.LTGRAY)//default color of inline code's background
                .setCodeBgColor(Color.LTGRAY)//default color of code's background
                .setTodoColor(Color.DKGRAY)//default color of todo
                .setTodoDoneColor(Color.DKGRAY)//default color of done
                .setUnOrderListColor(Color.BLACK)//default color of unorder list
                .setLinkColor(Color.RED)//default color of link text
                .setLinkUnderline(true)//default value of whether displays link underline
                .setRxMDImageLoader(new DefaultLoader(this))//default image loader
                .setDebug(true)//default value of debug
                .setOnLinkClickCallback(new OnLinkClickCallback() {//link click callback
                    @Override
                    public void onLinkClicked(View view, String link) {
                        Toast.makeText(view.getContext(), link, Toast.LENGTH_SHORT).show();
                    }
                })
                .build();

        RxMarkdown.with(content, this)
                .config(rxMDConfiguration)
                .factory(TextFactory.create())
                .intoObservable()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CharSequence>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(CharSequence charSequence) {
                        beforeContent.setText(charSequence, TextView.BufferType.SPANNABLE);

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

    public void addExpandData(String tag_name,List<Note> notes1){
        tags.add(tag_name);
        Log.d("addExpandData","tag_name:"+tag_name);
        list_notes.add(notes1);
//        System.out.println("the list size:"+notes.size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void doAfterMarkdown(String content){
        RxMDConfiguration rxMDConfiguration = new RxMDConfiguration.Builder(this)
                .setDefaultImageSize(100, 100)//default image width & height
                .setBlockQuotesColor(Color.LTGRAY)//default color of block quotes
                .setHeader1RelativeSize(1.6f)//default relative size of header1
                .setHeader2RelativeSize(1.5f)//default relative size of header2
                .setHeader3RelativeSize(1.4f)//default relative size of header3
                .setHeader4RelativeSize(1.3f)//default relative size of header4
                .setHeader5RelativeSize(1.2f)//default relative size of header5
                .setHeader6RelativeSize(1.1f)//default relative size of header6
                .setHorizontalRulesColor(Color.RED)//default color of horizontal rules's background
                .setInlineCodeBgColor(Color.LTGRAY)//default color of inline code's background
                .setCodeBgColor(Color.LTGRAY)//default color of code's background
                .setTodoColor(Color.DKGRAY)//default color of todo
                .setTodoDoneColor(Color.DKGRAY)//default color of done
                .setUnOrderListColor(Color.BLACK)//default color of unorder list
                .setLinkColor(Color.RED)//default color of link text
                .setLinkUnderline(true)//default value of whether displays link underline
                .setRxMDImageLoader(new DefaultLoader(this))//default image loader
                .setDebug(true)//default value of debug
                .setOnLinkClickCallback(new OnLinkClickCallback() {//link click callback
                    @Override
                    public void onLinkClicked(View view, String link) {
                        Toast.makeText(view.getContext(), link, Toast.LENGTH_SHORT).show();
                    }
                })
                .build();

        RxMarkdown.with(content, this)
                .config(rxMDConfiguration)
                .factory(TextFactory.create())
                .intoObservable()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CharSequence>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(CharSequence charSequence) {
                        afterContent.setText(charSequence, TextView.BufferType.SPANNABLE);

                    }
                });

    }
}
