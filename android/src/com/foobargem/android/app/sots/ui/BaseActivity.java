package com.foobargem.android.app.sots.ui;

import android.app.Activity;

import com.foobargem.android.app.sots.helper.ActivityHelper;

public class BaseActivity extends Activity {

	final ActivityHelper mActivityHelper = ActivityHelper.createInstance(this);
	
	public ActivityHelper getActivityHelper () {
		return mActivityHelper;
	}

}
