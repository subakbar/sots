package com.foobargem.android.app.sots.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.foobargem.android.app.sots.R;
import com.foobargem.android.app.sots.model.Word;

public class WordInputMemoryActivity extends BaseActivity {

	private Button resetButton;
	private TextView noteView;
	private TextView textSize;
	private EditText textInput;
	
	private Vibrator vibrator;
	
	private Word word;
	private ArrayList<String> divideWord;
	private ArrayList<Integer> hiddenWord;
	String noteViewString;
	int currentLevel;
	int textInputMax;
	
	
	// TextInput 입력이벤트처리 : 입력된 어절과 숨겨진 어절을 비교한다.
	TextWatcher watcher = new TextWatcher() {
		public void afterTextChanged(Editable s) {
		}
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// 입력된 어절과 숨겨진 어절이 같을 경우
			if (textInput.getText().toString().equals(divideWord.get(hiddenWord.get(0)))) {
				// 입력된 내용을 삭제한다.
				textInput.setText(null);
				
				// 숨겨진 어절을 삭제한다.
				hiddenWord.remove(0);
				
				// 숨겨진 어절이 없으면 다음 레벨로 넘어간다.
				if (hiddenWord.size() == 0)
					nextButton(null);
				
				// 마지막단계가 아닌경우, textSize(TextView)와 textInput(EditText)를 초기화한다.
				if (currentLevel != 6) {
					textInputMax = divideWord.get(hiddenWord.get(0)).length();
					textSize.setText(textInputMax + " 자");
					
					// noteView를 설정한다.
					setNoteViewString();
				}
			}
			// 입력된 어절이 숨겨진 어절보다 길이가 긴 경우
			else if (textInput.length() > divideWord.get(hiddenWord.get(0)).length()) {
				// 200ms간 진동발생
				vibrator.vibrate(200);
				
				// 입력된 내용을 삭제한다.
				textInput.setText(null);
			}
		}
	};

	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.word_input_memory);
		
		Bundle bundle = getIntent().getExtras();
		word = (Word) bundle.get("word");
		divideWord = new ArrayList<String>();
		noteViewString = new String(word.note);
		currentLevel = 1;
		
		resetButton = (Button) findViewById(R.id.word_input_memory_reset_button);
		noteView = (TextView) findViewById(R.id.word_input_memory_detail_note);
		textSize = (TextView) findViewById(R.id.word_input_memory_text_size);
		textInput = (EditText) findViewById(R.id.word_input_memory_text_input);
		textInput.addTextChangedListener(watcher);
		
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		// 구절을 타이틀로 사용한다.
		getActivityHelper().setupActionBar(word.paragraph, false, false);

		// word를 공백을 기준으로 분리하여 divideWord에 저장한다.
		int index;
		while(true) {
			// 공백을 찾는다, 공백이 없으면 -1이 된다.
			index = noteViewString.indexOf(" ");
			
			if (index == -1) {
				divideWord.add(noteViewString);
				break;
			}
			else {
				// 콤마를 제거한다.
				if (noteViewString.substring(index-1, index).equals(","))
					divideWord.add(noteViewString.substring(0, index-1));
				else
					divideWord.add(noteViewString.substring(0, index));
				
				noteViewString = noteViewString.substring(index+1);
			}
		}
		
		// 1단계로  noteViewString를 초기화한다.
		resetNoteViewString(currentLevel);
	}
	
	// 단계별로 noteViewString를 초기화한다.
	public void resetNoteViewString(int level) {
		int i, j;
		int randomNum;
		Random random = new Random();
		int wordSize = divideWord.size();
		int divideCount;
		hiddenWord = new ArrayList<Integer>();
		
		// 4단계까지 숨겨질 어절을 계산한다.
		if (level < 5) {
			divideCount = (int)(divideWord.size() / 5.0 * level);
			
			// 숨겨질 어절을 divideCount만큼 찾는다.
			i = 0;
			while(i < divideCount) {
				randomNum = random.nextInt(wordSize);
				
				if (hiddenWord.contains(randomNum) == false) {
					hiddenWord.add(randomNum);
					i++;
				}
			}
			
			// hiddenWord를 내림차순한다.
			Collections.sort(hiddenWord);
		}
		// 5단계는 모든 어절을 숨긴다.
		else {
			// hiddenWord에 모든 어절을 추가한다.
			for (i=0; i < divideWord.size(); i++)
				hiddenWord.add(i);
		}
		
		// NoteViewString을 설정한다.
		setNoteViewString();
		
		// textSize(TextView)와 textInput(EditText)를 초기화한다.
		textInputMax = divideWord.get(hiddenWord.get(0)).length();
		textSize.setText(textInputMax + " 자");
	}
	
	// NoteViewString을 설정한다.
	public void setNoteViewString() {
		int i, j;
		int wordSize = divideWord.size();
		
		noteViewString = "";
		for (i = 0; i < wordSize; i++) { 
			// 보이지는 어절을 표시한다. 
			if (hiddenWord.contains(i) == false) {
				noteViewString += "   " + divideWord.get(i);
			}
			// 숨겨질 어절은 -로 표시한다.
			else {
				noteViewString += "   ";
				
				for (j = 0; j < divideWord.get(i).length(); j++)
					noteViewString += " - ";
			}
		}
		
		// 화면에 보일 noteViewString의 시작공백을 제거한다.
		noteViewString = noteViewString.substring(3);
		// 화면에 noteViewString을 출력한다.
		noteView.setText(noteViewString);
	}
	
	// '←' 버튼 : 전단계로 변경한다, 만일 1단계시 액티비티를 종료한다. 
	public void preButton(View view) {
		currentLevel -= 1;
		
		if (currentLevel == 0) {
			finish();
		}
		else {
			resetNoteViewString(currentLevel);
			noteView.setText(noteViewString);
			resetButton.setText(currentLevel + "단계 다시하기");
		}
	}
	
	// 리셋버튼 : 현재단계에서 숨길말씀을 초기화한다.
	public void resetButton(View view) {
		resetNoteViewString(currentLevel);
	}
	
	// '→' 버튼 : 다음단계로 변경한다, 만일 10단계시 '암송했음'으로 체크되게 액티비티를 종료한다.
	public void nextButton(View view) {
		currentLevel += 1;
		
		if (currentLevel == 6) {
			Intent intent = getIntent();
			intent.putExtra("updated_word", word);
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
		else {
			resetNoteViewString(currentLevel);
			noteView.setText(noteViewString);
			resetButton.setText(currentLevel + "단계 다시하기");
		}
	}
}