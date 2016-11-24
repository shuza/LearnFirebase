package com.learn.learfirebase;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class ResultActivity extends AppCompatActivity {
    private VideoView vvShow;
    //private String VIDEO_LINK = "http://192.168.2.100:8888/API/Dennis.3gp";
    private String VIDEO_LINK = "http://ec2-54-186-116-27.us-west-2.compute.amazonaws.com:8888/DemoVideos/Dennis.3gp";
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vvShow = (VideoView) findViewById(R.id.vvShow);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Video Streaming");
        dialog.setMessage("Buffering...");
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);

        /*
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            Toast.makeText(this, "URL: "+extras.getString("URL"), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "no URL", Toast.LENGTH_SHORT).show();
        }
        */
        RunVideo(VIDEO_LINK);

    }

    private void RunVideo(String VIDEO_URL){
        dialog.show();
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(vvShow);
        Uri video = Uri.parse(VIDEO_URL);
        vvShow.setMediaController(mediaController);
        vvShow.setVideoURI(video);
        vvShow.requestFocus();
        vvShow.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                dialog.dismiss();
                vvShow.start();
            }
        });
    }
}
