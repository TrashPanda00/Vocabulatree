package com.example.vocabulatree.ui.dashboard;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vocabulatree.R;
import com.example.vocabulatree.ui.models.Entry;

import java.util.ArrayList;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder>
{
	private ArrayList<Entry> mEntries;
	final private OnListItemClickListener mOnListItemClickListener;
	
	EntryAdapter(ArrayList<Entry> entries, OnListItemClickListener listItemClickListener)
	{
		mEntries = entries;
		mOnListItemClickListener = listItemClickListener;
	}
	
	@NonNull
	@Override
	
	public EntryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.word_entry_list_item, parent, false);
		return new ViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull EntryAdapter.ViewHolder holder, int position)
	{
		holder.word.setText(mEntries.get(position).getWord());
		holder.translation.setText(mEntries.get(position).getTranslation());
		if(mEntries.get(position).getMasteryLevel() < 25)
		{
			holder.word.setTextColor(Color.parseColor("#0A9A9A"));
			holder.translation.setTextColor(Color.parseColor("#0A9A9A"));
			holder.dash.setTextColor(Color.parseColor("#0A9A9A"));
		}
		else if(mEntries.get(position).getMasteryLevel() < 50)
		{
			holder.word.setTextColor(Color.parseColor("#B2974F"));
			holder.translation.setTextColor(Color.parseColor("#B2974F"));
			holder.dash.setTextColor(Color.parseColor("#B2974F"));
		}
		else if(mEntries.get(position).getMasteryLevel() >= 100)
		{
			holder.word.setTextColor(Color.parseColor("#EF7C3B"));
			holder.translation.setTextColor(Color.parseColor("#EF7C3B"));
			holder.dash.setTextColor(Color.parseColor("#EF7C3B"));
		}
	}
	
	@Override
	public int getItemCount()
	{
		return mEntries.size();
	}
	
	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	{
		
		TextView word;
		TextView translation;
		TextView dash;
		
		public ViewHolder(@NonNull View itemView)
		{
			super(itemView);
			word = itemView.findViewById(R.id.word);
			translation = itemView.findViewById(R.id.translation);
			dash = itemView.findViewById(R.id.dash);
			itemView.setOnClickListener(this);
		}
		
		@Override
		public void onClick(View v)
		{
			mOnListItemClickListener.onListItemClick(getAdapterPosition());
		}
	}
	
	public interface OnListItemClickListener
	{
		void onListItemClick(int clickedItemIndex);
	}
}
