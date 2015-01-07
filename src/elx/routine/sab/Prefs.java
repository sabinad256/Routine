package elx.routine.sab;



import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.TextView;

public class Prefs extends PreferenceActivity{

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
		
		
		Preference button = (Preference)findPreference("reset");
		
		button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference arg0) { 
            	
            	
            	try{
            		String filename = FisrtTime.fileName;
            	
            	SharedPreferences prefs = getSharedPreferences(filename,0);
            	SharedPreferences.Editor editor = prefs.edit();
    			
    			editor.putBoolean("keyCode",false);
    			editor.commit();
            	}
            	catch(Exception e)
            	{
            		e.printStackTrace();
            	}
            	finally{
            		Dialog d = new Dialog(Prefs.this);
            		d.setTitle("Reset");
            		TextView tv = new TextView(Prefs.this);
            		tv.setText("\t \t Setting Reset to default" +
            				"\n \t\t Press back ");
            		d.setContentView(tv);
            		d.show();
            		
     }
                //code for what you want it to do   
            	
               return true;
            }
        });
		
		
		 
	}
	
	
	
	
	

}
