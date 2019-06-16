package com.example.ruler.noteapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ruler.noteapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends BaseAdapter {

    Context context;
    List<String>languages=new ArrayList<>();

    public SpinnerAdapter(Context context,List<String>languages){
        this.context=context;
        this.languages=languages;
    }

    @Override
    public int getCount() {
        return languages.size();
    }

    @Override
    public Object getItem(int position) {
        return languages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_spinner_adapter,null);
            viewHold=new ViewHold();

            convertView.setTag(viewHold);
        }else{
            viewHold=(ViewHold) convertView.getTag();
        }
        viewHold.textView=(TextView)convertView.findViewById(R.id.language_item);
        viewHold.textView.setText(languages.get(position));
        return  convertView;
    }

    public class ViewHold{
        TextView textView;
    }
}
