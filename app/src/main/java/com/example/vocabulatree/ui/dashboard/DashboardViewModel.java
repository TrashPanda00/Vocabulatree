package com.example.vocabulatree.ui.dashboard;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vocabulatree.ui.models.Entry;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel {

    private final EntryRepository repository;

    public DashboardViewModel(Application application) {
        super(application);
       repository = EntryRepository.getInstance(application);
    }

    public LiveData<List<Entry>> getAllEntries() {
        return repository.getAllEntries();
    }

    public void insert(final Entry entry)
    {
        repository.insert(entry);
    }

    public void deleteAllEntries()
    {
        repository.deleteAllEntries();
    }
}