package com.example.ruler.noteapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ruler.noteapplication.Data.Song;
import com.example.ruler.noteapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MusicListAdapter extends BaseAdapter {
    List<Song>songs=new ArrayList<>();
    Context context;
    public MusicListAdapter(Context context,List<Song>songs){
        this.songs=songs;
        this.context=context;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.layout_music_list_item,null);
            viewHolder=new ViewHolder();
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.albumId=(TextView)convertView.findViewById(R.id.album_id);
        viewHolder.songName=(TextView)convertView.findViewById(R.id.song_name);
        viewHolder.albumId.setText(String.valueOf(songs.get(position).getAlbumId()));
        viewHolder.songName.setText(songs.get(position).getName());
        return convertView;
    }

    class ViewHolder{
        TextView albumId;
        TextView songName;
    }
}
