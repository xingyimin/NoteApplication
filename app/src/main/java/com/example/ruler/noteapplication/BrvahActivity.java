package com.example.ruler.noteapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.example.ruler.noteapplication.Adapter.BrvahAdapter;
import com.example.ruler.noteapplication.Data.CustomerDao;
import com.example.ruler.noteapplication.Data.DaoMaster;
import com.example.ruler.noteapplication.Data.DaoSession;
import com.example.ruler.noteapplication.Data.JoinNoteTagDao;
import com.example.ruler.noteapplication.Data.Note;
import com.example.ruler.noteapplication.Data.NoteBookDao;
import com.example.ruler.noteapplication.Data.NoteDao;
import com.example.ruler.noteapplication.Data.OrderDao;
import com.example.ruler.noteapplication.Data.TagDao;

import java.util.List;

import butterknife.BindView;

public class BrvahActivity extends AppCompatActivity {

    @BindView(R.id.list_brvah)RecyclerView listBrvah;

    private NoteDao noteDao;
    private NoteBookDao noteBookDao;
    private TagDao tagDao;
    private OrderDao orderDao;
    private CustomerDao customerDao;
    private JoinNoteTagDao joinNoteTagDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brvah);
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "lenve.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        noteBookDao=daoSession.getNoteBookDao();
        noteDao= daoSession.getNoteDao();
        tagDao= daoSession.getTagDao();
        customerDao=daoSession.getCustomerDao();
        orderDao=daoSession.getOrderDao();
        joinNoteTagDao=daoSession.getJoinNoteTagDao();

        List<Note>notes=noteDao.loadAll();
        BrvahAdapter adapter=new BrvahAdapter(R.layout.layout_brvah_item,notes);
        listBrvah.setAdapter(adapter);
    }
}
