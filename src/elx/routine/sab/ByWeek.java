package elx.routine.sab;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class ByWeek extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.week);
		TextView tv = (TextView)findViewById(R.id.week_name);
		Intent i = getIntent();
		int id = i.getIntExtra("id",0);
		String week = i.getStringExtra("week");
		
		Datahandling db = new Datahandling(this);
		db.open();
		Cursor cv = db.fetchdata(""+id);
		db.close();
		
		String[] columns = new String[] {
			    Datahandling.KEY_SUBJECT,
			    Datahandling.KEY_TEACHER,
			    Datahandling.KEY_TIME,
			    Datahandling.KEY_ENDTIME
			  };
			 
			  // the XML defined views which the data will be bound to
			  int[] to = new int[] {
			   R.id.subject_name,
			   R.id.teacherNames,
			   R.id.time,
			   R.id.end_time
			    
			  };
			  
			  
		SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(this,R.layout.routine_list,cv,columns,to,0)
		{
			 @Override
			 public void setViewText(TextView v, String text) {
			 super.setViewText(v, convText(v, text));
			}    
		};
		ListView lv = (ListView)findViewById(R.id.listView1);
		lv.setAdapter(dataAdapter);
		tv.setText(week);
		
	}
	
	
	private String convText(TextView v, String text)
	{

	 switch (v.getId())
	 {
	 case R.id.end_time:
	             
	             String formatedText = Dates(text);
	             //do format
	            return formatedText;
	 case R.id.time:
		 String formatedTexts = Dates(text);
		 return formatedTexts;
	        }
	return text;
	}
	
	private String Dates(String text)
	{
		int hours=0;
		int minutes=0;
		String endTime="";
		try {
		    int myNum = Integer.parseInt(text);
		   
		    hours = myNum / 3600;
		    minutes = (myNum % 3600) / 60;
		    
		    
		    if(minutes<10)
		    	endTime = ""+hours + " : 0" + minutes;
		    else
		    	endTime = ""+hours + " : " + minutes;

		    
		} catch(NumberFormatException nfe) {
		  // Handle parse error.
		}

		
	return endTime;	
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.routine, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId())
		{
		case R.id.action_settings:
			Intent i = new Intent("elx.routine.sab.PREFS");
			startActivity(i);
			break;
		case R.id.about:
			Dialog d = new Dialog(this);
			d.setTitle("About the Developer");
			TextView tv = new TextView(this);
			tv.setText(Html.fromHtml("<b>Version 1.0.1 Beta 1</b><br />&copy 2013,Sabin Adhikari<br /><br /> " +
					"Routine application has been " +
					"developed for updating the student about their daily classes and providing them notification and updates about the latest assignment and notices.<br /><br /> " +
					"Concept & Developed By:<br />Sabin Adhikari" +
					"<br/><span color=\"red\">http://www.saben.com</span><br /><br />" +
					"For Putting your contact <br />Email: <a>sabinad256@gmail.com </a><br />" +
					"<a href=\"http://sabin.com/report\">Report a Problem</a><br /><br /> &copy 2013,Sabin Adhikari<br />"));
			tv.setMinimumWidth(100);tv.setGravity(Gravity.CENTER_HORIZONTAL);
			d.setContentView(tv);
			d.show();
			break;
		case R.id.action_week:
			Intent i2 = new Intent("elx.routine.sab.WEEK");
			startActivity(i2);
			break;
		case R.id.action_online:
			Intent i3 = new Intent("elx.routine.sab.ONLINE");
			startActivity(i3);
			break;
		
			
		}
		return false;
	}
	

}
