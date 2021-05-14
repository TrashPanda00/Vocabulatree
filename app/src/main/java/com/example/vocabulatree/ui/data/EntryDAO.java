package com.example.vocabulatree.ui.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.vocabulatree.ui.models.Entry;

import java.util.Date;
import java.util.List;

@Dao
public interface EntryDAO
{
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
	
	@Query("SELECT COUNT(*) FROM entry_table")
	int getCount();
	
	@Query("SELECT * FROM entry_table WHERE id = :entryid")
	Entry getEntry(Integer entryid);
	
	@Query("SELECT SUM(masteryLevel) FROM entry_table")
	int getTotalMastery();
	
	@Query("UPDATE entry_table SET word = :word, language = :language, translation = :translation, dateAdded = :dateAdded, masteryLevel = :masteryLevel, forvoLocation = :forvoLocation, personalLocation = :personalLocation WHERE id = :id")
	void updateEntry(String word, String language, String translation, Date dateAdded, Integer masteryLevel, String forvoLocation, String personalLocation, Integer id);
	
}
