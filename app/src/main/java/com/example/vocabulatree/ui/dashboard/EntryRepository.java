package com.example.vocabulatree.ui.dashboard;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.vocabulatree.ui.data.EntryDAO;
import com.example.vocabulatree.ui.data.EntryDatabase;
import com.example.vocabulatree.ui.models.Entry;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EntryRepository {
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
        if(instance == null)
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

    public void deleteAllEntries()
    {
        executorService.execute(entryDAO::deleteAllEntries);
    }
}
