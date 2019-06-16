package com.example.ruler.noteapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ruler.noteapplication.Data.DaoMaster;
import com.example.ruler.noteapplication.Data.DaoSession;
import com.example.ruler.noteapplication.Data.Note;
import com.example.ruler.noteapplication.Data.NoteDao;
import com.example.ruler.noteapplication.R;

import java.util.List;

public class ListNoteAdapter extends BaseAdapter{
    private Context context;
    private List<Note>notes;

    public ListNoteAdapter(Context context,List<Note>notes){
        this.context=context;
        this.notes=notes;

    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notes.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final NoteDao noteDao;
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "lenve.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        ViewHold viewHold=null;
        noteDao=daoSession.getNoteDao();
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_list_note,null);
            viewHold=new ViewHold();

            convertView.setTag(viewHold);
        }else{
            viewHold=(ViewHold) convertView.getTag();
        }
        viewHold.note_desc= (TextView) convertView.findViewById(R.id.note_desc);
        viewHold.note_desc.setText(notes.get(position).getTitle().toString());
        viewHold.note_date= (TextView) convertView.findViewById(R.id.note_date);
        viewHold.note_date.setText(notes.get(position).getAuthor().toString());
        viewHold.note_delete=(ImageView)convertView.findViewById(R.id.img_delete);
        viewHold.note_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note item=notes.get(position);
                noteDao.delete(item);
                notes.remove(item);
                notifyDataSetChanged();

            }
        });
        return convertView;
    }

    public class ViewHold{
        private TextView note_desc;
        private TextView note_date;
        private ImageView note_delete;
    }
}
