package elx.routine.sab;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.app.Dialog;

import android.content.Intent;
import android.content.SharedPreferences;




import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;


public class Online extends Activity {

	// url to make request
	private static String baseurl = "http://192.168.1.4/C2/index.php/DailyRoutine/section/";
	
	
	// JSON Node names
	private static final String TAG_DATA = "week";
	private static final String TAG_NAME = "name";
	private static final String TAG_WEEK = "week_id";
	private static final String TAG_TEACHER = "teacherName";
	private static final String TAG_TIME = "time";
	private static final String TAG_ENDTIME = "end_time";
	
	

	// contacts JSONArray
	JSONArray contacts = null;
	
	

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.progress);
		
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		if(cd.isConnected())
		{
		
		
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String URI = getPrefs.getString("section_id", "0");
		
		
		
		StringBuilder URL = new StringBuilder(baseurl);
		URL.append(URI);
		
		String url = URL.toString();
		
		
		
		
		
		
		// Hashmap for ListView
		

		// Creating JSON Parser instance
		JsonParser jParser = new JsonParser();

		// getting JSON string from URL
		JSONObject json = jParser.getJSONFromUrl(url);
		
		//Initialization of database
		Datahandling db = new Datahandling(this);
		db.open();
		db.deleteAll();

		try {
			// Getting Array of Contacts
			contacts = json.getJSONArray(TAG_DATA);
			
		
			
			// looping through All Contacts
			for(int i = 0; i < contacts.length(); i++){
				JSONObject c = contacts.getJSONObject(i);
				
				// Storing each json item in variable
				
				String name = c.getString(TAG_NAME);
				String teacher = c.getString(TAG_TEACHER);
				String week_id = c.getString(TAG_WEEK);
				String time = c.getString(TAG_TIME);
				String endTime = c.getString(TAG_ENDTIME);
				
				
				
				db.createEntry(name, time, week_id, teacher, endTime);
				
				
				
				
			   
				
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		finally{
			Intent i = new Intent("elx.routine.sab.ROUTINE");
			startActivity(i);
		}
		
		//writes on the screen
		db.close();
		
		}
		else 
		{
			Dialog d = new Dialog(this);
			d.setTitle("No internet Connection");
			TextView tv = new TextView(this);
			tv.setText("Please Connect To internet and ");
			d.show();
		
			
			
		}
	
		
		
	}




	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	
	
	
}