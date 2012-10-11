package com.foobargem.android.app.sots.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
	
	// '암송하기'버튼을 선택시 WordMemoryActivity로 이동한다.
	public void memoryButton(View view) {
		Intent intent = new Intent(getApplicationContext(), WordMemoryActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("word", word);
		startActivityForResult(intent, 0);
	}
	
	// WordMemoryActivity에서 10단계를 모두암송시 '암송했음'이 체크된다.
	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK)
			memorizedCtrl.setChecked(true);
	}
}
