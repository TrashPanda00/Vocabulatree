package com.example.vocabulatree.ui.editEntry;

import android.content.ContextWrapper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vocabulatree.R;
import com.example.vocabulatree.ui.models.Entry;
import com.example.vocabulatree.ui.record.RecordAudio;

import java.io.File;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditEntry#newInstance} factory method to
 * create an instance of this fragment.
 */
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private static final String ARG_PARAM1 = "param1";
private static final String ARG_PARAM2 = "param2";


// TODO: Rename and change types of parameters
private String mParam1;
private String mParam2;

public EditEntry()
{
	// Required empty public constructor
}

/**
 * Use this factory method to create a new instance of
 * this fragment using the provided parameters.
 *
 * @param param1 Parameter 1.
 * @param param2 Parameter 2.
 * @return A new instance of fragment EditEntry.
 */
// TODO: Rename and change types and number of parameters
public static EditEntry newInstance(String param1, String param2)
{
	EditEntry fragment = new EditEntry();
	Bundle args = new Bundle();
	args.putString(ARG_PARAM1, param1);
	args.putString(ARG_PARAM2, param2);
	fragment.setArguments(args);
	return fragment;
}

@Override
public void onCreate(Bundle savedInstanceState)
{
	super.onCreate(savedInstanceState);
	if (getArguments() != null)
	{
		mParam1 = getArguments().getString(ARG_PARAM1);
		mParam2 = getArguments().getString(ARG_PARAM2);
	}
}


@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
						 Bundle savedInstanceState)
{
	// Inflate the layout for this fragment
	View root = inflater.inflate(R.layout.fragment_edit_entry, container, false);
	viewModel = new ViewModelProvider(this).get(EditEntryViewModel.class);
	
	saveButton = root.findViewById(R.id.savebutton);
	deleteButton = root.findViewById(R.id.delete);
    recordButton = root.findViewById(R.id.recordButton);
    stopButton = root.findViewById(R.id.stopButton);
    playButton = root.findViewById(R.id.playButton);
    dateText = root.findViewById(R.id.date);
	
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
			    if(entry.getPersonalLocation() != null)
				    recordAudio.playRecording(new File(entry.getPersonalLocation()));
			}
		});
		
		deleteButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				viewModel.delete(entry);
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
			}
		});
		dateText.setVisibility(View.GONE);
		recordButton.setVisibility(View.GONE);
		playButton.setVisibility(View.GONE);
		stopButton.setVisibility(View.GONE);
		deleteButton.setVisibility(View.GONE);
	}
	
	
	return root;
}

}