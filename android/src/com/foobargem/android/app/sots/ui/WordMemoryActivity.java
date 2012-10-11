package com.foobargem.android.app.sots.ui;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.foobargem.android.app.sots.R;
import com.foobargem.android.app.sots.model.Word;

public class WordMemoryActivity extends BaseActivity {

	private Button resetButton;
	private TextView noteView;
	private Word word;
	private ArrayList<String> divideWord;
	String noteViewString;
	int currentLevel;

	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.word_memory);
		
		Bundle bundle = getIntent().getExtras();
		word = (Word) bundle.get("word");
		divideWord = new ArrayList<String>();
		noteViewString = new String(word.note);
		currentLevel = 1;
		
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
				divideWord.add(noteViewString.substring(0, index));
				noteViewString = noteViewString.substring(index+1);
			}
		}
		
		// 1단계로  noteViewString를 초기화한다.
		resetNoteViewString(currentLevel);
		
		resetButton = (Button) findViewById(R.id.word_memory_reset_button);
		noteView = (TextView) findViewById(R.id.word_detail_note);
		noteView.setText(noteViewString);
	}
	
	// 단계별로 noteViewString를 초기화한다.
	public void resetNoteViewString(int level) {
		int i, j;
		int randomNum;
		Random random = new Random();
		int WordSize = divideWord.size();
		int divideCount;
		ArrayList<Integer> hiddenWord = new ArrayList<Integer>();
		
		// 4단계까지는 숨겨질 어절로 계산한다.
		if (level < 5) {
			divideCount = (int)(divideWord.size() / 10.0 * level);
			
			// 숨겨질 어절을 divideCount만큼 찾는다.
			i = 0;
			while(i < divideCount) {
				randomNum = random.nextInt(WordSize);
				
				if (hiddenWord.contains(randomNum) == false) {
					hiddenWord.add(randomNum);
					i++;
				}
			}
			
			// 화면에 보일 noteViewString을 만든다.
			noteViewString = "";
			for (i = 0; i < WordSize; i++) { 
				// 보이지는 어절을 표시한다. 
				if (hiddenWord.contains(i) == false) {
					noteViewString += "   " + divideWord.get(i);
				}
				// 숨겨질 어절은 *로 표시한다.
				else {
					noteViewString += "   ";
					
					for (j = 0; j < divideWord.get(i).length(); j++)
						noteViewString += " - ";
				}
			}
		}
		// 5단계부터 9단계까지는 보여질 어절을 계산한다.
		else if (level < 10) {
			divideCount = (int)(divideWord.size() / 10.0 * (10 - level));
			
			// 보여질 어절을 divideCount만큼 찾는다.
			i = 0;
			while(i < divideCount) {
				randomNum = random.nextInt(WordSize);
				
				if (hiddenWord.contains(randomNum) == false) {
					hiddenWord.add(randomNum);
					i++;
				}
			}
			
			// 화면에 보일 noteViewString을 만든다.
			noteViewString = "";
			for (i = 0; i < WordSize; i++) { 
				// 보이지는 어절을 표시한다. 
				if (hiddenWord.contains(i) == true)
					noteViewString += "   " + divideWord.get(i);
				// 숨겨질 어절은 *로 표시한다.
				else {
					noteViewString += "   ";
					for (j = 0; j < divideWord.get(i).length(); j++)
						noteViewString += " - ";
				}
			}
		}
		// 10단계는 모든 어절을 숨긴다.
		else {
			noteViewString = "";
			for (i=0; i < WordSize; i++) {
				noteViewString += "   ";
				for (j = 0; j < divideWord.get(i).length(); j++)
					noteViewString += " · ";
			}
		}
		
		// 화면에 보일 noteViewString의 시작공백을 제거한다.
		noteViewString = noteViewString.substring(3);
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
		noteView.setText(noteViewString);
	}
	
	// '→' 버튼 : 다음단계로 변경한다, 만일 10단계시 '암송했음'으로 체크되게 액티비티를 종료한다.
	public void nextButton(View view) {
		currentLevel += 1;
		
		if (currentLevel == 11) {
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