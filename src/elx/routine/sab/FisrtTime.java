package elx.routine.sab;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class FisrtTime extends Activity implements OnClickListener{

	
	public static String fileName = "firstTime";
	Button btn ;
	EditText et;
	Spinner sp;
	TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		SharedPreferences prefs = getSharedPreferences(fileName,0);
		boolean check = prefs.getBoolean("keyCode",false);
		if(!check)
		{
			setContentView(R.layout.first_time);
			btn = (Button)findViewById(R.id.submitFirst);
			et = (EditText)findViewById(R.id.rollNo);
			sp = (Spinner)findViewById(R.id.sectionId);
			tv = (TextView)findViewById(R.id.firstText);
			tv.setText(Html.fromHtml("This window will appear once and you need to provide all the necessary information for configuring your section and faculty id for the purpose of the getting the routine from the online source.This screen will appear once and after that  you can change your section by going to setting panel of the application after this setting has been configured."));
			btn.setOnClickListener(this);
		}
		else
		{
			Intent i = new Intent("elx.routine.sab.ROUTINE");
			startActivity(i);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.submitFirst:
			String section = ""+sp.getSelectedItemId(); 
			String rollNo = et.getText().toString();
			try{
			SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		    Editor edit = pref.edit();
		    edit.putString("section_id",section);
		    edit.putString("roll_no",rollNo);
		  	edit.commit();
		  	}
			catch(Exception e)
			{
			e.printStackTrace();
			}
			finally{
				SharedPreferences prefs = getSharedPreferences(fileName,0);
			  	SharedPreferences.Editor editor = prefs.edit();
			  	editor.putBoolean("keyCode", true);
			  	editor.commit();
				Intent i = new Intent("elx.routine.sab.ONLINE");
				startActivity(i);
			}
		  	
			break;
		}
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	

	

}
