package elx.routine.sab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		Thread timer = new Thread()
		{
			public void run()
			{
			try{
				sleep(2000);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			
			}
			finally
			{
				
				Intent i = new Intent("elx.routine.sab.FIRSTTIME");
				finish();
				startActivity(i);
			}
		}};
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
		
	
	
	
	

}
