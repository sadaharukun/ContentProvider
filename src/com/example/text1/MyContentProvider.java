package com.example.text1;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Handler;

public class MyContentProvider extends ContentProvider {

	private DatabaseHelper mHelper;

	private static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	private static final String PATH1 = "example_text";

	private static final int URI_CODE1 = 1;

//	private static final int URI_CODE2 = 2;

//	private static final String PATH2 = "22";

	private String mDataBaseName = "example_text.db";

	private Context mContext;

	private int mVersion = 1;

	private String TABLE_NAME = "example_text.db";

	private String TAG = MyContentProvider.class.getSimpleName();
	
	static {
		mUriMatcher.addURI(MyUsers.AUTHORITY, PATH1, 1);
//		mUriMatcher.addURI(MyUsers.AUTHORITY, PATH2, URI_CODE2);
	}
	
	class MyContentObserver extends ContentObserver{

		public MyContentObserver(Handler handler) {
			super(handler);
			// TODO Auto-generated constructor stub
		}
		
		@SuppressLint("NewApi") @Override
		public void onChange(boolean selfChange, Uri uri) {
			super.onChange(selfChange, uri);
		}
		
	}

	class DatabaseHelper extends SQLiteOpenHelper {

		// public DatabaseHelper(Context context, String name,
		// CursorFactory factory, int version) {
		// super(context, mDataBaseName, null, mVersion);
		//
		// }

		public DatabaseHelper(Context context) {
			super(context, mDataBaseName, null, mVersion);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql = "create table "
					+ TABLE_NAME
					+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT,user_name String );";
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			mVersion = arg2;
			arg0.execSQL("drop table " + TABLE_NAME + "if exists");
			onCreate(arg0);
		}
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		SQLiteDatabase db = mHelper.getWritableDatabase();

		int row = db.delete(TABLE_NAME, arg1, arg2);
		
		getContext().getContentResolver().notifyChange(arg0, new MyContentObserver(new Handler()));
		db.close();
		return row;
	}

	@Override
	public String getType(Uri arg0) {
		return arg0.getScheme();
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		SQLiteDatabase database = mHelper.getWritableDatabase();

		long rowId = database.insert(TABLE_NAME, "", values);
		if (rowId > 0) {
			// Uri returnUri = Uri.parse(MyUsers.CONTENT_URI+"/rowId");
			Uri returnUri = ContentUris.withAppendedId(MyUsers.CONTENT_URI,
					rowId);
			// Uri rowUri =
			// ContentUris.appendId(MyUsers.CONTENT_URI.buildUpon(),
			// rowId).build();
			getContext().getContentResolver().notifyChange(returnUri, null);
			database.close();
			return returnUri;
		}
		return null;
	}

	@Override
	public boolean onCreate() {
//		mUriMatcher.addURI(MyUsers.AUTHORITY, PATH1, 1);
//		mUriMatcher.addURI(MyUsers.AUTHORITY, PATH2, URI_CODE2);

		mHelper = new DatabaseHelper(mContext);
		return mHelper == null ? false : true;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {

		SQLiteDatabase database = mHelper.getWritableDatabase();

		Cursor cursor = database.query(TABLE_NAME, arg1, arg2, arg3, arg4,
				null, null);

		database.close();

		// int code = mUriMatcher.match(arg0);
		//
		// switch (code) {
		// case 0:
		//
		// break;
		//
		// default:
		// break;
		// }

		return cursor;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		int rowId = db.update(TABLE_NAME, arg1, arg2, arg3);
		db.close();
		return rowId;
	}

}
