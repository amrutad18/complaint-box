package com.example.amruta.homescreen2.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.amruta.homescreen2.Model.User;
import com.example.amruta.homescreen2.Model.Complaint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amruta on 27/10/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper  {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Database.db";

    // User table name
    private static final String TABLE_USER = "user";

    // User Table Columns names
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_CONTACT = "user_contact";
    private static final String COLUMN_USER_ADDRESS = "user_address";

    private static final String COLUMN_USER_PASSWORD = "user_password";
    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_NAME + " TEXT,"+ COLUMN_USER_EMAIL + " TEXT,"+ COLUMN_USER_CONTACT + " TEXT,"
            + COLUMN_USER_ADDRESS + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    // drop table sql query for user table
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    private static final String TABLE_COMPLAINTS = "complaints";

    // Comlaint table  Columns names
    private static final String COLUMN_USER_EMAILC = "user_email";
    private static final String COLUMN_PROD_TYPE = "prod_type";
    private static final String COLUMN_MODEL_NO = "model_no";
    private static final String COLUMN_FDATE = "file_date";
    private static final String COLUMN_CDATE = "close_date";
    private static final String COLUMN_PRIORITY = "priority";
    private static final String COLUMN_STATUS = "status_code";
    private static final String COLUMN_DESCRIPTION ="description";

    // create table sql query
    private String CREATE_COMPLAINT_TABLE = "CREATE TABLE " + TABLE_COMPLAINTS + "("
            + COLUMN_USER_EMAILC + " TEXT," + COLUMN_PROD_TYPE + " TEXT,"
            + COLUMN_MODEL_NO + " TEXT," + COLUMN_FDATE + " TEXT DEFAULT CURRENT_TIMESTAMP," + COLUMN_CDATE + " TEXT," + COLUMN_PRIORITY + " INTEGER," +
            COLUMN_STATUS + " INTEGER," + COLUMN_DESCRIPTION + " TEXT" + ")";

    // drop table sql query
    private String DROP_COMPLAINT_TABLE = "DROP TABLE IF EXISTS " + TABLE_COMPLAINTS;


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_COMPLAINT_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_COMPLAINT_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_CONTACT, user.getContact());
        values.put(COLUMN_USER_ADDRESS, user.getAddress());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method is to create new Complaint record
     *
     * @param complaint
     */
    public void addComplaint(Complaint complaint) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAILC,complaint.getUser());
        values.put(COLUMN_PROD_TYPE,complaint.getProductType());
        values.put(COLUMN_MODEL_NO,complaint.getModelNo());
        values.put(COLUMN_DESCRIPTION,complaint.getDetails());
        values.put(COLUMN_STATUS,0);//0 status means the complaint has been registered
        // 2 means redressal is in process
        // 1 means the complaint has resloved
        values.put(COLUMN_CDATE,0);
        values.put(COLUMN_PRIORITY,complaint.getPriority());
        // Inserting Row
        db.insert(TABLE_COMPLAINTS, null, values);
        db.close();
        System.out.println("inserted values");
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_CONTACT,
                COLUMN_USER_ADDRESS,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_EMAIL+ " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setContact(cursor.getString(cursor.getColumnIndex(COLUMN_USER_CONTACT)));
                user.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ADDRESS)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method is to fetch all complaints and return the list
     *
     * @return list
     */
    public  List<Complaint> getAllComplaints(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_EMAILC,
                COLUMN_PROD_TYPE,
                COLUMN_MODEL_NO,
                COLUMN_FDATE,
                COLUMN_STATUS,
                COLUMN_PRIORITY,
                COLUMN_CDATE,
                COLUMN_DESCRIPTION

        };
        // sorting orders
        String sortOrder =
                COLUMN_STATUS + " ASC," +COLUMN_PRIORITY+ " ASC, " + COLUMN_FDATE + " ASC";

        List<Complaint> complaintList = new ArrayList<Complaint>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {email};

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_COMPLAINTS, //Table to query
                columns,    //columns to return
                COLUMN_USER_EMAILC + "=?",        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Complaint c = new Complaint();
                c.setUser(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAILC)));
                c.setProductType(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_TYPE)));
                c.setModelNo(cursor.getString(cursor.getColumnIndex(COLUMN_MODEL_NO)));
                c.setFileDate(cursor.getString(cursor.getColumnIndex(COLUMN_FDATE)));
                c.setStatus_code(cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)));
                c.setPriority(cursor.getInt(cursor.getColumnIndex(COLUMN_PRIORITY)));
                c.setCloseDate(cursor.getString(cursor.getColumnIndex(COLUMN_CDATE)));
                c.setDetails(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                // Adding record to list
                complaintList.add(c);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        System.out.println("list fetched");

        // return user list
        return complaintList;
    }

    public  List<Complaint> getAllComplaints() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_EMAILC,
                COLUMN_PROD_TYPE,
                COLUMN_MODEL_NO,
                COLUMN_FDATE,
                COLUMN_STATUS,
                COLUMN_PRIORITY,
                COLUMN_CDATE,
                COLUMN_DESCRIPTION

        };
        // sorting orders
        String sortOrder =
                COLUMN_STATUS + " ASC," +COLUMN_PRIORITY+ " ASC, " + COLUMN_FDATE + " ASC";
        List<Complaint> complaintList = new ArrayList<Complaint>();

        SQLiteDatabase db = this.getReadableDatabase();
        //String[] selectionArgs = {email};

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_COMPLAINTS, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Complaint c = new Complaint();
                c.setUser(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAILC)));
                c.setProductType(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_TYPE)));
                c.setModelNo(cursor.getString(cursor.getColumnIndex(COLUMN_MODEL_NO)));
                c.setFileDate(cursor.getString(cursor.getColumnIndex(COLUMN_FDATE)));
                c.setStatus_code(cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)));
                c.setPriority(cursor.getInt(cursor.getColumnIndex(COLUMN_PRIORITY)));
                c.setCloseDate(cursor.getString(cursor.getColumnIndex(COLUMN_CDATE)));
                c.setDetails(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                // Adding record to list
                complaintList.add(c);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        System.out.println("list fetched");

        // return user list
        return complaintList;
    }


    public void updateComplaintStatus(Complaint complaint) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, complaint.getStatus_code());

        // updating row
        db.update(TABLE_COMPLAINTS, values, COLUMN_USER_EMAIL + " = ? AND " + COLUMN_FDATE +" = ?",
                new String[]{String.valueOf(complaint.getUser()), String.valueOf(complaint.getFileDate())});
        db.close();
    }

    public long getResolvedNum(User user)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        long numRows = DatabaseUtils.queryNumEntries(db, TABLE_COMPLAINTS, COLUMN_USER_EMAIL + " = ? AND " + COLUMN_STATUS + " = 1",
                new String[]{String.valueOf(user.getEmail())});
        return numRows;
    }

    public long getPendingNum(User user)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        long numRows = DatabaseUtils.queryNumEntries(db, TABLE_COMPLAINTS, COLUMN_USER_EMAIL + " = ? AND " + COLUMN_STATUS + " = 0",
                new String[]{String.valueOf(user.getEmail())});
        return numRows;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_CONTACT, user.getContact());
        values.put(COLUMN_USER_ADDRESS, user.getAddress());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_EMAIL + " = ?",
                new String[]{String.valueOf(user.getEmail())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_EMAIL + " = ?",
                new String[]{String.valueOf(user.getEmail())});
        db.close();
    }

    /**
     * This method is to delete complaint record
     *
     * @param complaint
     */
    public void deleteComplaint(String email,String model) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete record by id
        //String email = complaint.getUser();
        //String model = complaint.getModelNo();
        db.delete(TABLE_COMPLAINTS, COLUMN_USER_EMAILC + " = ? AND " + COLUMN_MODEL_NO + "= ?",
                new String[]{email, model});
        db.close();
    }


    /**
     * This method to check user exist or not
     *
     * @param email/password
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_NAME
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    public boolean checkComplaint(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_DESCRIPTION
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAILC + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_COMPLAINTS, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_NAME
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

}



