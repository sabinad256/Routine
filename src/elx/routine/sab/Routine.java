package elx.routine.sab;


import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.Html;
import android.text.InputFilter.LengthFilter;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Routine extends Activity {
	
	NotificationManager nm;
	static final int uniqueId = 543212;
		
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.week);	
		
		
		
		nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		nm.cancel(uniqueId);
		  
		  
		
		
		
		
		dataLoad();
		long m=0;
		Calendar c = Calendar.getInstance();
		if(c.get(Calendar.AM_PM)==0)
		{
		 m = (c.get(Calendar.HOUR)*60*60+c.get(Calendar.MINUTE)*60+c.get(Calendar.SECOND));
		}
		else
			 m = ((c.get(Calendar.HOUR)+12)*60*60+c.get(Calendar.MINUTE)*60+c.get(Calendar.SECOND));
		
		
		
		
		Datahandling db = new Datahandling(this);
		db.open();
		String cv = db.getData(m,""+new Date().getDay());
		db.close();
		
		
		if(!cv.isEmpty())
		{
			
			long dm = Integer.parseInt(cv);
			Timer t = new Timer((dm-m)*1000,1000);
			t.start();
		
		}
		else
		{
			TextView tv = (TextView)findViewById(R.id.week_name);
			tv.setText("No Active Class");
		}
		

		
		
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
			
			tv.setMinimumWidth(100);
			
			tv.setGravity(Gravity.CENTER_HORIZONTAL);
			
		
		
		
			d.setContentView(tv);
			
			d.show();
			
			
			break;
		case R.id.action_week:
			Intent i2 = new Intent("elx.routine.sab.WEEK");
			startActivity(i2);
			break;
		case R.id.action_online:
			
				
			
			ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
			
			if(!cd.isConnected())
			{
				
			Toast t = Toast.makeText(getApplicationContext(), "No Internet Connection.Please check your setting and try again", 5);
			t.show();
			}
			else
			{	
			Intent i3 = new Intent("elx.routine.sab.ONLINE");
			finish();
			startActivity(i3);
			
			}
			
			
				
			
			break;
		
			
		}
		return false;
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		dataLoad();
	}

	private void dataLoad()
	{
		Datahandling db = new Datahandling(this);
		db.open();
		Cursor cv = db.fetchdata(""+new Date().getDay());
		db.close();
		
		String[] columns = new String[] {
			    Datahandling.KEY_SUBJECT,
			    Datahandling.KEY_TEACHER,
			    Datahandling.KEY_ENDTIME,
			    Datahandling.KEY_TIME,
			    
			  };
			 
			  // the XML defined views which the data will be bound to
			  int[] to = new int[] {
			   R.id.subject_name,
			   R.id.teacherNames,
			   R.id.end_time,
			   R.id.time
			  
			   };
			  
			  
		SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(this,R.layout.routine_list,cv,columns,to,0){
			 @Override
			 public void setViewText(TextView v, String text) {
			 super.setViewText(v, convText(v, text));
			}    
		};
		
				
		ListView lv = (ListView)findViewById(R.id.listView1);
		lv.setAdapter(dataAdapter);
		
		
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

	
	
	public class Timer extends CountDownTimer{
		
		TextView tv = (TextView)findViewById(R.id.week_name);
		

		public Timer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			
			
			// TODO Auto-generated constructor stub
		}

		@SuppressLint("NewApi")
		@SuppressWarnings("deprecation")
		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			tv.setText("Finished Period");
			Intent intent = new Intent(getApplicationContext(),Routine.class);
			PendingIntent pi= PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
			
			
			//Calendar c = Calendar.getInstance();
			//long m = (c.get(Calendar.HOUR)*60*60+c.get(Calendar.MINUTE)*60+c.get(Calendar.SECOND))*1000;
			//long dm=6960000;
			
			String body = "Numerical Method class completed";
			String title = "Period Completed!";
			@SuppressWarnings("deprecation")
			Notification n = new Notification(R.drawable.logo, body, System.currentTimeMillis());
			n.setLatestEventInfo(getApplicationContext(), title, body,pi);
			n.defaults = Notification.DEFAULT_ALL;
			nm.notify(uniqueId,n);
			
			//Timer ro = new Timer(300000,1000);
			//ro.start();
			
			long m=0;
			Calendar c = Calendar.getInstance();
			if(c.get(Calendar.AM_PM)==0)
			{
			 m = (c.get(Calendar.HOUR)*60*60+c.get(Calendar.MINUTE)*60+c.get(Calendar.SECOND));
			}
			else
				 m = ((c.get(Calendar.HOUR)+12)*60*60+c.get(Calendar.MINUTE)*60+c.get(Calendar.SECOND));
			
			
			Datahandling db = new Datahandling(getApplicationContext());
			db.open();
			String cv = db.getData(m,""+new Date().getDay());
			db.close();
			
			
			if(!cv.isEmpty())
			{
				
				long dm = Integer.parseInt(cv);
				Timer t = new Timer((dm-m)*1000,1000);
				t.start();
			
			}
			else
			{
				TextView tv = (TextView)findViewById(R.id.week_name);
				tv.setText("No active class");
			}
			

			
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			 long second = millisUntilFinished / 1000;
			 
			 long h =  second/3600;
			 long rem  = second%3600;
			 long m = rem/60;
			 long sec = rem%60;
		    	
		        tv.setText("Time Left: " + h + ":" + m+" : "+sec);
			
		}
		
	}

}
