package com.foobargem.android.app.sots.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.foobargem.android.app.sots.R;
import com.foobargem.android.app.sots.adapter.TagsListAdapter;
import com.foobargem.android.app.sots.model.Tag;

public class TagsListActivity extends BaseActivity {

	private TagsListAdapter adapter; 
	private ArrayList<Tag> tagsList;
	private ListView tagsListView;

	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tags_list);

		getActivityHelper().setupActionBar("주제별 성경암송 60구절", false, false);

		makeTagsList();

		adapter = new TagsListAdapter(this, R.layout.tag_item, tagsList);

		tagsListView = (ListView) findViewById(R.id.tags_list_container);
		tagsListView.setAdapter(adapter);
		tagsListView.setOnItemClickListener(new ItemClickListener());


	}
	
	
	private void makeTagsList () {
		tagsList = new ArrayList<Tag>();
		
		tagsList.add(new Tag("A", "복음"));
		tagsList.add(new Tag("B", "양육"));
		tagsList.add(new Tag("C", "제자의 삶"));
		tagsList.add(new Tag("D", "군사의 삶"));
		tagsList.add(new Tag("E", "재생산의 삶"));		
	}

	
	private class ItemClickListener implements AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> av, View v, int position, long id) {
			Tag tag = tagsList.get((int) id);
			Intent intent = new Intent(getApplicationContext(), WordsListActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("tag_name", tag.name);
			startActivity(intent);
		}		
	}
	
}
