package com.example.vocabulatree.ui.editEntry;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vocabulatree.ui.dashboard.EntryRepository;
import com.example.vocabulatree.ui.models.Entry;

import java.util.Date;
import java.util.concurrent.Future;

public class EditEntryViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private final EntryRepository repository;

    public EditEntryViewModel(Application application) {
        super(application);
        repository = EntryRepository.getInstance(application);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void insert(final Entry entry)
    {
        repository.insert(entry);
    }

    public Future<Entry> getEntry(Integer id) {return repository.getEntry(id);}

    public void update(String word, String language, String translation, Date dateAdded, String masteryLevel, String forvoLocation, String personalLocation, Integer id) {repository.update(word, language, translation, dateAdded, masteryLevel, forvoLocation, personalLocation, id);}
}