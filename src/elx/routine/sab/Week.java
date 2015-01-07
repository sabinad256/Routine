package elx.routine.sab;

import android.app.Activity;
import android.app.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
 
public class Week extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.week);
        
        
        ListView lv = (ListView)findViewById(R.id.listView1);
         
        // storing string resources into Array
        String[] adobe_products = getResources().getStringArray(R.array.weekName);
         
        // Binding resources Array to ListAdapter
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.label, adobe_products));
            // listening to single list item on click
        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
               
              // selected item
              String week = ((TextView) view).getText().toString();
            
              // Launching new Activity on selecting single List Item
              Intent i = new Intent(getApplicationContext(),ByWeek.class);
              // sending data to new activity
              i.putExtra("id", position);
              i.putExtra("week",week);
              startActivity(i);
             
          }
        });
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
		
		case R.id.action_online:
			Intent i3 = new Intent("elx.routine.sab.ONLINE");
			startActivity(i3);
			break;
		
			
		}
		return false;
	}
}