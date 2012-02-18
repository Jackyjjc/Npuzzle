/*******************************************************************
 * ImageSelection - the ListView for user to selection an image    *
 *                                                                 *
 * Author: Jacky, Chen                                             *
 *                                                                 *
 * Last Modified: Feb, 18th, 2012                                  *
 *                                                                 *
 * Version: 1.0                                                    *
 *                                                                 *
 *******************************************************************/

package com.qqending.npuzzle;

import android.app.ListActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Bundle;

//import android.graphics.BitmapFactory.Options;

public class ImageSelection extends ListActivity implements OnItemClickListener, OnClickListener {
	
	private final int PICK_IMAGE = 1;
	
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageselection);
        
        CustomizedAdapter adapter = new CustomizedAdapter(this);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
        
        TextView tv = (TextView)findViewById(R.id.chooseView);
        tv.setOnClickListener(this);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageIntent) {
    	super.onActivityResult(requestCode, resultCode, imageIntent);
    	
    	if(requestCode == PICK_IMAGE) {
    		if(resultCode == RESULT_OK) {
    			Intent i = new Intent(this, GamePlay.class);
    			i.setData(imageIntent.getData());
    			startActivity(i);
    		}
    	}
    }
    
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    	
    	Intent i = new Intent(this, GamePlay.class);
    	
    	TextView tv = (TextView) v;
    	Long imageId = (Long)tv.getTag();
    	
    	i.putExtra("imageId", imageId.intValue());
    	startActivity(i);
    }
    
    public void onClick(View v) {
    	Intent i = new Intent(Intent.ACTION_GET_CONTENT);
    	i.setType("image/*");
    	i.addCategory(Intent.CATEGORY_OPENABLE);
    	
    	startActivityForResult(i,PICK_IMAGE);
    }
}