package com.example.vocabulatree.ui.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.vocabulatree.R;
import com.example.vocabulatree.ui.dashboard.EntryAdapter;
import com.example.vocabulatree.ui.models.Entry;

import java.io.Serializable;
import java.util.ArrayList;

public class QuizFragment extends Fragment
{

private QuizViewModel quizViewModel;

private Button answer1;
private Button answer2;
private Button answer3;
private TextView score;
private TextView Question;
private Bundle quiz;
private int count=0;
private int scoreNum=0;

Entry question;
ArrayList<Entry> answers;


public View onCreateView(@NonNull LayoutInflater inflater,
						 ViewGroup container, Bundle savedInstanceState)
{
	quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);
	View root = inflater.inflate(R.layout.fragment_quiz, container, false);
	answer1 = root.findViewById(R.id.answer1);
	answer2 = root.findViewById(R.id.answer2);
	answer3 = root.findViewById(R.id.answer3);
	Question = root.findViewById(R.id.question);
	score = root.findViewById(R.id.score);
	return root;
}

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
{
	super.onViewCreated(view, savedInstanceState);
	quizViewModel.getAllEntries().observe(getViewLifecycleOwner(), entries -> {
		quizViewModel.setUpQuiz(entries);
		reloadQuiz();
		answer1.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				checkAnswer(0);
				reloadQuiz();
			}
		});
		
		answer2.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				checkAnswer(1);
				reloadQuiz();
			}
		});
		
		answer3.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				checkAnswer(2);
				reloadQuiz();
			}
		});
	});
	
	
}
private void reloadQuiz()
{
	if(count<5)
	{
		quiz = quizViewModel.playQuiz(count);
		question = (Entry) quiz.getSerializable("Question");
		Question.setText(question.getWord());
		answers = (ArrayList<Entry>) quiz.getSerializable("Answers");
		answer1.setText(answers.get(0).getTranslation());
		answer2.setText(answers.get(1).getTranslation());
		answer3.setText(answers.get(2).getTranslation());
	}
}
private void checkAnswer(Integer buttonNumber)
{
	if(count<5)
	{
		if (quizViewModel.checkAnswer(question, answers.get(buttonNumber).getTranslation()))
		{
			Toast.makeText(getContext(), "CORRECT!", Toast.LENGTH_SHORT).show();
			scoreNum++;
			score.setText(scoreNum + "/5");
		} else
		{
			Toast.makeText(getContext(), "WRONG!", Toast.LENGTH_SHORT).show();
		}
	}
	count++;
}
}