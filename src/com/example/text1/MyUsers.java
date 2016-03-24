package com.example.text1;

import android.net.Uri;

public class MyUsers {
	
	public static final String AUTHORITY = 	"com.example.text1";
	
	public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY);
	
	public static final String USER_NAME = "user_name";
	
	private static final String DATABASE_NAME = "example_text.db";

}
