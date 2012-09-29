package com.foobargem.android.app.sots.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static Context context;
	private static String dbDir;
	private final static String DB_NAME = "sots.sqlite3";
	
	public DatabaseHelper (Context _context) throws IOException {
		super(_context, _context.getApplicationInfo().dataDir + "/" + DB_NAME, null, 1);
		context = _context;
		dbDir = _context.getApplicationInfo().dataDir;
		makeDBIfNotExists();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

	@Override
	public void close () {
		super.close();
	}
	
	private void makeDBIfNotExists () throws IOException {
		File dbPath = new File(dbDir + "/" + DB_NAME);
		if (!dbPath.exists()) {
			copyDBFromAsset(dbPath);
		}
	}

	
	private void copyDBFromAsset (File dbPath) throws IOException {
		InputStream is = context.getAssets().open(DB_NAME);
		OutputStream os = new FileOutputStream(dbPath.getPath());

		byte[] buffer = new byte[1024];
		int length;
		while ((length = is.read(buffer)) > 0) {
			os.write(buffer, 0, length);
		}

		os.flush();
		os.close();
		is.close();
	}
	
}