package com.example.ruler.noteapplication.TabView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.ruler.noteapplication.Adapter.SpinnerAdapter;
import com.example.ruler.noteapplication.BmobBean.AmendantRecord;
import com.example.ruler.noteapplication.Data.DaoMaster;
import com.example.ruler.noteapplication.Data.DaoSession;
import com.example.ruler.noteapplication.Data.EditRecord;
import com.example.ruler.noteapplication.Data.EditRecordDao;
import com.example.ruler.noteapplication.Data.Note;
import com.example.ruler.noteapplication.Data.NoteDao;
import com.example.ruler.noteapplication.MainActivity;
import com.mittsu.markedview.MarkedView;

import com.example.ruler.noteapplication.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.RxMDTextView;
import com.yydcdut.rxmarkdown.RxMarkdown;
import com.yydcdut.rxmarkdown.callback.OnLinkClickCallback;
import com.yydcdut.rxmarkdown.loader.DefaultLoader;
import com.yydcdut.rxmarkdown.syntax.text.TextFactory;
import com.zzhoujay.markdown.MarkDown;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import ru.noties.markwon.Markwon;
import ru.noties.markwon.SpannableConfiguration;
import ru.noties.markwon.renderer.SpannableRenderer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TabFragment extends Fragment {
    private View view;
    private static final String KEY = "title";
    private TextView tvContent;
    private RxMDTextView textView;
    private int mode_markdown=0;
    private ImageView btn_mark_switch;
    private ImageView btn_share;
    private ImageView btn_mark;
    private EditText input_markdown;
    private Spinner spinner_language;
    private AutoCompleteTextView input_search;
    private ImageView img_search;
    private int isNightMode;

    private String[] html_markdown;
    private String userId;
    private String userName;
    private List<String>phone_mode;
    private Context context;

    private NoteDao noteDao;
    private EditRecordDao editRecordDao;
    private Note note;
    private EditRecord editRecord;
    private String file_name="";
    private Integer bmob_note_id;
    private AmendantRecord amendantRecord;
    private List<EditRecord>editRecords;


    @Override
    public void onStart() {
        super.onStart();
        bmob_note_id=null;
//        if (isAdded()) {//判断Fragment已经依附Activity
//            bmob_note_id = getArguments().getString("NoteID");
//        }

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final String TAG="content form TabFrom(Spinner)";
        final String[] language_use = {"java\n"};
        EventBus.getDefault().register(this);
        note=null;
        editRecord=new EditRecord();
        editRecords=new ArrayList<>();
        isNightMode=0;

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this.getContext(), "lenve.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        noteDao=daoSession.getNoteDao();
        editRecordDao=daoSession.getEditRecordDao();

        userName = getArguments().getString("USER_NAME");
        userId = getArguments().getString("USER_ID");

        amendantRecord=new AmendantRecord();
        mode_markdown=0;
        view = inflater.inflate(R.layout.layout_tab_fragment,container,false);

        initView();
        if(isNightMode==0) {
            textView.setBackgroundColor(Color.GRAY);
            input_markdown.setBackgroundColor(Color.GRAY);
            input_markdown.setTextColor(Color.WHITE);
        }else{
            textView.setBackgroundColor(Color.WHITE);
            input_markdown.setTextColor(Color.BLACK);
            input_markdown.setBackgroundColor(Color.WHITE);
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        html_markdown = new String[]{"```"+ language_use[0] +
                "class Test:\n" +
                "    def prt(self):\n" +
                "        print(self)\n" +
                "        print(self.__class__)\n" +
                " \n" +
                "t = Test()\n" +
                "t.prt()\n"+
                "```"};
        initData();

        doMarkdown(html_markdown[0]);

        final SpinnerAdapter spinnerAdapter=new SpinnerAdapter(this.getContext(),phone_mode);
        spinner_language.setAdapter(spinnerAdapter);

        spinner_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch ((String)spinnerAdapter.getItem(position)){
                    case "Mode_Day":
                        //setDayMode();
                        isNightMode=1;
                        setMode();
                        break;
                    case "Mode_Night":
                        //setNightMode();
                        isNightMode=0;
                        setMode();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btn_mark_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode_markdown==0){
                    mode_markdown=1;
                    textView.setVisibility(View.GONE);
                    input_markdown.setVisibility(View.VISIBLE);
                    input_markdown.setText(textView.getText());

                    amendantRecord.setBoforeUpdateContent(textView.getText().toString());
                    amendantRecord.setNoteId(bmob_note_id);
                    editRecord.setBoforeUpdateContent(textView.getText().toString());

                    Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.save);
                    btn_mark_switch.setImageBitmap(bitmap);
                }else {
                    mode_markdown=0;
                    html_markdown[0] ="```java\n" +input_markdown.getText().toString()+"\n"+"```";
                    input_markdown.setVisibility(View.GONE);
                    String content=input_markdown.getText().toString();
                    note.setContent(html_markdown[0]);
                    noteDao.update(note);

                    editRecords=editRecordDao.loadAll();
                    int history_size=editRecords.size();
                    //Long editId=0;
                    if(history_size!=0){
                        editRecord.setEditId(editRecords.get(editRecords.size()-1).getEditId()+1);
                    }else {
                        editRecord.setEditId((long) 0);
                    }
                    editRecord.setNoteId(note.getId());
                    editRecord.setAfterUpdateContent(content);
                    editRecord.setLastUpdateAt(new Date(System.currentTimeMillis()));
                    editRecordDao.insert(editRecord);
                    Long md=editRecord.getNoteId();
                    amendantRecord.setUserId(userId);
                    amendantRecord.setUserName(userName);
                    amendantRecord.setNoteId(new Integer(md.toString()));
                    amendantRecord.setAfterUpdateContent(content);
                    amendantRecord.setLastUpdateAt(new BmobDate(new Date(System.currentTimeMillis())));
                    amendantRecord.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e==null){
                                Log.d("addAmendantRecord","add data success");
                            }else {
                                Log.e("addAmendantRecord","add data error:"+e.getMessage());
                            }
                        }
                    });

                    textView.setVisibility(View.VISIBLE);
                    textView.setText(content);
                    String file_content=textView.getText().toString();

                    String status = Environment.getExternalStorageState();
                    if (status.equals(Environment.MEDIA_MOUNTED)) {
                        String main= Environment.getExternalStorageDirectory().getPath()+File.separator+"hello";
                        File destDir = new File(main);
                        if (!destDir.exists()) {
                            destDir.mkdirs();//在根创建了文件夹hello
                        }
                        destDir.mkdirs();
                        String toFile = main+File.separator+file_name+".txt";
                        try {
                            FileOutputStream fosto = new FileOutputStream(toFile);//创建test.text空文件
                            PrintStream ps=new PrintStream(fosto);
                            ps.println(file_content);
                            ps.flush();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    doMarkdown(html_markdown[0]);
                    Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.edit);
                    btn_mark_switch.setImageBitmap(bitmap);
                }
            }
        });
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share_note();

//                DialogPlus dialogPlus=DialogPlus.newDialog(getContext())
//                        .setAdapter(spinnerAdapter)
//                        .setOnItemClickListener(new OnItemClickListener() {
//                            @Override
//                            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
//
//                            }
//                        }).setExpanded(true)
//                        .setContentHolder(new ViewHolder(R.layout.layout_dialog_add_note))
//                        .create();
//                dialogPlus.show();
//
//                  boolean wrapInScrollView = true;
//                  new MaterialDialog.Builder(getActivity())
//                          .title("title")
//                          .customView(R.layout.layout_dialog_add_note,wrapInScrollView)
//                          .positiveText("agree")
//                          .negativeText("cancel")
//                          .onPositive(new MaterialDialog.SingleButtonCallback() {
//                              @Override
//                              public void onClick(MaterialDialog dialog, DialogAction which) {
//                                  dialog.dismiss();
//                              }
//                          })
//                          .onNeutral(new MaterialDialog.SingleButtonCallback() {
//                              @Override
//                              public void onClick(MaterialDialog dialog, DialogAction which) {
//                                  dialog.dismiss();
//                              }
//                          })
//                          .onNegative(new MaterialDialog.SingleButtonCallback() {
//                              @Override
//                              public void onClick(MaterialDialog dialog, DialogAction which) {
//                                  dialog.dismiss();
//                              }
//                          })
//                          .show();
                btn_mark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }
        });

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateRevert(Note receiveNote){
        noteDao.update(receiveNote);
        Log.d("fragment1Receive","get the message is"+receiveNote.getContent().toString());
        textView.setText(receiveNote.getContent());
    }

    public void share_note(){
        Intent share=new Intent(Intent.ACTION_SEND);
        ComponentName component = new ComponentName("com.tencent.mobileqq","com.tencent.mobileqq.activity.JumpActivity");
        share.setComponent(component);
        String main= Environment.getExternalStorageDirectory().getPath()+File.separator+"hello";
        File destDir = new File(main+"/"+file_name+".txt");
        System.out.println("get the target file state:"+destDir.exists());
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(destDir));
        share.setType("*/*");
        startActivity(Intent.createChooser(share,"发送"));
    }

    public void setTabText(String content,Note note){
        this.note=note;
        input_markdown.setText(content);
        if(isNightMode==0) {
            textView.setBackgroundColor(Color.GRAY);
            input_markdown.setBackgroundColor(Color.GRAY);
      //      input_markdown.setTextColor(Color.WHITE);
        }else{
            textView.setBackgroundColor(Color.WHITE);
            input_markdown.setTextColor(Color.BLACK);
     //       input_markdown.setBackgroundColor(Color.WHITE);
        }

        doMarkdown(content);
    }

    public void setFileName(String file_name){
        this.file_name=file_name;
        System.out.println("get the fileName from activity:"+this.file_name);
    }

    public void doMarkdown(String content){
        RxMDConfiguration rxMDConfiguration = new RxMDConfiguration.Builder(this.getContext())
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
                .setRxMDImageLoader(new DefaultLoader(this.getContext()))//default image loader
                .setDebug(true)//default value of debug
                .setOnLinkClickCallback(new OnLinkClickCallback() {//link click callback
                    @Override
                    public void onLinkClicked(View view, String link) {
                        Toast.makeText(view.getContext(), link, Toast.LENGTH_SHORT).show();
                    }
                })
                .build();

        RxMarkdown.with(content, this.getContext())
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
                        textView.setText(charSequence, TextView.BufferType.SPANNABLE);
                    }
                });

    }

    public void initData(){
        phone_mode=new ArrayList<>();
        phone_mode.add("Mode_Day");
        phone_mode.add("Mode_Night");
    }

    public void initView(){
        textView=(RxMDTextView)view.findViewById(R.id.text_markdown);
        btn_mark_switch=(ImageView) view.findViewById(R.id.btn_switch_write_save);
        btn_share=(ImageView)view.findViewById(R.id.btn_share);
        btn_mark=(ImageView) view.findViewById(R.id.img_mark);
        input_markdown=(EditText)view.findViewById(R.id.input_markdown);
        spinner_language=(Spinner)view.findViewById(R.id.spinner_language);
        input_search=(AutoCompleteTextView)view.findViewById(R.id.input_search);
        img_search=(ImageView)view.findViewById(R.id.btn_search);

        List<Note>notes=noteDao.loadAll();
        int l=notes.size();

        List<String>autoString=new ArrayList<>();
        for(int i=0;i<l;i++){
            autoString.add(notes.get(i).getTitle());
        }
        ArrayAdapter<String>adapter=new ArrayAdapter<>(this.getContext(),android.R.layout.simple_dropdown_item_1line,autoString);
        input_search.setAdapter(adapter);

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTitle=input_search.getText().toString();

                Note result=noteDao.queryBuilder().where(NoteDao.Properties.Title.eq(searchTitle)).unique();
                setTabText(result.getContent(),result);
                setFileName(result.getAuthor());
                textView.setText(result.getContent());
            }
        });

        if(mode_markdown==0){
            input_markdown.setVisibility(View.GONE);
        }
    }



    private void setNightMode(){
//        View fragmentview=LayoutInflater.from(context).inflate(R.layout.layout_tab_fragment,null);
//        RelativeLayout layout_tabfragment;
//        layout_tabfragment=(RelativeLayout)fragmentview.findViewById(R.id.layout_tabfragment_night);
//        layout_tabfragment.setBackgroundColor(Color.GRAY);
//        textView.setTextColor(Color.WHITE);
//        input_markdown.setTextColor(Color.WHITE);
    }
//
//    private void setDayMode(){
//        View fragmentview=LayoutInflater.from(context).inflate(R.layout.layout_tab_fragment,null);
//        RelativeLayout layout_tabfragment;
//        layout_tabfragment=(RelativeLayout)fragmentview.findViewById(R.id.layout_tabfragment_night);
//        layout_tabfragment.setBackgroundColor(Color.WHITE);
//        textView.setTextColor(Color.BLACK);
//        input_markdown.setTextColor(Color.BLACK);
//    }

    private void setMode(){

        if(isNightMode==0) {
            textView.setBackgroundColor(Color.GRAY);
            input_markdown.setBackgroundColor(Color.GRAY);
            input_markdown.setTextColor(Color.WHITE);
        }else{
            textView.setBackgroundColor(Color.WHITE);
            input_markdown.setTextColor(Color.BLACK);
            input_markdown.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        activity=getActivity();

    }

    /**
     * fragment静态传值
     */

    public static TabFragment newInstance(String str){
        TabFragment fragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY,str);
        fragment.setArguments(bundle);
        return fragment;
    }



}
