package com.example.vocabulatree.ui.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.vocabulatree.ui.models.Entry;

@Database(entities = {Entry.class}, version = 3)
@TypeConverters({Converters.class})
public abstract class EntryDatabase extends RoomDatabase
{
	private static EntryDatabase instance;
	
	public abstract EntryDAO entryDAO();
	
	public static synchronized EntryDatabase getInstance(Context context)
	{
		if (instance == null)
		{
			instance = Room.databaseBuilder(context, EntryDatabase.class, "entry_database").fallbackToDestructiveMigration().build();
		}
		return instance;
	}
	
}
