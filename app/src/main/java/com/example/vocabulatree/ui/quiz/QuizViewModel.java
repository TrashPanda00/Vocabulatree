package com.example.vocabulatree.ui.quiz;

import android.app.Application;
import android.os.Bundle;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vocabulatree.ui.dashboard.EntryRepository;
import com.example.vocabulatree.ui.models.Entry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizViewModel extends AndroidViewModel
{

private final EntryRepository repository;

private ArrayList<Entry> selectedEntries = new ArrayList<Entry>();
private ArrayList<Entry> possibleAnswers = new ArrayList<Entry>();


public QuizViewModel(Application application)
{
	super(application);
	repository = EntryRepository.getInstance(application);
}

public LiveData<List<Entry>> getAllEntries() {
	return repository.getAllEntries();
}

public void setUpQuiz(List<Entry> entries)
{
	if(possibleAnswers.isEmpty())
	possibleAnswers.addAll(entries);
	for (Entry entry: possibleAnswers)
	{
		System.out.println(entry.getTranslation() + "bitch");
	}
	selectedEntries.addAll(pickNRandom(possibleAnswers,5));
}

private static ArrayList<Entry> pickNRandom(ArrayList<Entry> list, int n)
{
	ArrayList<Entry> copy = new ArrayList<Entry>();
	copy.addAll(list);
	Collections.shuffle(copy);
	ArrayList<Entry> result = new ArrayList<>();
	result.addAll(n > copy.size() ? copy.subList(0, copy.size()) : copy.subList(0, n));
	return result;
}

private ArrayList<Entry> getAnswers(Integer i)
{
	ArrayList<Entry> answers = new ArrayList<>();
	answers.addAll(pickNRandom(possibleAnswers, 2));
    answers.add(selectedEntries.get(i));
    Collections.shuffle(answers);
    return answers;
}

private Entry getQuestion(Integer i)
{
    return selectedEntries.get(i);
}

public Bundle playQuiz(Integer count)
{
		possibleAnswers.remove(getQuestion(count));
	System.out.println("REMOOOOOOOOVEEEEEEEEEEEEEEEEEEEEEEEEED:" + getQuestion(count).getTranslation());
	for (Entry entry: possibleAnswers)
	{
		System.out.println(entry.getTranslation());
	}
		Bundle bundle = new Bundle();
		bundle.putSerializable("Question", getQuestion(count));
		bundle.putSerializable("Answers", getAnswers(count));
		count++;
		return bundle;
}

public boolean checkAnswer(Entry question, String translation)
{
	return question.getTranslation().equals(translation);
}

public void increaseMastery(Entry entry)
{
		repository.update(entry.getWord(),entry.getLanguage(),entry.getTranslation(),entry.getDateAdded(),entry.getMasteryLevel()+1,entry.getForvoLocation(),entry.getPersonalLocation(),entry.getId());
}

}