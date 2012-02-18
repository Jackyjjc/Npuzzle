/*******************************************************************
 * ThumbnailManager - managing the generation of the thumbnails    *
 *                                                                 *
 * Author: Jacky, Chen                                             *
 *                                                                 *
 * Last Modified: Feb, 18th, 2012                                  *
 *                                                                 *
 * Version: 1.0                                                    *
 *                                                                 *
 *******************************************************************/

package com.qqending.npuzzle;

import android.content.Context;
import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.content.res.Resources;
import android.media.ThumbnailUtils;
import java.util.ArrayList;
import java.lang.reflect.Field;

public class ThumbnailManager {

	public final ArrayList<BitmapDrawable> thumbnails;
	public final ArrayList<String> names;
	public final Integer ids[];
	public int numItems = 0;
	
	public ThumbnailManager(Context context, int width, int height) {
		
		thumbnails = new ArrayList<BitmapDrawable>();
		names = new ArrayList<String>();
		
		Field[] fields = R.drawable.class.getFields();
		int numFields = fields.length;
		
		String name;
		for(Field f: fields) {
			name = f.getName();
			if(name.startsWith("puzzle_")) {
				names.add(name);
				numItems++;
			}
		}
		
		ids = new Integer[numItems];
		
		for(int i = 0, j = 0; i < numFields; i++) {
			if(fields[i].getName().startsWith("puzzle_")) {
				try {
					ids[j] = fields[i].getInt(null);
				} catch (IllegalAccessException e) {
					Log.e("ThumbnailManager","IllegalAccessException");
				}
				j++;
			}
		}
		
		Resources res = context.getResources();
		
		for(Integer i: ids) {
			Bitmap origin = ((BitmapDrawable)res.getDrawable(i)).getBitmap();
			Bitmap resized = ThumbnailUtils.extractThumbnail(origin,width, height);
			thumbnails.add(new BitmapDrawable(resized));
			origin.recycle();
		}
	}
	
	public BitmapDrawable getThumbnail(int position) {
		return thumbnails.get(position);
	}
	
	public String getName(int position) {
		return names.get(position);
	}
	
	public int getCount() {
		return numItems;
	}
	
	public long getItemId(int position) {
		return ids[position];
	}
}
