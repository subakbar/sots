package com.foobargem.android.app.sots.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Word implements Parcelable {

	public int id;
	public static final int ID_COLUMN = 0;
	
	public String subject;
	public static final int SUBJECT_COLUMN = 1;
	
	public String paragraph;
	public static final int PARAGRAPH_COLUMN = 2;
	
	public String note;
	public static final int NOTE_COLUMN = 3;
	
	public String tag;
	public static final int TAG_COLUMN = 4;
	
	public String prefix;
	public static final int PREFIX_COLUMN = 5;
	
	public int memorized;
	public static final int MEMORIZED_COLUMN = 6;
	
	public int priority;
	public static final int PRIORITY_COLUMN = 7;
	
	
	
	public boolean isMemorized () {
		if (memorized == 0) {
			return false;
		} else {
			return true;
		}
	}
	

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(subject);
		dest.writeString(paragraph);
		dest.writeString(note);
		dest.writeString(tag);
		dest.writeString(prefix);
		dest.writeInt(memorized);
		dest.writeInt(priority);
	}

	public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {

		@Override
		public Word createFromParcel(Parcel source) {
			Word word = new Word();
			word.id = source.readInt();
			word.subject = source.readString();
			word.paragraph = source.readString();
			word.note = source.readString();
			word.tag = source.readString();
			word.prefix = source.readString();
			word.memorized = source.readInt();
			word.priority = source.readInt();

			return word;
		}

		@Override
		public Word[] newArray(int size) {
			return new Word[size];
		}
	};	
	
	
}