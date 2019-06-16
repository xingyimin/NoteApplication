package com.example.ruler.noteapplication.Adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ruler.noteapplication.Bean.UserSetting;
import com.example.ruler.noteapplication.Data.Note;
import com.example.ruler.noteapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SettingAdapter extends BaseAdapter{

    List<UserSetting>items=new ArrayList<>();
    Context context;
    public SettingAdapter(List<UserSetting>items, Context context){
        this.items=items;
        this.context=context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold=null;

        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_usersetting_item,null);
            viewHold=new ViewHold();

            convertView.setTag(viewHold);
        }else{
            viewHold=(ViewHold) convertView.getTag();
        }
        viewHold.settingitem_text= (TextView) convertView.findViewById(R.id.settingitem_text);
        viewHold.settingitem_text.setText(items.get(position).getName().toString());
        viewHold.settingitem_img=(ImageView)convertView.findViewById(R.id.settingitem_img);
        viewHold.settingitem_img.setImageBitmap(items.get(position).getBitmap());
        return convertView;

    }

    class ViewHold{
        private TextView settingitem_text;
        private ImageView settingitem_img;
    }
}
