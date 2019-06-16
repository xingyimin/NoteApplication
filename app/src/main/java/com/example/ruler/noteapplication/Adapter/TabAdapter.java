package com.example.ruler.noteapplication.Adapter;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
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

import com.example.ruler.noteapplication.App.MyApplication;
import com.example.ruler.noteapplication.Data.CustomerDao;
import com.example.ruler.noteapplication.Data.DaoMaster;
import com.example.ruler.noteapplication.Data.DaoSession;
import com.example.ruler.noteapplication.Data.JoinNoteTag;
import com.example.ruler.noteapplication.Data.JoinNoteTagDao;
import com.example.ruler.noteapplication.Data.JoinTagTest2User;
import com.example.ruler.noteapplication.Data.Note;
import com.example.ruler.noteapplication.Data.NoteBookDao;
import com.example.ruler.noteapplication.Data.NoteDao;
import com.example.ruler.noteapplication.Data.OrderDao;
import com.example.ruler.noteapplication.Data.Tag;
import com.example.ruler.noteapplication.Data.TagDao;
import com.example.ruler.noteapplication.Dialog.DialogAddNote;
import com.example.ruler.noteapplication.MainActivity;
import com.example.ruler.noteapplication.R;
import com.example.ruler.noteapplication.TabView.TabFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class TabAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String>tags;
    private List<List<Note>>tags_notes;
    private List<JoinTagTest2User>userTagMaps;
    private NoteDao noteDao;
    private NoteBookDao noteBookDao;
    private TagDao tagDao;
    private OrderDao orderDao;
    private CustomerDao customerDao;
    private JoinNoteTagDao joinNoteTagDao;
    private long group_position=-1;
    private long child_position=-1;
    final private Handler handler;
    private ExpandableListView listView;
    private volatile ExpandableListAdapter adapter;
    private List<com.example.ruler.noteapplication.BmobBean.Tag>bmobTagsOuter;

    public  TabAdapter(Context context, List<String> tags, List<List<Note>> tags_notes, List<JoinTagTest2User> userTagMaps) {
        this.context=context;
        this.tags=tags;
        this.tags_notes=tags_notes;
        this.userTagMaps=userTagMaps;
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

    public void setTags_notes(List<List<Note>>tags_notes){
        this.tags_notes=tags_notes;
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
        convertView=View.inflate(context,R.layout.layout_note_tag,null);
        TextView tag_name=(TextView)convertView.findViewById(R.id.tag_name);
        ImageView img_add_note=(ImageView)convertView.findViewById(R.id.img_add_note);
        ImageView img_delete_tag=(ImageView)convertView.findViewById(R.id.img_delete_tag);
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "lenve.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();


        group_position=groupPosition;
        tagDao=daoSession.getTagDao();
        noteDao=daoSession.getNoteDao();
        joinNoteTagDao=daoSession.getJoinNoteTagDao();
        List<Tag>tags=new ArrayList<>();
        for(int i=0;i<userTagMaps.size();i++){
            Tag init_tag=tagDao.queryBuilder().where(TagDao.Properties.Id.eq(userTagMaps.get(i).getTagId())).unique();
            Log.d("initExpandList","tagId:"+init_tag.getId()+" tagName:"+init_tag.getName());
            tags.add(init_tag);
        }
//        if(groupPosition>=tags.size()){
//            tags.add(new Tag());
//        }
        final Tag tag=tags.get(groupPosition);
        Log.d("getGroup","Get group position:"+groupPosition);
        final Date date=new Date();
        tag_name.setText(tags.get(groupPosition).getName());

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
                        final Note note=new Note(null,dialog.getInput_note_title().getText().toString()," ",dialog.getInput_note_name().getText().toString(),date,1,1);
                        noteDao.insert(note);
                        JoinNoteTag joinNoteTag=new JoinNoteTag(null,note.getId(),tag.getId());
                        System.out.println("note idL"+note.getId()+",tag id:"+tag.getId());
                        joinNoteTagDao.insert(joinNoteTag);
                        List<Note>notes=tags_notes.get(groupPosition);
                        notes.add(note);

                        tags_notes.set(groupPosition,notes);
                        listView.collapseGroup(groupPosition);
                        listView.expandGroup(groupPosition);

//                        com.example.ruler.noteapplication.BmobBean.Note bmob_note= new com.example.ruler.noteapplication.BmobBean.Note();
//                        com.example.ruler.noteapplication.BmobBean.Tag bmobtag=new com.example.ruler.noteapplication.BmobBean.Tag();
//                        bmobtag=bmobTags.get(groupPosition);

//                        bmobtag.setName(tag.getName());
//                        bmobtag.setNoteId(Integer.parseInt(note.getId().toString()));
//                        bmobtag.setId(Integer.parseInt(tag.getId().toString()));
//                        bmob_note.setTitle(note.getTitle());
//                        bmob_note.setAuthor(note.getAuthor());
//                        bmob_note.setContent(note.getContent());
//                        bmob_note.setCreatedate(BmobDate.createBmobDate("YYYY MM DD",new Date(System.currentTimeMillis()).toString()));
//                        bmob_note.setId(Integer.parseInt(note.getId().toString()));
//                        bmob_note.setTag(bmobtag);
//                        bmob_note.save(new SaveListener<String>() {
//                            @Override
//                            public void done(String s, BmobException e) {
//                                if(e==null){
//                                    Log.d("add_note","add note success");
//                                }else {
//                                    Log.d("add_note","add note error:"+e.getMessage());
//                                }
//                            }
//                        });
                        notifyDataSetChanged();
                        System.out.println("get the dialog title:  "+dialog.getInput_note_title().getText().toString());
                        System.out.println("get the dialog author"+dialog.getInput_note_name().getText().toString());
                        dialog.dismiss();
                    }
                });
            }
        });



        img_delete_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("get group position:"+groupPosition);
                tagDao.delete(tag);
                List<Note>delete_notes=tag.getNotes();
                for(int i=0;i<delete_notes.size();i++){
                    Note delete_item=delete_notes.get(i);
                    JoinNoteTag delete_bradge=new JoinNoteTag(null,delete_item.getId(),tag.getId());

                    System.out.println("get the key of joinNoteTagDao:"+joinNoteTagDao.getKey(delete_bradge));
                    joinNoteTagDao.deleteByKey(tag.getId());
                    noteDao.delete(delete_item);

                }


                        tags_notes.remove(groupPosition);
                        removeTag(groupPosition);
                        notifyDataSetChanged();

            }
        });
        return convertView;
    }

    private void removeTag(int groupPosition) {
        tags.remove(groupPosition);
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

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
        tagDao=daoSession.getTagDao();
        noteDao=daoSession.getNoteDao();
        joinNoteTagDao=daoSession.getJoinNoteTagDao();
        List<Tag>tags=tagDao.loadAll();
        Tag tag=tags.get(groupPosition);
        final List<Note>notes=tags_notes.get(groupPosition);
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
                                noteDao.delete(note);
                                notes.remove(childPosition);
                                tags_notes.set(groupPosition,notes);
                                listView.collapseGroup(groupPosition);
                                listView.expandGroup(groupPosition);
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
