package com.foobargem.android.app.sots.helper;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.foobargem.android.app.sots.model.Word;

public class WordDBHelper {

	private static final String WORDS_TABLE = "words";

	private SQLiteDatabase db;
	private Context context;
	private DatabaseHelper dbHelper;

	
	public WordDBHelper (Context _context) {
		context = _context;
		try {
			dbHelper = new DatabaseHelper(context);
			db = dbHelper.getWritableDatabase();
		} catch (Exception e) {
			//
			Log.e(" WordDBHelper Error", e.toString());
			close();
		}
	}

	public void close() {
		db.close();
		db = null;
		dbHelper.close();
		dbHelper = null;
	}

	public ArrayList<Word> wordListByTagName (String tagName) {
		ArrayList<Word> wordsList = new ArrayList<Word>();

		String[] columns = { "id", "subject", "paragraph", "note", "tag", "prefix", "memorized", "priority" };
		String cond = "tag = '" + tagName + "'";

		Cursor cursor = db.query(WORDS_TABLE, columns, cond, null, null, null, "priority ASC");

		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				Word word = new Word();
				word.id = cursor.getInt(Word.ID_COLUMN);
				word.subject = cursor.getString(Word.SUBJECT_COLUMN);
				word.paragraph = cursor.getString(Word.PARAGRAPH_COLUMN);
				word.note = cursor.getString(Word.NOTE_COLUMN);
				word.tag = cursor.getString(Word.TAG_COLUMN);
				word.prefix = cursor.getString(Word.PREFIX_COLUMN);
				word.memorized = cursor.getInt(Word.MEMORIZED_COLUMN);
				word.priority = cursor.getInt(Word.PRIORITY_COLUMN);
				
				wordsList.add(word);

				cursor.moveToNext();
			}
		}
		
		cursor.close();
		cursor = null;

		return wordsList;
	}
	
	public void updateMemorized (int id, boolean isMemorized) {
		ContentValues values = new ContentValues();
		values.put("memorized", (isMemorized ? 1 : 0));
		
		int res = db.update(WORDS_TABLE, values, "id = " + id, null);
		Log.d(" update result", "" + res);
		//db.execSQL("UPDATE " + WORDS_TABLE + " set memorized = " + val + " where id = " + id);
	}

}