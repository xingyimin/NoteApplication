package com.example.ruler.noteapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ruler.noteapplication.Adapter.MusicListAdapter;
import com.example.ruler.noteapplication.Data.Song;
import com.example.ruler.noteapplication.Utils.LocalMusicUtils;

import java.util.List;

public class MusicListActivity extends AppCompatActivity {
    ListView musicList;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        context=this;
        initView();
    }

    public void initView(){
        musicList=(ListView)findViewById(R.id.music_list);
        List<Song>songs=LocalMusicUtils.getmusic(this);
        MusicListAdapter adapter=new MusicListAdapter(this,songs);
        musicList.setAdapter(adapter);
        musicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(context,MusicActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }
}
