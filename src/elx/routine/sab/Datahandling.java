package elx.routine.sab;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
//import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Datahandling {
	
	public static final String KEY_ROWID = "_id";
	public static final String  KEY_SUBJECT = "name";
	public static final String KEY_TIME = "time";
	public static final String KEY_WEEKID="week_id";
	public static final String KEY_TEACHER = "teacherName";
	public static final String KEY_ENDTIME = "end_time";
	
	
	public static final String DATABASE_NAME = "routine";
	public static final String DATABASE_TABLE = "subject";
	public static final int DATABASE_VERSION = 1;
	
	private  DBhelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	private static class DBhelper extends SQLiteOpenHelper{

		public DBhelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" 
						+ KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
						+ KEY_SUBJECT +" TEXT NOT NULL, "
						+ KEY_WEEKID +" TEXT NOT NULL, "
						+ KEY_TEACHER +" TEXT NOT NULL, "
						+ KEY_ENDTIME +" TEXT NOT NULL, "
						+ KEY_TIME + " TEXT NOT NULL);");
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
			
		}
		
		
		
	}
	
	
	
	public Datahandling(Context c)
	{
		ourContext = c;
	}
	
	public Datahandling open()
	{
		ourHelper = new DBhelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		
		return this;
		
	}
	
	public void close()
	{
		ourHelper.close();
		
	}

	public long createEntry(String name, String time,String week_id,String teacher,String end_time) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_SUBJECT, name);
		cv.put(KEY_TIME, time);
		cv.put(KEY_WEEKID,week_id);
		cv.put(KEY_TEACHER, teacher);
		cv.put(KEY_ENDTIME, end_time);
		
		
		
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
		
		
	}
	
	public boolean deleteAll()
	{
		int mdb = 0;
		
		mdb = ourDatabase.delete(DATABASE_TABLE, null, null);
		return mdb > 0;
		
	
	}
	
	public Cursor fetchdata(String week_id)
	{
		String[] columns = new String[]{KEY_ROWID,KEY_SUBJECT,KEY_TIME,KEY_WEEKID,KEY_TEACHER,KEY_ENDTIME};
		Cursor cv = ourDatabase.query(DATABASE_TABLE, columns, KEY_WEEKID+"="+week_id, null, null, null, null);
		
		if (cv != null) {
			   cv.moveToFirst();
			  }
		return cv;
	}
	
	public void putData()
	{
		//createEntry("Power System","07:15-8:05","0","U.D");
		//createEntry("Numerical Method","08:05-9:45","0","R.P");
		//createEntry("Break","9:45-10:35","0","Break");
		//createEntry("MicroProcessor","10:35-12:15","0","A.R.P");
		//createEntry("Discreate Structure","12:15-1:05","0","S.K.S");
		//createEntry("NM Lab","1:05-2:45","0","lab");
		
	}

	@SuppressLint("NewApi")
	public String getData(long time,String id) {
		// TODO Auto-generated method stub
		String[] columns = new String[]{KEY_ROWID,KEY_SUBJECT,KEY_TIME,KEY_ENDTIME};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, 
				KEY_WEEKID+"="+id, null, null, null, null);
		
		
		String result = "";
		
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iName = c.getColumnIndex(KEY_SUBJECT);
		int iTime = c.getColumnIndex(KEY_TIME);
		int iendTime = c.getColumnIndex(KEY_ENDTIME);
		
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			int times = Integer.parseInt(c.getString(iTime));
			int endTimes = Integer.parseInt(c.getString(iendTime));
			
			if(times<time && endTimes>time)
			{
				result = c.getString(iendTime);
			}
			
			
		}
		
		
		
		return result;
	}
	
	public static final long currentPeriod()
	{
		long time = System.currentTimeMillis();
		
		
		
		return time;
		
	
		
	}
	
	

}
