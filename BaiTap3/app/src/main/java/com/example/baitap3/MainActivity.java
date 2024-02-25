package com.example.baitap3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.baitap3.object.Music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements VideoDownLoader.MethodCallback{

    public RecyclerView myView;
    ArrayList musicList = new ArrayList<>(Arrays.asList("Video_1"));
    VideoView video;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myView = findViewById(R.id.myRecyclerView);
        video = findViewById(R.id.videoView);
        myView.setLayoutManager(new LinearLayoutManager(this));
        myView.setAdapter(new Adapter(this,musicList));


    }

    public void downloadClick(View view){
        new DownloadFileFromDrive(MainActivity.this).execute();

    }

    public void removeClick(View view){
        if (video.isPlaying()){
            video.stopPlayback();
            video.setVideoURI(null);
            video.setVisibility(View.GONE);
        }
        if (video.getVisibility() == View.GONE){

            video.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onMethodCallback(String downloadFile) {
        MediaController mediaController = new MediaController(this);
        video.setKeepScreenOn(true);
        video.setMediaController(mediaController);
        mediaController.setAnchorView(mediaController);
        video.setVideoURI(Uri.parse(downloadFile));
        video.requestFocus();
        video.setOnPreparedListener(preparedlistner);

    }

    MediaPlayer.OnPreparedListener preparedlistner = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            if (video != null){
                video.start();
            }
        }
    };
}