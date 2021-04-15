package com.example.vocabulatree.ui.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.vocabulatree.ui.models.Entry;

import java.util.List;

@Dao
public interface EntryDAO {
    @Insert
    void insert(Entry entry);

    @Update
    void update(Entry entry);

    @Delete
    void delete(Entry entry);

    @Query("DELETE FROM entry_table")
    void deleteAllEntries();

    @Query("SELECT * FROM entry_table")
    LiveData<List<Entry>> getAllEntries();

}
