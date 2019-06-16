package com.example.ruler.noteapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ruler.noteapplication.Data.Song;
import com.example.ruler.noteapplication.Utils.LocalMusicUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//接入监听接口
public class MusicActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView titleTV;//歌名
    private TextView autherTV;//歌手
    private ImageView imageView;//背景图
    private TextView timeTV;//总时长
    private ImageView prveBtn;//上一首歌
    private ImageView nextBtn;//下一首
    private int position;
    private ImageView discBtn;
    private ImageView needleBtn;
    private MediaPlayer mediaPlayer;//定义MediaPlayer
    private ImageView pressBtn;
    private ImageView putBtn;
    private SeekBar seekBar;//进度条
    private Thread thread;//线程
    private boolean isStop;//线程标志位
    private TextView currentime;//当前时间
    private int totalTime;

    //运用Handler中的handleMessage方法接收子线程传递的信息
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            // 将SeekBar位置设置到当前播放位置
            seekBar.setProgress(msg.what);
            //获得音乐的当前播放时间
            currentime.setText(formatime(msg.what));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
//绑定监听事件的id
        bindID();
//设置点击事件
        prveBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        pressBtn.setOnClickListener(this);
        putBtn.setOnClickListener(this);

//得到之前界面的传值
        final Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
//实例化MediaPlayer
        mediaPlayer = new MediaPlayer();

        play();

//监听滚动条事件
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //参数 ： fromUser即b 是用来标识是否来自用户的手动操作  true 用户动过手动方式更改的进度条
                if (b){

                    //seekto方法是异步方法
                    //seekto方法的参数是毫秒，而不是秒
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    private void play() {
        isStop = false;
        //Common是自己定义的一个工具类，里面获得的是本地音乐的信息
        Song music = LocalMusicUtils.getmusic(this).get(position);
        titleTV.setText(music.name);
        autherTV.setText(music.singer + "-" + music.albumId);
        pressBtn.setImageResource(R.drawable.pause);
        timeTV.setText(formatime(music.getDuration()));
        //获得音乐时长
        totalTime=music.getDuration();
        //将专辑图片放入黑圈中
      //  Bitmap discBitmap = BitmapFactory.decodeResource(MergeImagegetResources(), R.drawable.user);
        if (music.albumId != -1) {

//            imageView.setImageBitmap(music.albumbtm);
//            Bitmap bgbm = BlurUtil.doBlur(music.albumbtm, 15, 10);
//            imageView.setImageBitmap(bgbm);
//            Bitmap albumdiscBit = MergeImage.mergeThumbnailBitmap(discBitmap, music.albumId);
//            discBtn.setImageBitmap(albumdiscBit);

        } else {
//            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.play_page_default_cover);
//            imageView.setImageBitmap(BlurUtil.doBlur(bm, 10, 5));
//            Bitmap albumdiscBit = MergeImage.mergeThumbnailBitmap(discBitmap, bm);
//            discBtn.setImageBitmap(albumdiscBit);
        }
        //重置，当切换音乐时不会放前一首歌的歌曲
        mediaPlayer.reset();
        try {
            // 设置音乐播放源
            mediaPlayer.setDataSource(music.path);
            // 准备
            mediaPlayer.prepare();
            // 启动
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // 设置seekbar的最大值
        seekBar.setMax(mediaPlayer.getDuration());
        // 创建一个线程
        thread = new Thread(new MuiscThread());
        // 启动线程
        thread.start();

    }
    //时间转换类，将得到的音乐时间毫秒转换为时分秒格式
    private String formatime(int lengrh) {
        Date date = new Date(lengrh);
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String totalTime = sdf.format(date);
        return totalTime;
    }
    //绑定id的类
    private void bindID() {
        titleTV = findViewById(R.id.music_title);
        autherTV = findViewById(R.id.music_auther);
        imageView = findViewById(R.id.music_img);
        timeTV = findViewById(R.id.music_time);
        prveBtn = findViewById(R.id.music_prevbtn);
        nextBtn = findViewById(R.id.music_nextbtn);
        discBtn = findViewById(R.id.music_disc);
        needleBtn=findViewById(R.id.music_needle);
        pressBtn = findViewById(R.id.music_pressbtn);
        putBtn = findViewById(R.id.music_putdown);
        currentime=findViewById(R.id.music_firsttime);
        seekBar = findViewById(R.id.playSeekBar);
    }

    //点击事件
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.music_prevbtn://点击上一首歌曲
                position--;
                if (position == -1) {
                    position = LocalMusicUtils.getmusic(this).size() - 1;
                }
                play();
                break;
            case R.id.music_nextbtn://点击下一首歌曲
                position++;
                if (position == LocalMusicUtils.getmusic(this).size()) {
                    position = 0;
                }
                play();
                break;
            case R.id.music_pressbtn:
                //用if语句判断音乐播放的的状态
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    pressBtn.setImageResource(R.drawable.play);
                } else {
                    mediaPlayer.start();
                    //点击下一首时会继续出现暂停的按钮
                    pressBtn.setImageResource(R.drawable.pause);

                }
                break;
            case R.id.music_putdown:
                this.finish();
                break;
            default:
                break;

        }
    }

    //销毁时所做的工作
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.reset();
    }


    //建立一个子线程实现Runnable接口
    class MuiscThread implements Runnable {

        @Override
        //实现run方法
        public void run() {
            //判断音乐的状态，在不停止与不暂停的情况下向总线程发出信息
            while (mediaPlayer != null && isStop == false) {

                try {
                    // 每100毫秒更新一次位置
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //发出的信息
                handler.sendEmptyMessage(mediaPlayer.getCurrentPosition());
            }

        }

    }



}
