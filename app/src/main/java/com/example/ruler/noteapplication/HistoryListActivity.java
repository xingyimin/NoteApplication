package com.example.ruler.noteapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ruler.noteapplication.Adapter.HistoryListAdapter;
import com.example.ruler.noteapplication.BmobBean.AmendantRecord;
import com.example.ruler.noteapplication.Data.DaoMaster;
import com.example.ruler.noteapplication.Data.DaoSession;
import com.example.ruler.noteapplication.Data.EditRecord;
import com.example.ruler.noteapplication.Data.EditRecordDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HistoryListActivity extends AppCompatActivity {

    public ListView historyList;
    private List<EditRecord>editRecords;
    private List<AmendantRecord>amendantRecords;

    private EditRecordDao editRecordDao;
    private Context context;
    private HistoryListAdapter adapter;
    private String userId;
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
        editRecords=new ArrayList<>();
        amendantRecords=new ArrayList<>();
        context=this;
        amendantRecords=new ArrayList<>();
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "lenve.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        editRecordDao=daoSession.getEditRecordDao();
        editRecords=editRecordDao.loadAll();
        BmobQuery amendantRecordsQuery=new BmobQuery();
        amendantRecordsQuery.addWhereEqualTo("userId",userId);

        historyList=(ListView)findViewById(R.id.history_list);

        userName = getIntent().getStringExtra("USER_NAME");
        userId = getIntent().getStringExtra("USER_ID");

        BmobQuery<AmendantRecord>amendantRecordBmobQuery=new BmobQuery<>();
        amendantRecordsQuery.addWhereEqualTo("userId",userId);
        amendantRecordsQuery.findObjects(new FindListener<AmendantRecord>( ){

            @Override
            public void done(List<AmendantRecord> list, BmobException e) {
                setEditRecords(list);
                initData();
                initView();
            }
        });


        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditRecord editRecord= editRecords.get(position);
                AmendantRecord amendantRecord=amendantRecords.get(position);
                Intent intent=new Intent(HistoryListActivity.this,HistoryDetialActivity.class);
                intent.putExtra("NOTE_ID",(amendantRecord.getNoteId()).longValue());
                intent.putExtra("BEFORE_CONTENT",amendantRecord.getBoforeUpdateContent());
                intent.putExtra("AFTER_CONTENT",amendantRecord.getAfterUpdateContent());
                intent.putExtra("UPDATE_DATE",amendantRecord.getLastUpdateAt().getDate().toString());
                intent.putExtra("EDIT_ID",amendantRecord.getObjectId());
                intent.putExtra("USER_ID",amendantRecord.getUserId());

                startActivity(intent);
            }
        });
    }

    private void setEditRecords(List<AmendantRecord> list) {
        amendantRecords=list;
    }

    private void initView() {
            adapter=new HistoryListAdapter(amendantRecords,this);
            historyList.setAdapter(adapter);
        }

    private void initData() {



    }
}
