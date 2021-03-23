package com.lk.nxt_pharma.aboutus;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.lk.nxt_pharma.R;

public class Aboutus extends AppCompatActivity {

    VideoView videoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        videoView=findViewById(R.id.videoView);

        String videopath="android.resource://"+getPackageName()+"/"+R.raw.video2;

        Uri uri=Uri.parse(videopath);

        videoView.setVideoURI(uri);


        MediaController mediaController=new MediaController(this);

        videoView.setMediaController(mediaController);

        mediaController.setAnchorView(videoView);








    }
}