/*****************************************************
 * CustomizedAdapter - ListAdapter for the ListView  *
 *                                                   *
 * Author: Jacky, Chen                               *
 *                                                   *
 * Last Modified: Feb, 18th, 2012                    *
 *                                                   *
 * Version: 1.0                                      *
 *                                                   *
 *****************************************************/

package com.qqending.npuzzle;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.BaseAdapter;

public class CustomizedAdapter extends BaseAdapter {
	
	private final ThumbnailManager thumbmgn;
	private final int WIDTH = 120;
	private final int HEIGHT = 120;
	private Context context;
	
	public CustomizedAdapter(Context context) {
		
		this.context = context;
		thumbmgn = new ThumbnailManager(context,WIDTH,HEIGHT);
	}
	
	@Override
	public View getView(int position, View v, ViewGroup parent) {

		if(v == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			v = inflater.inflate(R.layout.list_item, null);
		}
		
		TextView tv = (TextView)v.findViewById(R.id.adapterTextView);
		tv.setText(thumbmgn.getName(position));
		tv.setCompoundDrawablesWithIntrinsicBounds(thumbmgn.getThumbnail(position),null,null,null);
		tv.setTag(thumbmgn.getItemId(position));
		
		return v;
	}
	
	@Override
	public int getCount() {
		return thumbmgn.getCount();
	}
	
	@Override
	public Object getItem(int position) {
		return null;
	}
	
	@Override
	public long getItemId(int position) {
		return thumbmgn.getItemId(position);
	}
}
