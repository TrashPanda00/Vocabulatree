package com.example.vocabulatree.ui.record;

import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;

public class RecordAudio {

    MediaRecorder mediaRecorder = new MediaRecorder();
    ContextWrapper cw;
    File directory;
    File file;


    public RecordAudio(String filename, Context context)
    {
        ContextWrapper cw = new ContextWrapper(context);
        directory = cw.getDir("audioDir", Context.MODE_PRIVATE);
        file = new File(directory, "UniqueFileName" + ".3gp");
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(file.getPath());
    }

    public void startRecording()
    {
        System.out.println("recording");
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopRecording()
    {
        System.out.println("stopped");
        mediaRecorder.stop();
        mediaRecorder.release();
    }

    public void playRecording()
    {
        System.out.println("playing");
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

}
