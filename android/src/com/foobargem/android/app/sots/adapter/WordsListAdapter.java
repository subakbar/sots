package com.foobargem.android.app.sots.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.foobargem.android.app.sots.R;
import com.foobargem.android.app.sots.model.Word;

public class WordsListAdapter extends BaseAdapter {

	private int layoutResource;
	private ArrayList<Word> wordsList;
	private LayoutInflater inflater;


	public WordsListAdapter (Context context, int layoutResource, ArrayList<Word> wordsList) {
		this.layoutResource = layoutResource;
		this.wordsList = wordsList;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	
	@Override
	public int getCount() {
		return wordsList.size();
	}

	@Override
	public Word getItem(int position) {
		return wordsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    ViewHolder holder;
	    Word word = getItem(position);

		if (convertView == null) {
			convertView = inflater.inflate(layoutResource, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.subjectView = (TextView) convertView.findViewById(R.id.word_item_subject);
            holder.paragraphView = (TextView) convertView.findViewById(R.id.word_item_paragraph);

            convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.subjectView.setText(word.subject);
		holder.paragraphView.setText(word.paragraph);
		
		if (word.isMemorized()) {
			convertView.setBackgroundColor(0xFFEEFFCC);
		} else {
			convertView.setBackgroundColor(0x00FFFFFF);
		}

		return convertView;
	}

	
	
	private class ViewHolder {
		public TextView subjectView;
		public TextView paragraphView;
	}

}
