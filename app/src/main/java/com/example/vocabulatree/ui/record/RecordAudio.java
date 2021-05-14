package com.example.vocabulatree.ui.record;

import android.content.Context;
import android.content.ContextWrapper;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import com.example.vocabulatree.ui.dashboard.EntryRepository;
import com.example.vocabulatree.ui.models.Entry;

import java.io.File;
import java.io.IOException;

public class RecordAudio
{
	
	MediaRecorder mediaRecorder = new MediaRecorder();
	Entry entry;
	ContextWrapper cw;
	File directory;
	File file;
	
	
	public RecordAudio(Entry entry, ContextWrapper cw)
	{
		this.entry = entry;
		directory = cw.getDir("audioDir", Context.MODE_PRIVATE);
		file = new File(directory, this.entry.getId() + ".3gp");
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mediaRecorder.setOutputFile(file.getPath());
	}
	
	public void startRecording()
	{
		try
		{
			mediaRecorder.prepare();
			mediaRecorder.start();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void stopRecording()
	{
		mediaRecorder.stop();
		mediaRecorder.release();
	}
	
	public File getFile()
	{
		return file;
	}
	
	public void playRecording(File file)
	{
		MediaPlayer mediaPlayer = new MediaPlayer();
		try
		{
			mediaPlayer.setDataSource(file.getPath());
			mediaPlayer.prepare();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		mediaPlayer.start();
	}
	
}
