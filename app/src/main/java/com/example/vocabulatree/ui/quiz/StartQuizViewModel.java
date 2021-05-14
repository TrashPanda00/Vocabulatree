package com.example.vocabulatree.ui.quiz;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.vocabulatree.ui.dashboard.EntryRepository;
import com.example.vocabulatree.ui.models.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class StartQuizViewModel extends AndroidViewModel
{

private final EntryRepository repository;


public StartQuizViewModel(Application application)
{
	super(application);
	repository = EntryRepository.getInstance(application);
}

public int getCount()
{
	Future<Integer> result = repository.getCount();
	try
	{
		return result.get();
	} catch (ExecutionException | InterruptedException e)
	{
		e.printStackTrace();
	}
	return 0;
}
}
