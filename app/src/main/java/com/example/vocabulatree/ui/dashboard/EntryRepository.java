package com.example.vocabulatree.ui.dashboard;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.vocabulatree.ui.data.EntryDAO;
import com.example.vocabulatree.ui.data.EntryDatabase;
import com.example.vocabulatree.ui.models.Entry;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EntryRepository
{
	private static EntryRepository instance;
	private final EntryDAO entryDAO;
	private final LiveData<List<Entry>> allEntries;
	private final ExecutorService executorService;
	
	private EntryRepository(Application application)
	{
		EntryDatabase database = EntryDatabase.getInstance(application);
		entryDAO = database.entryDAO();
		allEntries = entryDAO.getAllEntries();
		executorService = Executors.newFixedThreadPool(2);
	}
	
	public static synchronized EntryRepository getInstance(Application application)
	{
		if (instance == null)
		{
			instance = new EntryRepository(application);
		}
		return instance;
	}
	
	public LiveData<List<Entry>> getAllEntries()
	{
		return allEntries;
	}
	
	public void insert(final Entry entry)
	{
		executorService.execute(() -> entryDAO.insert(entry));
	}
	
	public void update(String word, String language, String translation, Date dateAdded, Integer masteryLevel, String forvoLocation, String personalLocation, Integer id)
	{
		executorService.execute(() -> entryDAO.updateEntry(word, language, translation, dateAdded, masteryLevel, forvoLocation, personalLocation, id));
	}
	
	public Future<Entry> getEntry(Integer id)
	{
		Callable<Entry> call = () -> entryDAO.getEntry(id);
		return executorService.submit(call);
	}
	
	public void deleteAllEntries()
	{
		executorService.execute(entryDAO::deleteAllEntries);
	}
	
	public void delete(Entry entry)
	{
		executorService.execute(() -> entryDAO.delete(entry));
	}
	
	public Future<Integer> getCount()
	{
		Callable<Integer> call = entryDAO::getCount;
		return executorService.submit(call);
	}
	
	public Future<Integer> getTotalMastery()
	{
		Callable<Integer> call = entryDAO::getTotalMastery;
		return executorService.submit(call);
	}
}
