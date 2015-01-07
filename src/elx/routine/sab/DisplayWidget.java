package elx.routine.sab;

import java.util.Random;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViews.RemoteView;
import android.widget.Toast;

public class DisplayWidget extends AppWidgetProvider{
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Random r = new Random();
		int rand = r.nextInt(100);
		String text = String.valueOf(rand);
		
		final int N = appWidgetIds.length;
		
		for(int i=0;i<N;i++)
		{
			int appWid = appWidgetIds[i];
			RemoteViews rv = new RemoteViews(context.getPackageName(),R.layout.widget_d);
			rv.setTextViewText(R.id.titleW,"Current Period:");
			rv.setTextViewText(R.id.subName, "Numerical Method");
			rv.setTextViewText(R.id.TimeW, "Time Left:45:00min");
			appWidgetManager.updateAppWidget(appWid, rv);
			
		}
		
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
		Toast.makeText(context, "Bye Bye try again later", 3).show();
	}

	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
		Toast.makeText(context, "Please Enable to display", 3).show();
	}

	

}
