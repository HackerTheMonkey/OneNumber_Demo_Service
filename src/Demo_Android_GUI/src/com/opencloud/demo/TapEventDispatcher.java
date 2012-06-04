package com.opencloud.demo;


import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class TapEventDispatcher implements OnItemClickListener
{

	private Context context = null;
	
	public TapEventDispatcher(Context context)
	{
		this.context = context;
	}	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{	
		String testMessage = "The item selected is: " + ((TextView)view).getText() + "(" + position + ")";
		switch(position)
		{			
			case 0:								
				Toast.makeText(context, testMessage, Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Toast.makeText(context, testMessage, Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(context, testMessage, Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(context, testMessage, Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(context, testMessage, Toast.LENGTH_SHORT).show();
				break;
			case 5:
				Toast.makeText(context, testMessage, Toast.LENGTH_SHORT).show();
				break;
			case 6:
				Toast.makeText(context, testMessage, Toast.LENGTH_SHORT).show();
				break;
			case 7:
				Toast.makeText(context, testMessage, Toast.LENGTH_SHORT).show();
				break;
			case 8:
				Toast.makeText(context, testMessage, Toast.LENGTH_SHORT).show();
				break;
			case 9:
				Toast.makeText(context, testMessage, Toast.LENGTH_SHORT).show();
				break;
			case 10:
				Toast.makeText(context, testMessage, Toast.LENGTH_SHORT).show();
				break;
			default:				
		}
	}

}