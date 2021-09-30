package com.ardee.thegreattravelagency;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME="mydatabase.db";
    private static final int DATABASE_VERSION=1;

    private static final String TABLE_NAME1 ="packages";
    private static final String COLUMN_ID1 ="_id";
    private static final String COLUMN_NAME1 ="package_name";
    private static final String COLUMN_CAPACITY1 ="capacity";
    private static final String COLUMN_SPACES_AVAILABLE1 ="spaces_available";

    private static final String TABLE_NAME2 ="destinations";
    private static final String COLUMN_ID2 ="_id";
    private static final String COLUMN_NAME2 ="destination_name";
    private static final String COLUMN_PACKAGE_ID2 ="package_id";

    private static final String TABLE_NAME3 ="activities";
    private static final String COLUMN_ID3 ="_id";
    private static final String COLUMN_NAME3 ="activity_name";
    private static final String COLUMN_DESCRIPTION3 ="description";
    private static final String COLUMN_COST3 ="cost";
    private static final String COLUMN_CAPACITY3 ="capacity";
    private static final String COLUMN_SPACES_AVAILABLE3 ="spaces_available";
    private static final String COLUMN_DESTINATION_ID3 ="destination_id";
    private static final String COLUMN_PACKAGE_ID3 ="package_id";

    private static final String TABLE_NAME4 ="passengers";
    private static final String COLUMN_ID4 ="_id";
    private static final String COLUMN_NAME4 ="passenger_name";
    private static final String COLUMN_PASSENGER_TYPE4 ="passenger_type";
    private static final String COLUMN_BALANCE4 ="passenger_balance";
    private static final String COLUMN_PACKAGE_ID4 ="package_id";

    private static final String TABLE_NAME5 ="passenger_activities";
    private static final String COLUMN_ID5 ="_id";
    private static final String COLUMN_PASSENGER_ID5 ="passenger_id";
    private static final String COLUMN_ACTIVITY_ID5="activity_id";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creating tables in database
       String query1="CREATE TABLE " + TABLE_NAME1 +
                    " (" + COLUMN_ID1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                      COLUMN_NAME1 + " TEXT, " +
               COLUMN_CAPACITY1 + " INTEGER, " +
               COLUMN_SPACES_AVAILABLE1 +" INTEGER);";

       String query2="CREATE TABLE " + TABLE_NAME2 +
               " (" + COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
               COLUMN_NAME2 + " TEXT, " +
               COLUMN_PACKAGE_ID2 +" INTEGER);";

        String query3="CREATE TABLE " + TABLE_NAME3 +
                " (" + COLUMN_ID3 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME3 + " TEXT, " +
                COLUMN_DESCRIPTION3 + " TEXT, " +
                COLUMN_COST3 + " INTEGER, " +
                COLUMN_CAPACITY3 + " INTEGER, " +
                COLUMN_SPACES_AVAILABLE3 + " INTEGER, " +
                COLUMN_PACKAGE_ID4 +" INTEGER, " +
                COLUMN_DESTINATION_ID3 +" INTEGER);";

        String query4="CREATE TABLE " + TABLE_NAME4 +
                " (" + COLUMN_ID4 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME4 + " TEXT, " +
                COLUMN_PASSENGER_TYPE4 + " TEXT, " +
                COLUMN_BALANCE4 + " INTEGER, " +
                COLUMN_PACKAGE_ID4 +" INTEGER);";

        String query5="CREATE TABLE " + TABLE_NAME5 +
                " (" + COLUMN_ID5 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PASSENGER_ID5 + " INTEGER, " +
                COLUMN_ACTIVITY_ID5 +" INTEGER);";

        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL(query4);
        db.execSQL(query5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME5);
       onCreate(db);
    }

    //Adding a Activity into database
    void dbAddActivity(String name, String description, int cost,int capacity,int destination_id,String package_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME3, name);
        cv.put(COLUMN_DESCRIPTION3, description);
        cv.put(COLUMN_COST3, cost);
        cv.put(COLUMN_CAPACITY3, capacity);
        cv.put(COLUMN_SPACES_AVAILABLE3,capacity);
        cv.put(COLUMN_DESTINATION_ID3,destination_id);
        cv.put(COLUMN_PACKAGE_ID3,Integer.parseInt(package_id));

        long result = db.insert(TABLE_NAME3, null, cv);
        if(result==-1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    //Adding a package into database
    void dbAddPackage(String name, int capacity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME1, name);
        cv.put(COLUMN_CAPACITY1, capacity);
        cv.put(COLUMN_SPACES_AVAILABLE1,capacity);
       long result = db.insert(TABLE_NAME1, null, cv);
       if(result==-1){
           Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
       }else{
           Toast.makeText(context,"Added Successfully!", Toast.LENGTH_SHORT).show();
       }
    }

    //Adding a destination into database
    void dbAddDestination(String name, int package_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME2, name);
        cv.put(COLUMN_PACKAGE_ID2, package_id);

        long result = db.insert(TABLE_NAME2, null, cv);
        if(result==-1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    //Adding a pssenger into database
    void dbAddPassenger(String name, String passenger_type, int balance, int package_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME4, name);
        cv.put(COLUMN_PASSENGER_TYPE4, passenger_type);
        cv.put(COLUMN_BALANCE4, balance);
        cv.put(COLUMN_PACKAGE_ID4, package_id);

        long result = db.insert(TABLE_NAME4, null, cv);
        if(result==-1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    //Adding activities of a passenger into database
    void dbAddPassengerActivity(int passenger_id,int activity_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PASSENGER_ID5, passenger_id);
        cv.put(COLUMN_ACTIVITY_ID5, activity_id);

        long result = db.insert(TABLE_NAME5, null, cv);
        if(result==-1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    //Deleting activity of a passenger from database
    void dbDeletePassengerActivity(int passenger_id,int activity_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ACTIVITY_ID5, activity_id);

        //long result = db.insert(TABLE_NAME5, null, cv);
        long result1 = db.delete(TABLE_NAME5,COLUMN_ACTIVITY_ID5 + "=" + activity_id + " AND "+
                COLUMN_PASSENGER_ID5 + "=" +passenger_id,null);
        if(result1==-1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Deleted Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    //Getting passenger id from passenger name
    int dbGetPassengerId(String  name){
        SQLiteDatabase db=this.getReadableDatabase();

        //String query= "SELECT "+COLUMN_NAME4+" FROM "+ TABLE_NAME4;
        String Uquery=String.format("SELECT * FROM %s WHERE %s = '"+name+"'",TABLE_NAME4,COLUMN_NAME4);
        //" WHERE "+ COLUMN_NAME4 + " = "+"Ram"+";"

        Cursor cursor = db.rawQuery(Uquery,null);
        int ans=0;
        while (cursor.moveToNext()){
            ans=Integer.parseInt(cursor.getString(0));
            Log.d(TAG,(cursor.getString(0)));
        }
        return ans;
    }


    //Reading all the package data
    Cursor readAllPackageData(){
        String query = "SELECT * FROM "+ TABLE_NAME1;
        SQLiteDatabase db= this.getReadableDatabase();

        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    //Reading all the destinations of a package
    Cursor readAllDestinationData(int id){
        String query = "SELECT * FROM "+TABLE_NAME2+" WHERE "+ COLUMN_PACKAGE_ID2+ " = "+id;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    //Reading all the activities of a destination
    Cursor readAllActivityData(int id){
        String query = "SELECT * FROM "+TABLE_NAME3+ " WHERE " + COLUMN_DESTINATION_ID3+ " = "+id;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    //Reading all the activities of a package
    Cursor readAllActivityDataByPackID(int id) {
        String query = "SELECT * FROM "+TABLE_NAME3+ " WHERE " + COLUMN_PACKAGE_ID3+ " = "+id;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    //Reading all the passengers data of a package
    Cursor readAllPassengerData(int id){
        String query = "SELECT * FROM "+TABLE_NAME4+ " WHERE " + COLUMN_PACKAGE_ID4+ " = "+id;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    //Reading all the activity data a passenger
    Cursor readAllPassengerActivityData(int id){
        String query = "SELECT * FROM "+TABLE_NAME5+ " WHERE " + COLUMN_PASSENGER_ID5+ " = "+id;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

   //Decrementing the passenger space of a package
    void dbDecrementPassengerSpaces(String package_id) {
        SQLiteDatabase db=this.getReadableDatabase();
        String query = "UPDATE " + TABLE_NAME1 +" SET "+ COLUMN_SPACES_AVAILABLE1 + " = "+
                 COLUMN_SPACES_AVAILABLE1 + "-1 " + " WHERE "+ COLUMN_ID1+" = "+package_id;
        db.execSQL(query);
    }

    //Decrementing the passenger space of an activity
    void dbDecrementActivitySpaces(int id) {
        SQLiteDatabase db=this.getReadableDatabase();
        String query = "UPDATE " + TABLE_NAME3 +" SET "+ COLUMN_SPACES_AVAILABLE3 + " = "+
                COLUMN_SPACES_AVAILABLE3 + "-1 " + " WHERE "+ COLUMN_ID3+" = "+id;
        db.execSQL(query);
    }

    //Incrementing the passenger space of an activity
    void dbIncrementActivitySpaces(int id) {
        SQLiteDatabase db=this.getReadableDatabase();
        String query = "UPDATE " + TABLE_NAME3 +" SET "+ COLUMN_SPACES_AVAILABLE3 + " = "+
                COLUMN_SPACES_AVAILABLE3 + "+1 " + " WHERE "+ COLUMN_ID3+" = "+id;
        db.execSQL(query);
    }

    //Update balance of a passenger
    public void dbUpdatePassengerBalance(int passenger_id, String passenger_type, int cost, int i) {
        SQLiteDatabase db=this.getReadableDatabase();
        String query;
        if(passenger_type=="gold") cost=(int)0.9*cost;
        else if(passenger_type=="premium") cost=0;

        if(i==0){
            query = "UPDATE " + TABLE_NAME4 +" SET "+ COLUMN_BALANCE4 + " = "+
                    COLUMN_BALANCE4 + " - "+cost + " WHERE "+ COLUMN_ID4+" = "+passenger_id;
        }
        else{
            query = "UPDATE " + TABLE_NAME4 +" SET "+ COLUMN_BALANCE4 + " = "+
                    COLUMN_BALANCE4 + " + "+cost + " WHERE "+ COLUMN_ID4+" = "+passenger_id;
        }
        db.execSQL(query);
    }

    //Getting the balance of a passenger by its id
     int dbGetPassengerBalance(int passenger_id) {
         SQLiteDatabase db=this.getReadableDatabase();

         String Uquery=String.format("SELECT * FROM %s WHERE %s = '"+passenger_id+"'",TABLE_NAME4,COLUMN_ID4);

         Cursor cursor = db.rawQuery(Uquery,null);
         int ans=0;
         while (cursor.moveToNext()){
             ans=Integer.parseInt(cursor.getString(3));
             Log.d(TAG,(cursor.getString(3)));
         }
         return ans;
    }

    //Getting the available spaces of a package
    public int dbGetPassengerSpaces(String package_id) {
        SQLiteDatabase db=this.getReadableDatabase();

        String Uquery=String.format("SELECT * FROM %s WHERE %s = '"+package_id+"'",TABLE_NAME1,COLUMN_ID1);

        Cursor cursor = db.rawQuery(Uquery,null);
        int ans=0;
        while (cursor.moveToNext()){
            ans=Integer.parseInt(cursor.getString(3));
            Log.d(TAG,(cursor.getString(3)));
        }
        return ans;
    }

    //Getting the passenger capacity of a package
    public int dbGetPassengerCapacity(String package_id) {
        SQLiteDatabase db=this.getReadableDatabase();

        String Uquery=String.format("SELECT * FROM %s WHERE %s = '"+package_id+"'",TABLE_NAME1,COLUMN_ID1);

        Cursor cursor = db.rawQuery(Uquery,null);
        int ans=0;
        while (cursor.moveToNext()){
            ans=Integer.parseInt(cursor.getString(2));
            Log.d(TAG,(cursor.getString(2)));
        }
        return ans;
    }

    //Getting the spaces available of an activity
    public int dbGetActivitySpaces(int act_id) {
        SQLiteDatabase db=this.getReadableDatabase();

        String Uquery=String.format("SELECT * FROM %s WHERE %s = '"+act_id+"'",TABLE_NAME3,COLUMN_ID3);

        Cursor cursor = db.rawQuery(Uquery,null);
        int ans=0;
        while (cursor.moveToNext()){
            ans=Integer.parseInt(cursor.getString(5));
            Log.d(TAG,(cursor.getString(5)));
        }
        return ans;
    }

    //Getting activity name by its id
    public String getActivityNameById(String s) {
        String ans = null;
        SQLiteDatabase db=this.getReadableDatabase();
        String Uquery=String.format("SELECT * FROM %s WHERE %s = '"+Integer.parseInt(s)+"'",TABLE_NAME3,COLUMN_ID3);
        Cursor cursor = db.rawQuery(Uquery,null);
        while (cursor.moveToNext()){
            ans=cursor.getString(1);
            break;
        }
        return ans;
    }

    //getting destination name by its id
    public String getDestinationName(String s){
        String ans=null;
        SQLiteDatabase db=this.getReadableDatabase();
        String Uquery=String.format("SELECT * FROM %s WHERE %s = '"+Integer.parseInt(s)+"'",TABLE_NAME2,COLUMN_ID2);
        Cursor cursor = db.rawQuery(Uquery,null);
        while (cursor.moveToNext()){
            ans=cursor.getString(1);
            break;
        }
        return  ans;
    }
    public String getDestinationNameById(String s) {
        String ans = null;
        SQLiteDatabase db=this.getReadableDatabase();
        String Uquery=String.format("SELECT * FROM %s WHERE %s = '"+Integer.parseInt(s)+"'",TABLE_NAME3,COLUMN_ID3);
        Cursor cursor = db.rawQuery(Uquery,null);
        while (cursor.moveToNext()){
            ans=cursor.getString(6);
            break;
        }
        Uquery=String.format("SELECT * FROM %s WHERE %s = '"+Integer.parseInt(ans)+"'",TABLE_NAME2,COLUMN_ID2);
        cursor = db.rawQuery(Uquery,null);
        while (cursor.moveToNext()){
            ans=cursor.getString(1);
            break;
        }
        return ans;
    }

    //getting the cost of a activity by activity id
    public String getActivityCostById(String s) {
        String ans = null;
        SQLiteDatabase db=this.getReadableDatabase();
        String Uquery=String.format("SELECT * FROM %s WHERE %s = '"+Integer.parseInt(s)+"'",TABLE_NAME3,COLUMN_ID3);
        Cursor cursor = db.rawQuery(Uquery,null);
        while (cursor.moveToNext()){
            ans=cursor.getString(3);
            break;
        }
        return ans;
    }

    //returns the activites of a destination that has non-zero spaces left
    public Cursor dbGetActivitiesWithSpaces(int destination_id) {
        String query = "SELECT * FROM "+ TABLE_NAME3 + " WHERE ("+COLUMN_DESTINATION_ID3
                +" = "+destination_id+" AND "+COLUMN_SPACES_AVAILABLE3+ ">0"+")";
        SQLiteDatabase db= this.getReadableDatabase();

        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }
}
