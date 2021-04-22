package com.example.vocabulatree.ui.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(tableName = "entry_table")
public class Entry implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String word;
    private String language;
    private String translation;
    private String dateAdded;
    private String masteryLevel;
    private String forvoLocation;
    private String personalLocation;


    public Entry(String word, String translation) {
        this.word = word;
        this.translation = translation;
        this.dateAdded = " ";
        this.masteryLevel = " ";
        this.forvoLocation = " ";
        this.personalLocation = " ";
    }

    @Ignore
    public Entry(String word, String language, String translation, String dateAdded, String masteryLevel, String forvoLocation, String personalLocation) {
        this.word = word;
        this.language = language;
        this.translation = translation;
        this.dateAdded = dateAdded;
        this.masteryLevel = masteryLevel;
        this.forvoLocation = forvoLocation;
        this.personalLocation = personalLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getMasteryLevel() {
        return masteryLevel;
    }

    public void setMasteryLevel(String masteryLevel) {
        this.masteryLevel = masteryLevel;
    }

    public String getForvoLocation() {
        return forvoLocation;
    }

    public void setForvoLocation(String forvoLocation) {
        this.forvoLocation = forvoLocation;
    }

    public String getPersonalLocation() {
        return personalLocation;
    }

    public void setPersonalLocation(String personalLocation) {
        this.personalLocation = personalLocation;
    }
}
