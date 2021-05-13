package com.example.vocabulatree.ui.quiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.vocabulatree.R;

public class EndQuizFragment extends Fragment
{
	Button playAgain;
	TextView quizResult;
	
public EndQuizFragment()
{
	// Required empty public constructor
}

@Override
public void onCreate(Bundle savedInstanceState)
{
	super.onCreate(savedInstanceState);
	
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
						 Bundle savedInstanceState)
{
	View root = inflater.inflate(R.layout.fragment_end_quiz, container, false);
	playAgain = root.findViewById(R.id.playagain);
	quizResult = root.findViewById(R.id.quizresult);
	int result = getArguments().getInt("score");
	quizResult.setText(result+ "/5");
	return root;
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
{
	super.onViewCreated(view, savedInstanceState);
	NavController nav = Navigation.findNavController(view);
	playAgain.setOnClickListener(new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			nav.navigate(R.id.action_endQuizFragment_to_navigation_quiz);
		}
	});
}
}