package com.example.ruler.noteapplication.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ruler.noteapplication.Data.CustomerDao;
import com.example.ruler.noteapplication.Data.DaoMaster;
import com.example.ruler.noteapplication.Data.DaoSession;
import com.example.ruler.noteapplication.Data.JoinNoteTag;
import com.example.ruler.noteapplication.Data.JoinNoteTagDao;
import com.example.ruler.noteapplication.Data.Note;
import com.example.ruler.noteapplication.Data.NoteBookDao;
import com.example.ruler.noteapplication.Data.NoteDao;
import com.example.ruler.noteapplication.Data.OrderDao;
import com.example.ruler.noteapplication.Data.Tag;
import com.example.ruler.noteapplication.Data.TagDao;
import com.example.ruler.noteapplication.Dialog.DialogAddNote;
import com.example.ruler.noteapplication.LoginActivity;
import com.example.ruler.noteapplication.R;
import com.example.ruler.noteapplication.TabView.TabFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class TabBmobAdapter extends BaseExpandableListAdapter{
    private Context context;
    private List<com.example.ruler.noteapplication.BmobBean.Tag> tags;
    private List<List<com.example.ruler.noteapplication.BmobBean.Note>> tags_notes;
    private List<com.example.ruler.noteapplication.BmobBean.Note>result;

    private long group_position=-1;
    private long child_position=-1;
    final private Handler handler;
    private ExpandableListView listView;
    private ExpandableListAdapter adapter;



    public  TabBmobAdapter(Context context, List<com.example.ruler.noteapplication.BmobBean.Tag>tags, List<List<com.example.ruler.noteapplication.BmobBean.Note>>tags_notes) {
        this.context=context;
        this.tags=tags;
        this.tags_notes=tags_notes;
        result=new ArrayList<>();
        handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                notifyDataSetChanged();
                super.handleMessage(msg);
            }
        };
    }

    public void getInstance(ExpandableListView listView,ExpandableListAdapter adapter){
        this.listView=listView;
        this.adapter=adapter;
    }


//    public void reFlash(){
//        Message message=new Message();
//        message.what=6598;
//        message.obj="from TabAdapter";
//        handler.sendMessage(new Message());
//    }

    @Override
    public int getGroupCount() {
        return tags.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return tags_notes.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return tags_notes.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
        convertView=View.inflate(context, R.layout.layout_note_tag,null);
        TextView tag_name=(TextView)convertView.findViewById(R.id.tag_name);
        ImageView img_add_note=(ImageView)convertView.findViewById(R.id.img_add_note);
        ImageView img_delete_tag=(ImageView)convertView.findViewById(R.id.img_delete_tag);
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "lenve.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        tag_name.setText(tags.get(groupPosition).getName());

        group_position=groupPosition;
        img_add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogAddNote dialog=new DialogAddNote(context);
                dialog.show();
                dialog.setLeftButton("cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setRightButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("enter the method dialogOnclick");
                        Date date=new Date();

                        com.example.ruler.noteapplication.BmobBean.Note bmob_note= new com.example.ruler.noteapplication.BmobBean.Note();
                        com.example.ruler.noteapplication.BmobBean.Tag bmobtag=new com.example.ruler.noteapplication.BmobBean.Tag();
                        bmobtag=tags.get(groupPosition);
                        BmobQuery<com.example.ruler.noteapplication.BmobBean.Note>noteBmobQuery=new BmobQuery<>();
                        noteBmobQuery.addWhereEqualTo("tag",new BmobPointer(bmobtag));
                        noteBmobQuery.findObjects(new FindListener< com.example.ruler.noteapplication.BmobBean.Note>(){

                            @Override
                            public void done(List<com.example.ruler.noteapplication.BmobBean.Note> list, BmobException e) {
                                if(e==null){
                                    Log.d("Select All Notes in tag","select all notes success,get the resultSize:"+list.size());
                                    setResult(list);
                                }else {
                                    Log.e("Select all notes in tag","select error,errorCode:"+e.getErrorCode()+" because :"+e.getMessage());
                                }
                            }
                        });

//                        bmobtag.setName(tag.getName());
//                        bmobtag.setNoteId(Integer.parseInt(note.getId().toString()));
//                        bmobtag.setId(Integer.parseInt(tag.getId().toString()));
                        bmob_note.setTitle(bmob_note.getTitle());
                        bmob_note.setAuthor(bmob_note.getAuthor());
                        bmob_note.setContent(bmob_note.getContent());
                        bmob_note.setCreatedate(BmobDate.createBmobDate("YYYY MM DD",new Date(System.currentTimeMillis()).toString()));
                        bmob_note.setId(Integer.parseInt(bmob_note.getId().toString()));
                        bmob_note.setTag(bmobtag);
                        bmob_note.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if(e==null){
                                    Log.d("add_note","add note success");
                                }else {
                                    Log.d("add_note","add note error:"+e.getMessage());
                                }
                            }
                        });


                        listView.collapseGroup(groupPosition);
                        listView.expandGroup(groupPosition);
                    }});

            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        convertView=View.inflate(context,R.layout.layout_list_note,null);
        group_position=groupPosition;
        child_position=childPosition;
        TextView note_author=(TextView)convertView.findViewById(R.id.note_desc);
        TextView note_title=(TextView)convertView.findViewById(R.id.note_date);
        ImageView note_delete=(ImageView)convertView.findViewById(R.id.img_delete);
        note_author.setText(tags_notes.get(groupPosition).get(childPosition).getAuthor());
        note_title.setText(tags_notes.get(groupPosition).get(childPosition).getTitle());
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "lenve.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        List<Tag>tags = null;
        Tag tag=tags.get(groupPosition);
        List<Note>notes=tag.getNotes();
        final Note note=notes.get(childPosition);
        TabFragment tabFragment=new TabFragment();
        note_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("are you sure to delete it")
                        .setMessage("")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        return convertView;
    }

    public void setResult(List<com.example.ruler.noteapplication.BmobBean.Note>result){
        this.result=result;
    }

    public void clickDeleteNote(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("are you sure to delete it")
//                .setMessage("")
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        noteDao.delete(note);
//                        dialog.dismiss();
//
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.dismiss();
//                    }
//                }).show();
    }

    public void clickDeleteTag(){

    }

    public void clickAddNote(){

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public long getGroup_position() {
        return group_position;
    }

    public void setGroup_position(long group_position) {
        this.group_position = group_position;
    }

    public long getChild_position() {
        return child_position;
    }

    public void setChild_position(long child_position) {
        this.child_position = child_position;
    }
}
