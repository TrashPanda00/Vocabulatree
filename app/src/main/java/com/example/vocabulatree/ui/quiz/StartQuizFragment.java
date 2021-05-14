package com.example.vocabulatree.ui.quiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.vocabulatree.R;

public class StartQuizFragment extends Fragment
{
	StartQuizViewModel viewModel;
	
	TextView numWords;
	Button playQuiz;
	
	public StartQuizFragment()
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
		
		View root = inflater.inflate(R.layout.fragment_start_quiz, container, false);
		viewModel = new ViewModelProvider(this).get(StartQuizViewModel.class);
		numWords = root.findViewById(R.id.numWords);
		playQuiz = root.findViewById(R.id.startQuiz);
		int count = viewModel.getCount();
		numWords.setText(count + " words");
		if (count < 10)
		{
			playQuiz.setVisibility(View.GONE);
		}
		return root;
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		NavController nav = Navigation.findNavController(view);
		playQuiz.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				nav.navigate(R.id.action_startQuizFragment_to_navigation_quiz);
			}
		});
	}
}