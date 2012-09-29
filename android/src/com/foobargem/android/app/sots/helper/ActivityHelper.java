package com.foobargem.android.app.sots.helper;

import android.app.Activity;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foobargem.android.app.sots.R;
import com.foobargem.android.app.sots.ui.TagsListActivity;

public class ActivityHelper {
    protected Activity mActivity;


    public static ActivityHelper createInstance(Activity activity) {
    	return new ActivityHelper(activity);
    }

    protected ActivityHelper(Activity activity) {
        mActivity = activity;
    }

    public Activity getActivity () {
    	return mActivity;
    }
    
    

    /**
     * Invoke "home" action, returning to {@link com.google.android.apps.iosched.ui.HomeActivity}.
     */
    protected void goHome() {
        if (mActivity instanceof TagsListActivity) {
            return;
        }

        final Intent intent = new Intent(mActivity, TagsListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mActivity.startActivity(intent);
    }


    public void setupActionBar (CharSequence title, boolean useHomeBtn, boolean useBackBtn) {
        final ViewGroup actionBarCompat = getActionBarCompat();
        if (actionBarCompat == null) {
            return;
        }

//        if (useHomeBtn) {
//            View.OnClickListener homeClickListener = new View.OnClickListener() {
//                public void onClick(View view) {
//                    goHome();
//                }
//            };
//            addActionButtonCompat(R.drawable.btn_home, R.string.description_home, homeClickListener, true);
//        }
//        
//        if (useBackBtn) {
//        	View.OnClickListener backClickListener = new View.OnClickListener() {
//				public void onClick(View v) {
//					//mActivity.onBackPressed();
//				}
//			};
//        	addActionButtonCompat(R.drawable.btn_back, R.string.description_back, backClickListener, true);
//        }

        if (title != null) {
            LinearLayout.LayoutParams springLayoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.FILL_PARENT);
            springLayoutParams.weight = 1;

        	// Add title text
            TextView titleText = new TextView(mActivity, null, R.attr.actionbarTitleStyle);
            titleText.setLayoutParams(springLayoutParams);
            titleText.setText(title);
            actionBarCompat.addView(titleText);
        }

    }


    /**
     * Sets the action bar title to the given string.
     */
    public void setActionBarTitle(CharSequence title) {
        ViewGroup actionBar = getActionBarCompat();
        if (actionBar == null) {
            return;
        }

        TextView titleText = (TextView) actionBar.findViewById(R.id.actionbar_title);
        if (titleText != null) {
            titleText.setText(title);
        }
    }

    /**
     * Returns the {@link ViewGroup} for the action bar on phones (compatibility action bar).
     * Can return null, and will return null on Honeycomb.
     */
    public ViewGroup getActionBarCompat() {
        return (ViewGroup) mActivity.findViewById(com.foobargem.android.app.sots.R.id.actionbar);
    }

    /**
     * Adds an action bar button to the compatibility action bar (on phones).
     */
//    public View addActionButtonCompat(int iconResId, int textResId, View.OnClickListener clickListener, boolean separatorAfter) {
//        final ViewGroup actionBar = getActionBarCompat();
//        if (actionBar == null) {
//            return null;
//        }
//
//        // Create the separator
//        ImageView separator = new ImageView(mActivity, null, R.attr.actionbarCompatSeparatorStyle);
//        separator.setLayoutParams(new ViewGroup.LayoutParams(2, ViewGroup.LayoutParams.FILL_PARENT));
//
//        // Create the button
//        ImageButton actionButton = new ImageButton(mActivity, null, R.attr.actionbarCompatButtonStyle);
//        actionButton.setLayoutParams(new ViewGroup.LayoutParams(
//                (int) mActivity.getResources().getDimension(R.dimen.actionbar_compat_height),
//                ViewGroup.LayoutParams.FILL_PARENT));
//        actionButton.setImageResource(iconResId);
//        actionButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//        actionButton.setContentDescription(mActivity.getResources().getString(textResId));
//        actionButton.setOnClickListener(clickListener);
//
//        // Add separator and button to the action bar in the desired order
//
//        if (!separatorAfter) {
//            actionBar.addView(separator);
//        }
//
//        actionBar.addView(actionButton);
//
//        if (separatorAfter) {
//            actionBar.addView(separator);
//        }
//
//        return actionButton;
//    }


}
