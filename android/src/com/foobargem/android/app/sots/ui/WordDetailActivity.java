package com.foobargem.android.app.sots.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.foobargem.android.app.sots.R;
import com.foobargem.android.app.sots.helper.WordDBHelper;
import com.foobargem.android.app.sots.model.Word;

public class WordDetailActivity extends BaseActivity {

	private TextView noteView;
	private TextView paragraphView;
	private CheckBox memorizedCtrl;
	private WordDBHelper wordDBHelper;
	private Word word;

	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.word_detail);
		
		Bundle bundle = getIntent().getExtras();
		word = (Word) bundle.get("word");
		
		getActivityHelper().setupActionBar(word.subject, false, false);

		wordDBHelper = new WordDBHelper(getApplicationContext());

		noteView = (TextView) findViewById(R.id.word_detail_note);
		noteView.setText(word.note);
		
		paragraphView = (TextView) findViewById(R.id.word_detail_paragraph);
		paragraphView.setText(word.paragraph);
		
		memorizedCtrl = (CheckBox) findViewById(R.id.word_detail_memorized_ctrl);
		memorizedCtrl.setChecked(word.isMemorized());
		memorizedCtrl.setOnCheckedChangeListener(new CheckedChangeListener());

	}

	
	private class CheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			word.memorized = (isChecked ? 1 : 0);
			wordDBHelper.updateMemorized(word.id, isChecked);

			Intent intent = getIntent();
			intent.putExtra("updated_word", word);
			setResult(Activity.RESULT_OK, intent);
		}		
	}
	
}
