package com.example.vocabulatree.ui.editEntry;

import android.app.ActionBar;
import android.content.ContextWrapper;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vocabulatree.R;
import com.example.vocabulatree.ui.forvo.JsonReader;
import com.example.vocabulatree.ui.models.Entry;
import com.example.vocabulatree.ui.record.RecordAudio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class EditEntry extends Fragment
{
	
	EditEntryViewModel viewModel;
	Button saveButton;
	Button deleteButton;
	EditText word;
	EditText definition;
	Button recordButton;
	Button playButton;
	Button stopButton;
	RecordAudio recordAudio;
	TextView dateText;
	TextView mastery;
	Button forvo;
	
	public EditEntry()
	{
		// Required empty public constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.fragment_edit_entry, container, false);
		viewModel = new ViewModelProvider(this).get(EditEntryViewModel.class);
		
		saveButton = root.findViewById(R.id.savebutton);
		deleteButton = root.findViewById(R.id.delete);
		recordButton = root.findViewById(R.id.recordButton);
		stopButton = root.findViewById(R.id.stopButton);
		playButton = root.findViewById(R.id.playButton);
		dateText = root.findViewById(R.id.date);
		mastery = root.findViewById(R.id.mastery);
		forvo = root.findViewById(R.id.forvo);
		
		word = (EditText) root.findViewById(R.id.entryword);
		definition = (EditText) root.findViewById(R.id.definition);
		
		if (getArguments() != null)
		{
			Entry entry = (Entry) getArguments().getSerializable("toEdit");
			dateText.setText(entry.getDateAdded().toString());
			word.setText(entry.getWord());
			definition.setText(entry.getTranslation());
			ContextWrapper cw = new ContextWrapper(getContext());
			recordAudio = new RecordAudio(entry, cw);
			mastery.setText("Mastery points: " + entry.getMasteryLevel());
			
			forvo.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					String url = "https://apifree.forvo.com/action/word-pronunciations/format/json/word/"+entry.getWord()+"/order/rate-desc/language/da/key/58b1fea6def552ade0d67eab8d05547f/";
					try
					{
							JSONObject obj = JsonReader.readJsonFromUrl(url).get();
							JSONArray arr = obj.getJSONArray("items");
							if (arr.length() != 0)
							{
								JSONObject result = arr.getJSONObject(0);
								
								String audioUrl = result.getString("pathmp3");
								viewModel.update(entry.getWord(), entry.getLanguage(), entry.getTranslation(), entry.getDateAdded(), entry.getMasteryLevel(), audioUrl, entry.getPersonalLocation(), entry.getId());
								playForvo(audioUrl);
							}
							else
							{
								Toast.makeText(getContext(),"No pronunciation found :(",Toast.LENGTH_SHORT).show();
							}
						
						
					} catch (IOException | InterruptedException | JSONException | ExecutionException e)
					{
						e.printStackTrace();
					}
				}
			});
			
			saveButton.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Entry toAdd = new Entry(word.getText().toString(), definition.getText().toString());
					Entry toEdit = null;
					try
					{
						toEdit = viewModel.getEntry(entry.getId()).get();
					} catch (ExecutionException | InterruptedException e)
					{
						e.printStackTrace();
					}
					System.out.println(toEdit.getWord() + String.valueOf(toEdit.getId()));
					viewModel.update(toAdd.getWord(), toEdit.getLanguage(), toAdd.getTranslation(), toEdit.getDateAdded(), toEdit.getMasteryLevel(), toEdit.getForvoLocation(), toEdit.getPersonalLocation(), toEdit.getId());
					getActivity().onBackPressed();
				}
			});
			
			recordButton.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					recordAudio.startRecording();
				}
			});
			
			stopButton.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					recordAudio.stopRecording();
					viewModel.update(entry.getWord(), entry.getLanguage(), entry.getTranslation(), entry.getDateAdded(), entry.getMasteryLevel(), entry.getForvoLocation(), recordAudio.getFile().getAbsolutePath(), entry.getId());
					entry.setPersonalLocation(recordAudio.getFile().getAbsolutePath());
				}
			});
			
			playButton.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					if (entry.getPersonalLocation() != null)
					{
						recordAudio.playRecording(new File(entry.getPersonalLocation()));
					}
				}
			});
			
			deleteButton.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					viewModel.delete(entry);
					getActivity().onBackPressed();
				}
			});
		}
		else
		{
			saveButton.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Entry toAdd;
					toAdd = new Entry(word.getText().toString(), definition.getText().toString());
					toAdd.setDateAdded(Calendar.getInstance().getTime());
					viewModel.insert(toAdd);
					getActivity().onBackPressed();
				}
			});
			dateText.setVisibility(View.GONE);
			recordButton.setVisibility(View.GONE);
			playButton.setVisibility(View.GONE);
			mastery.setVisibility(View.GONE);
			forvo.setVisibility(View.GONE);
			stopButton.setVisibility(View.GONE);
			deleteButton.setVisibility(View.GONE);
		}
		return root;
	}
	
	private void playForvo(String audioUrl)
	{
		try
		{
			MediaPlayer mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build());
			mediaPlayer.setDataSource(audioUrl);
			mediaPlayer.prepare();
			mediaPlayer.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
}