/*****************************************************
 * ErrorAlertDialog - the customized AlertDialog     *
 *                                                   *
 * Author: Jacky, Chen                               *
 *                                                   *
 * Last Modified: Feb, 18th, 2012                    *
 *                                                   *
 * Version: 1.0                                      *
 *                                                   *
 *****************************************************/

package com.qqending.npuzzle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class ErrorAlertDialog implements OnClickListener {

	AlertDialog.Builder builder;
	private Activity owner;
	
	public ErrorAlertDialog(Activity owner) {
		
		this.owner = owner;
		
		this.builder = new AlertDialog.Builder(owner);
		builder = builder.setTitle("Error");
		builder.setMessage("Sorry something problem has occured %n" +
		                   "Please report it to us and We will fix it as soon as possible");
		builder.setCancelable(false);
		builder.setNeutralButton("close", this);
	}
	
	public void onClick(DialogInterface dialog, int which) {
		owner.finish();
	}
	
	public void show() {
		builder.show();
	}
}
