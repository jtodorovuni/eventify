package uni.fmi.eventify.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import uni.fmi.eventify.entity.User;


public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "eventify.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_USER = "user";
    public static final String USER_ID = "id";
    public static final String USER_USERNAME = "username";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";

    public static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER
            + "( " + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + USER_USERNAME + " VARCHAR(100) UNIQUE NOT NULL,"
            + USER_EMAIL + " VARCHAR(250) UNIQUE NOT NULL,"
            + USER_PASSWORD + " VARCHAR(42) NOT NULL);";

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {  }


    public boolean registers(User user){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(USER_USERNAME, user.getUsername());
        cv.put(USER_EMAIL, user.getEmail());
        cv.put(USER_PASSWORD, user.getPassword());

        if(db.insert(TABLE_USER, null, cv) == -1){
            return false;
        }

        return true;
    }

    public User login (String username, String password){
        SQLiteDatabase db = getReadableDatabase();
        try {
            String query = "SELECT * FROM " + TABLE_USER
                    + " WHERE " + USER_USERNAME + " = '" + username + "'"
                    + " AND " + USER_PASSWORD + " = '" + password + "'";

            Cursor c = db.rawQuery(query, null);

            if (c.moveToFirst()) {
                User user = new User();

                user.setId(c.getInt(c.getColumnIndexOrThrow(USER_ID)));
                user.setUsername(username);
                user.setEmail(c.getString(c.getColumnIndexOrThrow(USER_EMAIL)));

                return user;
            }

        }catch (IllegalArgumentException ex){
            Log.wtf("db", ex.getMessage());
        }
        return null;
    }

}
