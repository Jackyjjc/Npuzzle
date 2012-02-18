/**************************************
 * YouWin - the winning animation     *
 *                                    *
 * Author: Jacky, Chen                *
 *                                    *
 * Last Modified: Feb, 18th, 2012     *
 *                                    *
 * Version: 1.0                       *
 *                                    *
 **************************************/

package com.qqending.npuzzle;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.media.MediaPlayer;
import android.graphics.drawable.AnimationDrawable;
import android.view.View.OnClickListener;
import android.os.Bundle;

public class YouWin extends Activity implements OnClickListener {

	MediaPlayer mediaplayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.winpage);
		
		Button b = (Button)findViewById(R.id.back);
		b.setOnClickListener(this);
		
		final ImageView img = (ImageView)findViewById(R.id.nyanView);
		img.setBackgroundResource(R.anim.animation);
		img.post(new Runnable() {
			@Override
			public void run() {
				AnimationDrawable ani = (AnimationDrawable)img.getBackground();
				ani.start();
			}
		});
		
		mediaplayer = MediaPlayer.create(this, R.raw.nyanlooped);
		mediaplayer.setLooping(true);
		mediaplayer.start();
	}
	
	public void onClick(View v) {
		mediaplayer.stop();
		finish();
	}
	
	@Override
	protected void onStop() {
		mediaplayer.stop();
		super.onStop();
	}
}
