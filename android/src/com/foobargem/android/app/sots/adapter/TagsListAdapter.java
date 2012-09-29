package com.foobargem.android.app.sots.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.foobargem.android.app.sots.R;
import com.foobargem.android.app.sots.model.Tag;

public class TagsListAdapter extends BaseAdapter {

	private int layoutResource;
	private ArrayList<Tag> tagsList;
	private LayoutInflater inflater;


	public TagsListAdapter (Context context, int layoutResource, ArrayList<Tag> tagsList) {
		this.layoutResource = layoutResource;
		this.tagsList = tagsList;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	
	@Override
	public int getCount() {
		return tagsList.size();
	}

	@Override
	public Tag getItem(int position) {
		return tagsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    ViewHolder holder;
	    Tag tag = getItem(position);

		if (convertView == null) {
			convertView = inflater.inflate(layoutResource, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.tagView = (TextView) convertView.findViewById(R.id.tag_item_tag_name);

            convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tagView.setText(tag.prefix + ". " + tag.name);

		return convertView;
	}

	
	
	private class ViewHolder {
		public TextView tagView;
		//public TextView accessoryView;
	}

}
