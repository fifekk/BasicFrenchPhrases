package com.filipwiatrowski.sounddemo;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.fart);
        //setting up audioManager to work with system audio
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // max volume for music stream getStreamMaxVolume
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);// current volume getStreamVolume
        SeekBar volumeControl = (SeekBar) findViewById(R.id.seekBar);
        volumeControl.setMax(maxVolume); //setting maxVolume as maximum volume is in system
        volumeControl.setProgress(currentVolume);
        //using finger by user;  this is called when user start or stops changing seekbar
        volumeControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            //when the seekBar is used
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Log.i("SeekBar value", Integer.toString(progress));

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        final SeekBar progressControl = (SeekBar) findViewById(R.id.soundProgress);
        progressControl.setMax(mediaPlayer.getDuration());//set max to audio file duration
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //0 and 1000 means thath we want to run code immediately and update it every second
                progressControl.setProgress(mediaPlayer.getCurrentPosition());
            }
        },0,100);
        progressControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mediaPlayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.start();
            }
        });
    }

    public void play (View view){
        //mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }
    public void pause (View view){
        mediaPlayer.pause();
    }
}
