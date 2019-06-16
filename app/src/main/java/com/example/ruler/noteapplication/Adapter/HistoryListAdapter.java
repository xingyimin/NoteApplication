package com.example.ruler.noteapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ruler.noteapplication.BmobBean.AmendantRecord;
import com.example.ruler.noteapplication.Data.EditRecord;
import com.example.ruler.noteapplication.R;
import com.yydcdut.rxmarkdown.RxMDTextView;

import java.util.List;

public class HistoryListAdapter extends BaseAdapter{
    private List<AmendantRecord>editRecords;
    private Context context;
    public HistoryListAdapter(List<AmendantRecord>editRecords, Context context){
        this.editRecords=editRecords;
        this.context=context;
    }

    @Override
    public int getCount() {
        return editRecords.size();
    }

    @Override
    public Object getItem(int position) {
        return editRecords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_history_list_item,null);
            viewHolder=new ViewHolder();
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.noteId=(TextView)convertView.findViewById(R.id.text_note_id);
        viewHolder.recordDate=(TextView)convertView.findViewById(R.id.text_record_date);
        viewHolder.noteId.setText(String.valueOf( editRecords.get(position).getNoteId()));
        viewHolder.recordDate.setText(editRecords.get(position).getLastUpdateAt().getDate().toString());
        return convertView;
    }

    class ViewHolder{
       private TextView noteId;
       private TextView recordDate;
    }
}
