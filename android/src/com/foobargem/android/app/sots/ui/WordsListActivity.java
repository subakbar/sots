package com.foobargem.android.app.sots.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.foobargem.android.app.sots.R;
import com.foobargem.android.app.sots.adapter.WordsListAdapter;
import com.foobargem.android.app.sots.helper.WordDBHelper;
import com.foobargem.android.app.sots.model.Word;

public class WordsListActivity extends BaseActivity {
	
	private ArrayList<Word> wordsList;
	private ListView wordsListView;
	private WordsListAdapter adapter;
	private WordDBHelper wordDBHelper;
	private String currentTagName;

	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.words_list);
		
		wordDBHelper = new WordDBHelper(getApplicationContext());

		Bundle bundle = getIntent().getExtras();
		currentTagName = bundle.getString("tag_name");

		getActivityHelper().setupActionBar(currentTagName, false, false);
		
		makeWordsList();
		
		adapter = new WordsListAdapter(this, R.layout.word_item, wordsList);

		wordsListView = (ListView) findViewById(R.id.words_list_container);
		wordsListView.setAdapter(adapter);
		wordsListView.setOnItemClickListener(new ItemClickListener());
	}
	
	
	private void makeWordsList () {
		wordsList = new ArrayList<Word>();
		wordsList = wordDBHelper.wordListByTagName(currentTagName);
	}
	
	
	private class ItemClickListener implements AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
			Intent intent = new Intent(getApplicationContext(), WordDetailActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("word", wordsList.get((int) id));
			startActivityForResult(intent, 0);
		}		
	}

	
	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			Word updatedWord = (Word) bundle.get("updated_word");
			for (Word w : wordsList) {
				if (w.id == updatedWord.id) w.memorized = updatedWord.memorized;
			}

			adapter.notifyDataSetChanged();
			//Log.d("RESULT", "OK");
		}
	}

	@Override
	protected void onDestroy () {
		wordDBHelper.close();
		super.onDestroy();
	}
	

}