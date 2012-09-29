package com.foobargem.android.app.sots.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.foobargem.android.app.sots.R;

public class HomeActivity extends BaseActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				finish();
				Intent intent = new Intent(getApplicationContext(), TagsListActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
        }, 3000);
    }
}