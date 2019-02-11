package com.example.mohammedalrabeai.ali2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by ibrahim on 9/14/2016.
 */

/**
 * Created by ibrahim on 8/26/2016.
 */
public class A_TableDB {
    public static final String AMOUNT = "amount";
    public static final String DATE_TIME_DIBET ="dibet_date_time" ;
    public static final String TIME_DIBET = "dibet_time";
    public static final String DISCRIPTION = "discription";
    public static final String NOTE = "note";
    private static final String DIBET_TABLE ="dibet" ;
    private final String DB_FILEPATH;
    private static final String DATABASE_NAME = "services";
    private static final int DATABASE_VERSION = 1;

       private static final String CUSTOMERS_TABLE = "customers";
    private static final String TAJERS_TABLE = "tajers";
    public static final String ID_CUSTOM = "id_custom";
    public static final String ID_CUSTOM_OUTO = "id_custom_outo";
    public static final String NAME_CUSTOM = "name_custom";
//    public static final String MODEL_CUSTOM = "model_custom";
//    public static final String MANUFACTURE_CUSTOM = "manufacture_custom";
//    public static final String PROBLEM_CUSTOM = "problem_custom";
//    public static final String DELIVER_tIM_CUSTOM = "deliver_tim_custom";
   public static final String PHONE_CUSTOM = "phone_custom";
    public static final String NOTE_CUSTOM = "note_custom";
    public static final String NOTE2_CUSTOM = "note2_custom";


    private static final String DEVICESCUSTOMERS_TABLE = "devicescustomers";
    public static final String ID_DEVICE_OUTO = "id_device_outo";
    public static final String MODEL_CUSTOM = "model_custom";
    public static final String MANUFACTURE_CUSTOM = "manufacture_custom";
    public static final String PROBLEM_CUSTOM = "problem_custom";
    public static final String DELIVER_tIM_CUSTOM = "deliver_tim_custom";
    public static final String COST_CUSTOM = "cost_custom";
    public static final String TYP_SERVIC_CUSTOM = "typ_servic_custom";
    public static final String DATE_TIME_in = "in_date_time";
    public static final String NOTE_DEVICE = "note_device";
    public static final String NOTE2_DEVICE = "note2_device";


    private static final String MONY_OUT_TABLE = "monyout";
    public static final String ID_MONYOUT_OUTO = "id_onyout_outo";
//    public static final String MODEL_mony_out = "model_custom";
//    public static final String MANUFACTURE_mony_out = "manufacture_custom";
    public static final String NAME_PIECE= "name_piece";
    public static final String DATE_TIME_MONY_OUT = "monyout_date_time";
    public static final String COST_MNY_OUT= "cost_monyout";
    public static final String DISCRIPTION_MONY_OUT = "description_monyout";
    public static final String NOTE_MONY_OUT = "note_monyout";



    private static final String MONYIN_TABLE = "monyin";
    public static final String ID_MONYIN_OUTO = "id_monyin_outo";
    public static final String COST_MNY_IN= "cost_monyin";
    public static final String DATE_TIME_MONY_IN = "monyin_date_time";
    public static final String NOTE_MONY_IN = "note_monyn";

    private static final String DELIVEREDDEVICES_TABLE = "delivereddevices";
    public static final String ID_DELV_OUTO = "id_delv_outo";
    public static final String COST_ALL= "cost_all";
    public static final String COST_LOST= "cost_lost";
    public static final String STATUS= "status";
    public static final String DATE_TIME_DEVICES = "deliverd_date_time";
    public static final String NOTE_DELIVERED = "note_delivered";


    private static final String PIECES_TABLE = "pieces";
    public static final String ID_PIECE_OUTO = "id_piece_outo";
    public static final String MODEL_PIECES = "model_piece";
        public static final String MANUFACTURE_PIECES = "manufacture_piece";
    public static final String NAME_PIECE_PIECES= "name_piece";
    public static final String DATE_TIME_PIECES = "piece_date_time";
    public static final String COST_PIECES= "cost_piece";
    public static final String OWNER= "owner";
    public static final String DISCRIPTION_PIECES = "description_monyout";
    public static final String NOTE_PIECES = "note_monyout";

    public static final String TIBLE_ID = "tible_id";
    private static final String UNIQUE = " UNIQUE ";
    private static final String INTEGER = " INTEGER ";
    private static final String TEXT = " TEXT ";
    private static final String INTEGERC = " INTEGER, ";
    private static final String TEXTC = " TEXT, ";
    private static final String PARENTHSE_LEFT = " ( ";
    private static final String PARENTHSE_RIGHT = " ) ";
    private static final String CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS ";
    private static final String INTEGER_PRIMARY_KEY = " INTEGER PRIMARY KEY, ";

    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";
    public static final String TABLENAME_TABLE ="tables_inf";
    public static final String ID_TABLE_OUTO= "id_table";
    public static final String TABLE_NAME ="table_name";
    public static final String CUL_NUM="cul_num";
    public static final String NOTE_TABLES="note";
//    public static final String KEY_ROWID = "_id";
//    public static final String KEY_SUBNUM = "_N";
//    public static final String KEY_DAYID = "day_id";


    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private static final String CUSTOMERS_TABLE_CREATE =
            "create table " + CUSTOMERS_TABLE + " ("
                    + ID_CUSTOM_OUTO + " integer primary key autoincrement, "
                    +NAME_CUSTOM + " text not null, "
                    +ID_CUSTOM +" integer,"
                    + PHONE_CUSTOM + " text not null,"
                    + NOTE_CUSTOM+ " text not null, "
                    + NOTE2_CUSTOM + " text not null);";
    private static final String TAJERS_TABLE_CREATE =
            "create table " + TAJERS_TABLE + " ("
                    + ID_CUSTOM_OUTO + " integer primary key autoincrement, "
                    +NAME_CUSTOM + " text not null, "
                    +ID_CUSTOM +" integer,"
                    + PHONE_CUSTOM + " text not null,"
                    + NOTE_CUSTOM+ " text not null, "
                    + NOTE2_CUSTOM + " text not null);";
    private static final String DEVICESCUSTOMERS_TABLE_CREATE =
            "create table " + DEVICESCUSTOMERS_TABLE + " ("
                    + ID_DEVICE_OUTO + " integer primary key autoincrement, "
                    +ID_CUSTOM +" integer,"
                    +MODEL_CUSTOM + " text not null, "
                    +MANUFACTURE_CUSTOM + " text not null, "
                    +PROBLEM_CUSTOM + " text not null, "
                    +DELIVER_tIM_CUSTOM + " text not null, "
                    +COST_CUSTOM + " text not null, "
                    +TYP_SERVIC_CUSTOM + " text not null, "
                    +DATE_TIME_in + " text not null, "
                    + NOTE_DEVICE+ " text not null, "
                    + NOTE2_DEVICE + " text not null);";


    private static final String MONY_OUT_TABLE_CREATE =
            "create table " + MONY_OUT_TABLE + " ("
                    + ID_MONYOUT_OUTO + " integer primary key autoincrement, "
                    +ID_CUSTOM +" integer,"
                    +NAME_PIECE + " text not null, "
                    +DATE_TIME_MONY_OUT + " text not null, "
                    +COST_MNY_OUT + " text not null, "
                    +DISCRIPTION_MONY_OUT + " text not null, "
                    +NOTE_MONY_OUT + " text not null);";
    private static final String MONYIN_TABLE_CREATE =
            "create table " + MONYIN_TABLE + " ("
                    + ID_MONYIN_OUTO + " integer primary key autoincrement, "
                    +ID_CUSTOM +" integer,"
                    +COST_MNY_IN + " text not null, "
                    +DATE_TIME_MONY_IN + " text not null, "
                    +NOTE_MONY_IN + " text not null);";
    private static final String DELIVEREDDEVICES_TABLE_CREATE =
            "create table " + DELIVEREDDEVICES_TABLE + " ("
                    + ID_DELV_OUTO + " integer primary key autoincrement, "
                    +ID_CUSTOM +" integer,"
                    +COST_ALL + " text not null, "
                    +COST_LOST + " text not null, "
                    +STATUS + " text not null, "
                    +DATE_TIME_DEVICES + " text not null, "
                    +NOTE_DELIVERED + " text not null);";
    private static final String PIECES_TABLE_CREATE =
            "create table " + PIECES_TABLE + " ("
                    + ID_PIECE_OUTO + " integer primary key autoincrement, "
                    +ID_CUSTOM +" integer,"
                    +MODEL_PIECES + " text not null, "
                    +MANUFACTURE_PIECES + " text not null, "
                    +NAME_PIECE_PIECES + " text not null, "
                    +DATE_TIME_PIECES + " text not null, "
                    +OWNER + " text not null, "
                    +DISCRIPTION_PIECES + " text not null, "
                    +NOTE_PIECES + " text not null);";
    private static final String TABLES_TABLE_CREATE =
            "create table " + TABLENAME_TABLE + " ("
                    + ID_TABLE_OUTO + " integer primary key autoincrement, "
                    +TABLE_NAME + " text not null, "
                    +CUL_NUM +" integer,"
                    +NOTE_TABLES + " text not null);";


    private final Context mCtx;
    public A_TableDB(Context ctx) {
        this.mCtx = ctx;
        final String packageName = ctx.getPackageName();
        DB_FILEPATH = "/data/user/0/" + packageName + "/databases/services";
//        DB_FILEPATH = "/data/data/" + packageName + "/databases/services.db";
    }


    public A_TableDB open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

   /* public long createReminder1(String title, String body, String
            reminderDateTime) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_BODY, body);
        initialValues.put(KEY_DATE_TIME, reminderDateTime);
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }*/

    public long createCustom(String[] ss  ){
      //  ss=new String[10];
        ContentValues initialValues = new ContentValues();
        initialValues.put(NAME_CUSTOM, ss[1]);
        initialValues.put(ID_CUSTOM, ss[2]);
        initialValues.put(PHONE_CUSTOM, ss[3]);
        initialValues.put(NOTE_CUSTOM, ss[4]);
        initialValues.put(NOTE2_CUSTOM, ss[5]);

        return mDb.insert(CUSTOMERS_TABLE, null, initialValues);
    }
    public long createTajers(String[] ss  ){
        //  ss=new String[10];
        ContentValues initialValues = new ContentValues();
        initialValues.put(NAME_CUSTOM, ss[1]);
        initialValues.put(ID_CUSTOM, ss[2]);
        initialValues.put(PHONE_CUSTOM, ss[3]);
        initialValues.put(NOTE_CUSTOM, ss[4]);
        initialValues.put(NOTE2_CUSTOM, ss[5]);

        return mDb.insert(TAJERS_TABLE, null, initialValues);
    }

    public long createDEVICESCUSTOMERS(String[] ss  ){
              ContentValues initialValues = new ContentValues();
        initialValues.put(ID_CUSTOM, ss[1]);
        initialValues.put(MODEL_CUSTOM, ss[2]);
        initialValues.put(MANUFACTURE_CUSTOM, ss[3]);
        initialValues.put(PROBLEM_CUSTOM, ss[4]);
        initialValues.put(DELIVER_tIM_CUSTOM, ss[5]);
        initialValues.put(COST_CUSTOM, ss[6]);
        initialValues.put(TYP_SERVIC_CUSTOM, ss[7]);
        initialValues.put(DATE_TIME_in, ss[8]);
        initialValues.put(NOTE_DEVICE, ss[9]);
        initialValues.put(NOTE2_DEVICE, ss[10]);
        return mDb.insert(DEVICESCUSTOMERS_TABLE, null, initialValues);
    }
    public long createDELIVEREDDEVICES(String[] ss  ){
         ContentValues initialValues = new ContentValues();
        initialValues.put(ID_CUSTOM, ss[1]);
        initialValues.put(COST_ALL, ss[2]);
        initialValues.put(COST_LOST, ss[3]);
        initialValues.put(STATUS, ss[4]);
        initialValues.put(DATE_TIME_DEVICES, ss[5]);
        initialValues.put(NOTE_DELIVERED, ss[6]);
        return mDb.insert(DELIVEREDDEVICES_TABLE, null, initialValues);
    }
    public long createMONY_OUT(String[] ss  ){
        ContentValues initialValues = new ContentValues();
       int m = Integer.parseInt(ss[1]);
        initialValues.put(ID_CUSTOM, m);
        initialValues.put(NAME_PIECE, ss[2]);
        initialValues.put(DATE_TIME_MONY_OUT, ss[3]);
        initialValues.put(COST_MNY_OUT, ss[4]);
        initialValues.put(DISCRIPTION_MONY_OUT, ss[5]);
        initialValues.put(NOTE_MONY_OUT, ss[6]);
        return mDb.insert(MONY_OUT_TABLE, null, initialValues);
    }
    public long createDibet(String[] ss  ){

        ContentValues initialValues = new ContentValues();
        int m = Integer.parseInt(ss[1]);
        initialValues.put(ID_CUSTOM, m);
        initialValues.put(AMOUNT, ss[2]);
        initialValues.put(DATE_TIME_DIBET, ss[3]);
        initialValues.put(TIME_DIBET, ss[4]);
        initialValues.put(DISCRIPTION, ss[5]);
        initialValues.put(NOTE, ss[6]);
        return mDb.insert(DIBET_TABLE, null, initialValues);
    }
    public long createMONY_IN(String[] ss  ){
        ContentValues initialValues = new ContentValues();
        initialValues.put(ID_CUSTOM, Integer.parseInt(ss[1]));
        initialValues.put(COST_MNY_IN, ss[2]);
        initialValues.put(DATE_TIME_MONY_IN, ss[3]);
        initialValues.put(NOTE_MONY_IN, ss[4]);
        return mDb.insert(MONYIN_TABLE, null, initialValues);
    }
    public long createPIECES(String[] ss  ){
        ContentValues initialValues = new ContentValues();
        initialValues.put(ID_CUSTOM, ss[1]);
        initialValues.put(MODEL_PIECES, ss[2]);
        initialValues.put(MANUFACTURE_PIECES, ss[3]);
        initialValues.put(NAME_PIECE_PIECES, ss[4]);
        initialValues.put(DATE_TIME_PIECES, ss[5]);
        initialValues.put(OWNER, ss[6]);
        initialValues.put(DISCRIPTION_PIECES, ss[7]);
        initialValues.put(NOTE_PIECES, ss[8]);
        return mDb.insert(PIECES_TABLE, null, initialValues);
    }

//    public long createReminder(String day, int number, String timeone, String subOne ,String timeTwo,String subTwo
//                            ,   String timeTree ,String subTree ,String timeFour ,String subFour,
//                               String body, String reminderDateTime) {
//        ContentValues initialValues = new ContentValues();
//        initialValues.put(KEY_DAYID, day);
//        initialValues.put(KEY_SUBNUM, number);
//        initialValues.put(KEY_DATE_TIME_ONE, timeone);
//        initialValues.put(KEY_TITLE_ONE, subOne);
//        initialValues.put(KEY_DATE_TIME_TWO, timeTwo);
//        initialValues.put(KEY_TITLE_TWO, subTwo);
//        initialValues.put(KEY_DATE_TIME_THREE, timeTree);
//        initialValues.put(KEY_TITLE_THREE, subTree);
//        initialValues.put(KEY_DATE_TIME_FOUR, timeFour);
//        initialValues.put(KEY_TITLE_FOUR, subFour);
//        initialValues.put(KEY_BODY, body);
//        initialValues.put(KEY_DATE_TIME, reminderDateTime);
//        return mDb.insert(DATABASE_TABLE, null, initialValues);
//    }

    public boolean deleteMONY_OUT(long rowId) {
        return  mDb.delete(MONY_OUT_TABLE, ID_MONYOUT_OUTO + "=" + rowId, null) > 0;
    }
    public boolean deleteDEVICESCUSTOMERS(long rowId) {
        return mDb.delete(DEVICESCUSTOMERS_TABLE, ID_DEVICE_OUTO + "=" + rowId, null) > 0;
    }
    public boolean deleteCustom(long rowId) {
        return  mDb.delete(CUSTOMERS_TABLE, ID_CUSTOM_OUTO + "=" + rowId, null) > 0;
    }
    public boolean deleteMONY_IN(long rowId) {
        return mDb.delete(MONY_OUT_TABLE, ID_MONYIN_OUTO + "=" + rowId, null) > 0;
    }
    public boolean deletePIECES(long rowId) {
        return mDb.delete(DEVICESCUSTOMERS_TABLE, ID_PIECE_OUTO + "=" + rowId, null) > 0;
    }
    public boolean deleteDELIVERE(long rowId) {
        return mDb.delete(CUSTOMERS_TABLE, ID_DELV_OUTO + "=" + rowId, null) > 0;
    }




    public Cursor fetchAllCUSTOMERS() {
        return mDb.query(CUSTOMERS_TABLE, new String[]{ ID_CUSTOM_OUTO ,
                NAME_CUSTOM ,ID_CUSTOM , PHONE_CUSTOM , NOTE_CUSTOM, NOTE2_CUSTOM }, null, null, null, null, null);
    }
    public Cursor fetchAllTAJERS() {
        return mDb.query(TAJERS_TABLE, new String[]{ ID_CUSTOM_OUTO ,
                NAME_CUSTOM ,ID_CUSTOM , PHONE_CUSTOM , NOTE_CUSTOM, NOTE2_CUSTOM }, null, null, null, null, null);
    }
    public Cursor fetchAllMONY_OUT() {
        return mDb.query(CUSTOMERS_TABLE, new String[]{ ID_MONYOUT_OUTO ,ID_CUSTOM
                ,NAME_PIECE ,DATE_TIME_MONY_OUT ,COST_MNY_OUT ,DISCRIPTION_MONY_OUT ,NOTE_MONY_OUT  }, null, null, null, null, null);
    }
    public Cursor fetchAllDEVICESCUSTOMERS() {
        return mDb.query(CUSTOMERS_TABLE, new String[]{ ID_DEVICE_OUTO ,ID_CUSTOM ,MODEL_CUSTOM
                ,MANUFACTURE_CUSTOM ,PROBLEM_CUSTOM ,DELIVER_tIM_CUSTOM ,COST_CUSTOM ,TYP_SERVIC_CUSTOM
                ,DATE_TIME_in, NOTE_DEVICE, NOTE2_DEVICE}, null, null, null, null, null);
    }
    public Cursor fetchAllPIECES() {
        return mDb.query(PIECES_TABLE, new String[]{ID_PIECE_OUTO ,ID_CUSTOM
                ,MODEL_PIECES ,MANUFACTURE_PIECES ,NAME_PIECE_PIECES ,DATE_TIME_PIECES
                ,OWNER ,DISCRIPTION_PIECES ,NOTE_PIECES  }, null, null, null, null, null);
    }
    public Cursor fetchAllDELIVEREDDEVICES() {
        return mDb.query(DELIVEREDDEVICES_TABLE, new String[]{ID_DELV_OUTO ,ID_CUSTOM
                ,COST_ALL ,COST_LOST,STATUS ,DATE_TIME_DEVICES,NOTE_DELIVERED}, null, null, null, null, null);
    }
    public Cursor fetchAllMONYIN() {
        return mDb.query(MONYIN_TABLE, new String[]{ID_MONYIN_OUTO ,ID_CUSTOM ,COST_MNY_IN
                ,DATE_TIME_MONY_IN ,NOTE_MONY_IN}, null, null, null, null, null);
    }



    public Cursor fetchCUSTOM(long rowId) throws SQLException {
        Cursor mCursor =mDb.query(true, CUSTOMERS_TABLE, new String[]{ID_CUSTOM_OUTO ,
                                NAME_CUSTOM ,ID_CUSTOM , PHONE_CUSTOM , NOTE_CUSTOM, NOTE2_CUSTOM }, ID_CUSTOM_OUTO + "=" +
                                rowId, null,
                        null,null,null, null);
        if (mCursor != null) {mCursor.moveToFirst();}
        return mCursor;
    }
    public Cursor fetchMONY_OUT(long rowId) throws SQLException {
        Cursor mCursor =
                mDb.query(true, CUSTOMERS_TABLE, new String[]{ID_MONYOUT_OUTO ,ID_CUSTOM
                                ,NAME_PIECE ,DATE_TIME_MONY_OUT ,COST_MNY_OUT ,DISCRIPTION_MONY_OUT ,NOTE_MONY_OUT }, ID_CUSTOM_OUTO + "=" +
                                rowId, null,
                        null,null,null, null);
        if (mCursor != null) {mCursor.moveToFirst();}
        return mCursor;
    }
    public Cursor fetchDEVICESCUSTOMERS(long rowId) throws SQLException {
        Cursor mCursor =
                mDb.query(true, CUSTOMERS_TABLE, new String[]{ID_DEVICE_OUTO ,ID_CUSTOM ,MODEL_CUSTOM
                                ,MANUFACTURE_CUSTOM ,PROBLEM_CUSTOM ,DELIVER_tIM_CUSTOM ,COST_CUSTOM ,TYP_SERVIC_CUSTOM
                                ,DATE_TIME_in, NOTE_DEVICE, NOTE2_DEVICE}, ID_CUSTOM_OUTO + "=" +
                                rowId, null,
                        null,null,null, null);
        if (mCursor != null) {mCursor.moveToFirst();}
        return mCursor;
    }
    public Cursor fetchMONY_IN(long rowId) throws SQLException {
        Cursor mCursor =
                mDb.query(true, CUSTOMERS_TABLE, new String[]{ID_MONYIN_OUTO ,ID_CUSTOM ,COST_MNY_IN
                                ,DATE_TIME_MONY_IN ,NOTE_MONY_IN }, ID_CUSTOM_OUTO + "=" +
                                rowId, null,
                        null,null,null, null);
        if (mCursor != null) {mCursor.moveToFirst();}
        return mCursor;
    }
    public Cursor fetchDELIVERE(long rowId) throws SQLException {
        Cursor mCursor =
                mDb.query(true, CUSTOMERS_TABLE, new String[]{ID_DELV_OUTO ,ID_CUSTOM
                                ,COST_ALL ,COST_LOST,STATUS ,DATE_TIME_DEVICES,NOTE_DELIVERED}, ID_CUSTOM_OUTO + "=" +
                                rowId, null,
                        null,null,null, null);
        if (mCursor != null) {mCursor.moveToFirst();}
        return mCursor;
    }
    public Cursor fetchPIECES(long rowId) throws SQLException {
        Cursor mCursor =
                mDb.query(true, PIECES_TABLE, new String[]{ID_PIECE_OUTO ,ID_CUSTOM
                                ,MODEL_PIECES ,MANUFACTURE_PIECES ,NAME_PIECE_PIECES ,DATE_TIME_PIECES
                                ,OWNER ,DISCRIPTION_PIECES ,NOTE_PIECES  }, ID_CUSTOM + "=" +
                                rowId, null,
                        null,null,null, null);
        if (mCursor != null) {mCursor.moveToFirst();}
        return mCursor;
    }
    public Cursor fetchPIECES_AS_Date(String mDate) throws SQLException {
        Cursor mCursor =
                mDb.query(true, PIECES_TABLE, new String[]{ID_PIECE_OUTO ,ID_CUSTOM
                                ,MODEL_PIECES ,MANUFACTURE_PIECES ,NAME_PIECE_PIECES ,DATE_TIME_PIECES
                                ,OWNER ,DISCRIPTION_PIECES ,NOTE_PIECES  }, String.format("%s='%s'", DATE_TIME_PIECES, mDate), null,
                        null,null,null, null);
        if (mCursor != null) {mCursor.moveToFirst();}
        return mCursor;
    }

    public int findIdCUSTOM(String nam) throws SQLException {
        Cursor mCursor =mDb.query(true,CUSTOMERS_TABLE, new String[]{ID_CUSTOM ,ID_CUSTOM_OUTO}
        , String.format("%s='%s'", NAME_CUSTOM, nam),null,null,null,null,null);

        if (mCursor != null) {
            mCursor.moveToFirst();
//            Toast.makeText(mCtx, "row"+mCursor.getCount()+" col"+mCursor.getColumnCount(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(mCtx, "row"+mCursor.getColumnName(0)+" col"+mCursor.getColumnName(1), Toast.LENGTH_SHORT).show();
          // int idS = mCursor.getInt(mCursor.getColumnIndexOrThrow(mCursor.getColumnName(0)));
            int idS=0;
            int myNum;
            try {
                 idS = mCursor.getInt(mCursor.getColumnIndexOrThrow(A_TableDB.ID_CUSTOM_OUTO));
                 myNum = 0;
            }catch (Exception e){
                Toast.makeText(mCtx, "not fount", Toast.LENGTH_SHORT).show();
                return -1;
            }
            try {
               // myNum = Integer.parseInt(idS);
                myNum=idS;

            } catch(NumberFormatException nfe) {
                // Handle parse error.
                return -1;
            }
            mCursor.moveToFirst();
        return myNum;}
        return -1;
    }
    public Cursor fetchAllMONY_OUT_id(long id) {
        return mDb.query(MONY_OUT_TABLE, new String[]{ ID_MONYOUT_OUTO ,ID_CUSTOM
                ,NAME_PIECE ,DATE_TIME_MONY_OUT ,COST_MNY_OUT ,DISCRIPTION_MONY_OUT ,NOTE_MONY_OUT  }, ID_CUSTOM + "=" + id, null, null, null, null);
    }
    public Cursor fetchAllMONYIN_id(long id) {
        return mDb.query(MONYIN_TABLE, new String[]{ID_MONYIN_OUTO ,ID_CUSTOM ,COST_MNY_IN
                ,DATE_TIME_MONY_IN ,NOTE_MONY_IN}, ID_CUSTOM + "=" + id, null, null, null, null);
    }


        public boolean updateDELIVEREDDEVICES(long rowId,String[] ss  ){
        ContentValues initialValues = new ContentValues();
        initialValues.put(ID_CUSTOM, ss[1]);
        initialValues.put(COST_ALL, ss[2]);
        initialValues.put(COST_LOST, ss[3]);
        initialValues.put(STATUS, ss[4]);
        initialValues.put(DATE_TIME_DEVICES, ss[5]);
        initialValues.put(NOTE_DELIVERED, ss[6]);
        return
                mDb.update(DELIVEREDDEVICES_TABLE, initialValues, ID_DELV_OUTO + "=" + rowId, null) > 0;
    }
    public boolean updateDEVICESCUSTOMERS(long rowId,String[] ss  ){
        ContentValues initialValues = new ContentValues();
        initialValues.put(ID_CUSTOM, ss[1]);
        initialValues.put(MODEL_CUSTOM, ss[2]);
        initialValues.put(MANUFACTURE_CUSTOM, ss[3]);
        initialValues.put(PROBLEM_CUSTOM, ss[4]);
        initialValues.put(DELIVER_tIM_CUSTOM, ss[5]);
        initialValues.put(COST_CUSTOM, ss[6]);
        initialValues.put(TYP_SERVIC_CUSTOM, ss[7]);
        initialValues.put(DATE_TIME_in, ss[8]);
        initialValues.put(NOTE_DEVICE, ss[9]);
        initialValues.put(NOTE2_DEVICE, ss[10]);
        return
                mDb.update(DEVICESCUSTOMERS_TABLE, initialValues, ID_DEVICE_OUTO + "=" + rowId, null) > 0;
    }
    public boolean updateMONY_OUT(long rowId,String[] ss  ){
        ContentValues initialValues = new ContentValues();
        initialValues.put(ID_CUSTOM, ss[1]);
        initialValues.put(NAME_PIECE, ss[2]);
        initialValues.put(DATE_TIME_MONY_OUT, ss[3]);
        initialValues.put(COST_MNY_OUT, ss[4]);
        initialValues.put(DISCRIPTION_MONY_OUT, ss[5]);
        initialValues.put(NOTE_MONY_OUT, ss[6]);
        return
                mDb.update(MONY_OUT_TABLE, initialValues, ID_MONYOUT_OUTO + "=" + rowId, null) > 0;
    }
    public boolean updateMONY_IN(long rowId,String[] ss  ){
        ContentValues initialValues = new ContentValues();
        initialValues.put(ID_CUSTOM, ss[1]);
        initialValues.put(COST_MNY_IN, ss[2]);
        initialValues.put(DATE_TIME_MONY_IN, ss[3]);
        initialValues.put(NOTE_MONY_IN, ss[4]);
        return
                mDb.update(MONYIN_TABLE, initialValues, ID_MONYIN_OUTO + "=" + rowId, null) > 0;

    }
    public boolean updatePIECES(long rowId,String[] ss  ){
        ContentValues initialValues = new ContentValues();
        initialValues.put(ID_CUSTOM, ss[1]);
        initialValues.put(MODEL_PIECES, ss[2]);
        initialValues.put(MANUFACTURE_PIECES, ss[3]);
        initialValues.put(NAME_PIECE_PIECES, ss[4]);
        initialValues.put(DATE_TIME_PIECES, ss[5]);
        initialValues.put(OWNER, ss[6]);
        initialValues.put(DISCRIPTION_PIECES, ss[7]);
        initialValues.put(NOTE_PIECES, ss[8]);
        return
                mDb.update(PIECES_TABLE, initialValues, ID_PIECE_OUTO + "=" + rowId, null) > 0;
    }
    public boolean updateCustom(long rowId,String[] ss  ){


        ContentValues initialValues = new ContentValues();
        initialValues.put(NAME_CUSTOM, ss[1]);
        initialValues.put(ID_CUSTOM, ss[2]);
        initialValues.put(PHONE_CUSTOM, ss[3]);
        initialValues.put(NOTE_CUSTOM, ss[4]);
        initialValues.put(NOTE2_CUSTOM, ss[5]);
        return
                mDb.update(CUSTOMERS_TABLE, initialValues, ID_CUSTOM_OUTO + "=" + rowId, null) > 0;
    }

    public void createTable(String nameT,String []cul_name) {
        final StringBuilder sql = new StringBuilder();
        sql.append(CREATE_TABLE_IF_NOT_EXISTS);
        sql.append(nameT);
        sql.append(PARENTHSE_LEFT);
        sql.append(TIBLE_ID);
        sql.append(INTEGER_PRIMARY_KEY);
        for(int i=0;i<cul_name.length;i++) {
            if (cul_name[i] != null) {

            sql.append(cul_name[i]);
            if (i != cul_name.length - 1) {
                if (cul_name[i+1] != null) {
                sql.append(TEXTC);
            }
            }
            Log.d("A_TableDB:createTable: ", sql.toString());
        }
        }
//        sql.append();
////        sql.append(TEXTC);
////        sql.append(UNIQUE); // ensures uniqueness for languages
////        sql.append(PARENTHSE_LEFT);
////        sql.append(LANGUAGE_NAME);
////        sql.append(PARENTHSE_RIGHT);
        sql.append(PARENTHSE_RIGHT);
        Log.d("CREATE_TABLE_LANGUAGE", sql.toString());
      mDb.execSQL(sql.toString());

    }

    public Long insertTable(String nameT, int cul,String note) {
        Long idC;
        ContentValues CV=new ContentValues();
        CV.put(TABLE_NAME,nameT);
        CV.put(CUL_NUM,cul);
        CV.put(NOTE_TABLES,note);
       return idC = mDb.insert(TABLENAME_TABLE, null, CV);
    }

    public Cursor fetchTablesInf() {
        return mDb.query(TABLENAME_TABLE,new String[]{ID_TABLE_OUTO,TABLE_NAME,CUL_NUM,NOTE_TABLES},null,null,null,null,null);
    }

    public Long insertRowCustomTable(String nameT, String[] cul_name,int cul, String[] data) {
        Long idC;
        ContentValues CV = new ContentValues();
        for (int i = 0; i < cul; i++) {
            CV.put(cul_name[i], data[i]);
        }
        return idC = mDb.insert(nameT, null, CV);
    }

    public Cursor fetchCustomTable(String name) {
        return mDb.query(name,new String[]{"*"},null,null,null,null,null);

    }

    public String getame() {
      return  mDbHelper.getDatabaseName()+" // " +mDb.getPath();
    }

    public Cursor qury(String s) {
        return mDb.rawQuery(s,null);
    }

//    public void creatTa() {
//        mDb.execSQL(TAJERS_TABLE_CREATE);
//    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL( TABLES_TABLE_CREATE);
           db.execSQL(CUSTOMERS_TABLE_CREATE);
//            db.execSQL(DEVICESCUSTOMERS_TABLE_CREATE);
           db.execSQL(MONY_OUT_TABLE_CREATE);
//            db.execSQL(MONYIN_TABLE_CREATE);
//            db.execSQL(DELIVEREDDEVICES_TABLE_CREATE);
          db.execSQL(PIECES_TABLE_CREATE);
          db.execSQL(TAJERS_TABLE_CREATE);

        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
// Not used, but you could upgrade the database with ALTER
// Scripts
        }
    }


    public long createTimeServ(String[] ss , int id_custom, int type ){
        //ss=new String[10];
        long idC;
        if (id_custom==-1) {
            ContentValues initialValues = new ContentValues();
            //  initialValues.put(NAME_CUSTOM, ss[0]);//
            initialValues.put(NAME_CUSTOM, ss[1]);//name
            initialValues.put(ID_CUSTOM, 1);
            initialValues.put(PHONE_CUSTOM, ss[6]);//contPhone
            initialValues.put(NOTE_CUSTOM, "no");
            initialValues.put(NOTE2_CUSTOM, "no2");
            Toast.makeText(mCtx, "" + ss[1], Toast.LENGTH_SHORT).show();
            idC = mDb.insert(CUSTOMERS_TABLE, null, initialValues);
            Toast.makeText(mCtx, "زبون جديد", Toast.LENGTH_SHORT).show();

        }else { idC = (long)id_custom; }

        if (type==1){
            ContentValues initialValuesD = new ContentValues();
            initialValuesD.put(ID_CUSTOM, (int)idC);
            initialValuesD.put(COST_ALL, ss[5]);
            initialValuesD.put(COST_LOST, ss[10]);
            initialValuesD.put(STATUS, ss[8]);
            initialValuesD.put(DATE_TIME_DEVICES, ss[7]);
            initialValuesD.put(NOTE_DELIVERED, ss[6]);
             mDb.insert(DELIVEREDDEVICES_TABLE, null, initialValuesD);
        }
       // mDb.execSQL("");

        ContentValues initialValues2 = new ContentValues();
        initialValues2.put(ID_CUSTOM, (int)idC);
        initialValues2.put(MODEL_CUSTOM, ss[3]);
        initialValues2.put(MANUFACTURE_CUSTOM, ss[2]);
        initialValues2.put(PROBLEM_CUSTOM, ss[4]);
        initialValues2.put(DELIVER_tIM_CUSTOM, ss[7]);
        initialValues2.put(COST_CUSTOM, ss[5]);
        initialValues2.put(TYP_SERVIC_CUSTOM, "صيانة");
        initialValues2.put(DATE_TIME_in, ss[7]);
        initialValues2.put(NOTE_DEVICE, "no");
        initialValues2.put(NOTE2_DEVICE, "no2");
        long n2=  mDb.insert(DEVICESCUSTOMERS_TABLE, null, initialValues2);

        ContentValues initialValues3 = new ContentValues();
        initialValues3.put(ID_CUSTOM, (int)idC);
        initialValues3.put(COST_MNY_IN, ss[0]);
        initialValues3.put(DATE_TIME_MONY_IN, ss[7]);
        initialValues3.put(NOTE_MONY_IN, "no");
        long n3=  mDb.insert(MONYIN_TABLE, null, initialValues3);
        Toast.makeText(mCtx, ""+n2+" "+n2+" "+n3, Toast.LENGTH_SHORT).show();
      // return mDb.insert(CUSTOMERS_TABLE, null, initialValues);
        return n2;
    }


    public void backupDatabase() throws IOException {

        if (isSDCardWriteable()) {
            // Open your local db as the input stream
            String inFileName = DB_FILEPATH;
            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);

          String   voiceStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            //Toast.makeText(AudioRecordingActivity.this, voiceStoragePath, Toast.LENGTH_SHORT).show();
            File audioVoice = new File(voiceStoragePath + File.separator + "dataBase");
            if (!audioVoice.exists()) {
                audioVoice.mkdir();
            }

            String outFileName = Environment.getExternalStorageDirectory() + "/dataBase/syntaxionary.db";
            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);
            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Close the streams
            output.flush();
            output.close();
            fis.close();
        }
    }
    private boolean isSDCardWriteable() {
        boolean rc = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            rc = true;
        }
        return rc;
    }
    public void dropTable(String nameT) {
   mDb.execSQL(DROP_TABLE_IF_EXISTS + nameT);
    }
}
