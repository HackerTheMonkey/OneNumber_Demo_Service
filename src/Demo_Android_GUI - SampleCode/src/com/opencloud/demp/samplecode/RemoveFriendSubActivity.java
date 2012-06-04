package com.opencloud.demp.samplecode;

import uk.ac.ucl.ee.snsAssignment.mLocationizer.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class RemoveFriendSubActivity extends Activity 
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remove_friend_menu_layout);
		RemoveFriendButtonOnClickListener removeFriendButtonOnClickListener = new RemoveFriendButtonOnClickListener(this);
		Button removeFriendButton = (Button)findViewById(R.id.removeFriendButton);
		removeFriendButton.setOnClickListener(removeFriendButtonOnClickListener);
	}
}
