package com.example.vocabulatree.ui.editEntry;

import android.app.ActionBar;
import android.content.ContextWrapper;
import android.graphics.Color;
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
import android.widget.ImageButton;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	RecordAudio recordAudio;
	TextView dateText;
	TextView masteryplaceholder;
	ImageButton deletepersonal;
	TextView mastery;
	Button forvo;
	String state = "ready";
	
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
		deletepersonal = root.findViewById(R.id.deletepersonal);
		saveButton = root.findViewById(R.id.savebutton);
		deleteButton = root.findViewById(R.id.delete);
		recordButton = root.findViewById(R.id.recordButton);
		dateText = root.findViewById(R.id.date);
		mastery = root.findViewById(R.id.mastery2);
		masteryplaceholder = root.findViewById(R.id.mastery);
		forvo = root.findViewById(R.id.forvo);
		
		word = (EditText) root.findViewById(R.id.entryword);
		definition = (EditText) root.findViewById(R.id.definition);
		
		if (getArguments() != null)
		{
			Entry entry = (Entry) getArguments().getSerializable("toEdit");
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy, hh:mm a");
			dateText.setText("Date added: "+format.format(entry.getDateAdded()));
			word.setText(entry.getWord());
			definition.setText(entry.getTranslation());
			ContextWrapper cw = new ContextWrapper(getContext());
			recordAudio = new RecordAudio(entry, cw);
			if(entry.getMasteryLevel() < 25)
			{
				mastery.setTextColor(Color.parseColor("#0A9A9A"));
				mastery.setText("New ("+entry.getMasteryLevel()+" points)");
			}
			else if(entry.getMasteryLevel() < 50)
			{
				mastery.setTextColor(Color.parseColor("#B2974F"));
				mastery.setText("Known ("+entry.getMasteryLevel()+" points)");
			}
			else if(entry.getMasteryLevel() >= 100)
			{
				mastery.setTextColor(Color.parseColor("#EF7C3B"));
				mastery.setText("Mastered ("+entry.getMasteryLevel()+" points)");
			}
			
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
			
			deletepersonal.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					entry.setPersonalLocation(" ");
					viewModel.update(entry.getWord(), entry.getLanguage(), entry.getTranslation(), entry.getDateAdded(), entry.getMasteryLevel(), entry.getForvoLocation(), " ", entry.getId());
					recordButton.setText("Record Personal");
					state = "ready";
					recordAudio = new RecordAudio(entry, cw);
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
			
			if(!entry.getPersonalLocation().equals(" "))
			{
				recordButton.setText("Play Personal");
			}
			else
			{
				recordButton.setText("Record Personal");
			}
			
			recordButton.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					if(!entry.getPersonalLocation().equals(" "))
					{
						state = "playable";
						recordAudio.playRecording(new File(entry.getPersonalLocation()));
					}
					else if(state.equals("ready"))
					{
						recordAudio.startRecording();
						recordButton.setText("Stop Recording");
						state = "recording";
					}
					else
					{
						recordButton.setText("Play Personal");
						recordAudio.stopRecording();
						viewModel.update(entry.getWord(), entry.getLanguage(), entry.getTranslation(), entry.getDateAdded(), entry.getMasteryLevel(), entry.getForvoLocation(), recordAudio.getFile().getAbsolutePath(), entry.getId());
						entry.setPersonalLocation(recordAudio.getFile().getAbsolutePath());
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
			deletepersonal.setVisibility(View.GONE);
			masteryplaceholder.setVisibility(View.GONE);
			mastery.setVisibility(View.GONE);
			forvo.setVisibility(View.GONE);
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