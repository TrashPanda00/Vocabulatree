package com.example.vocabulatree.ui.editEntry;

import android.Manifest;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.vocabulatree.R;
import com.example.vocabulatree.ui.models.Entry;
import com.example.vocabulatree.ui.record.RecordAudio;
import com.example.vocabulatree.ui.record.RecordState;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditEntry#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditEntry extends Fragment {

    EditEntryViewModel viewModel;
    Button saveButton;
    EditText word;
    EditText definition;
    Button recordButton;
    Button playButton;
    Button stopButton;
    RecordAudio recordAudio;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditEntry() {
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
    public static EditEntry newInstance(String param1, String param2) {
        EditEntry fragment = new EditEntry();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_entry, container, false);
        viewModel = new ViewModelProvider(this).get(EditEntryViewModel.class);
        saveButton = root.findViewById(R.id.savebutton);
        word = (EditText) root.findViewById(R.id.entryword);
        definition = (EditText) root.findViewById(R.id.definition);


        try {
            Entry entry = (Entry) getArguments().getSerializable("toEdit");

            word.setText(entry.getWord());
            definition.setText(entry.getTranslation());

            recordAudio = new RecordAudio(String.valueOf(entry.getId()),getContext());

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Entry toAdd = new Entry(word.getText().toString(),definition.getText().toString());
                    Entry toEdit = null;
                    try {
                        toEdit = viewModel.getEntry(entry.getId()).get();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(toEdit.getWord() + String.valueOf(toEdit.getId()));
                    viewModel.update(toAdd.getWord(),toAdd.getLanguage(),toAdd.getTranslation(),toAdd.getDateAdded(),toAdd.getMasteryLevel(),toAdd.getForvoLocation(),toAdd.getPersonalLocation(),toEdit.getId());
                }
            });

            recordButton = root.findViewById(R.id.recordButton);

            recordButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("PRESSED RECORD");
                    recordAudio.startRecording();
                }
            });

            stopButton = root.findViewById(R.id.stopButton);
            stopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recordAudio.stopRecording();
                }
            });

            playButton = root.findViewById(R.id.playButton);
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recordAudio.playRecording();
                }
            });
        }
        catch (NullPointerException nullPointerException)
        {
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Entry toAdd;
                    toAdd = new Entry(word.getText().toString(),definition.getText().toString());
                    viewModel.insert(toAdd);
                }
            });
        }


        return root;
    }

}