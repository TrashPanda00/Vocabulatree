package com.example.vocabulatree.ui.tree;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.vocabulatree.ui.dashboard.EntryRepository;

import java.util.concurrent.ExecutionException;

public class TreeViewModel extends AndroidViewModel
{
	private final EntryRepository repository;
	
	public TreeViewModel(@NonNull Application application)
	{
		super(application);
		repository = EntryRepository.getInstance(application);
	}
	
	public int getTotalMastery()
	{
		try
		{
			return repository.getTotalMastery().get();
		} catch (ExecutionException e)
		{
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return 0;
	}
}
