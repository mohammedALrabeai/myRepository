package com.example.mohammedalrabeai.ali2;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

//import com.treslines.syntaxionary.entity.LanguageEntity;


/**
 * Use this class to manipulate the DB created over DbFactory.
 * <b>Usage example: </b>Db.open(...).insert(...);
 * After execution the Db will always be closed automatically.
 * @author Ricardo Ferreira
 * @version 1.0
 * @since 17/07/2014
 */
public class DAO {

    private static final String TAG = "TAG";
    private static final long INSERT_ERROR = -1;
    private static final int UPDATE_ERROR = 0;
    private static final String DB_QUERY_SIGN = "= ?";
    private static DAO instance;

    private DbFactory dbFactory;
    private SQLiteDatabase db;//NOPMD

    // singleton
    private DAO(Context context) {
        super();
        this.dbFactory = new DbFactory(context);
        this.db = this.dbFactory.getWritableDatabase();
    }

    /** always start a new db transaction over DAO.open(...) */
    public synchronized static DAO open(Context context) {
        if (instance == null) {
            instance = new DAO(context);
        }
        return instance;
    }

    public long insertLanguage(LanguageEntity language) {
        final String columnName = DbFactory.LANGUAGE_NAME;
        ContentValues values = new ContentValues();
        values.put(columnName, language.getLanguage());
        long newRowId = db.insert(DbFactory.LANGUAGE, null, values);
        close();
        return newRowId;
    }

    public int getLanguageCounter() {
        final String tableName = DbFactory.LANGUAGE;
        final Cursor result = db.query(tableName, null, null,null,null,null,null);
        int counter = result.getCount();
        close();
        return counter;
    }

    public boolean hasLanguage(String newLanguage) {
        final String tableName = DbFactory.LANGUAGE;
        String where = DbFactory.LANGUAGE_NAME+DB_QUERY_SIGN;
        String [ ] languages = new String [ ] {newLanguage};
        final Cursor result = db.query(tableName, null, where,languages,null,null,null);
        boolean canInsert = result.moveToNext();
        close();
        return canInsert;
    }

    public int queryLanguageIdByName(String language) {
        final String tableName = DbFactory.LANGUAGE;
        String where = DbFactory.LANGUAGE_NAME+ DB_QUERY_SIGN;
        Cursor result = db.query(tableName, null,where,new String [ ] {language},null,null,null,null);
        while(result.moveToNext()){
            int columnIndex = result.getColumnIndex(DbFactory.LANGUAGE_ID);
            return result.getInt(columnIndex);
        }
        return 0;
    }

    public boolean insertLanguage(String newLanguage) {
        final String tableName = DbFactory.LANGUAGE;
        ContentValues tableValues = new ContentValues();
        tableValues.put(DbFactory.LANGUAGE_NAME, newLanguage);
        long newRowId = db.insert(tableName, null, tableValues);
        close();
        return newRowId != INSERT_ERROR;
    }

    public List < String > queryLanguages() {
        Cursor result = db.query(DbFactory.LANGUAGE, new String [ ]{DbFactory.LANGUAGE_NAME}, null, null, null, null, null);
        List< String > languages = new ArrayList< String >();
        while(result.moveToNext()){
            int nameIndex = result.getColumnIndex(DbFactory.LANGUAGE_NAME);
            languages.add(result.getString(nameIndex));
        }
        return languages;
    }

    public int deleteLanguage(String language) {
        final String tableName = DbFactory.LANGUAGE;
        String where = DbFactory.LANGUAGE_NAME + DB_QUERY_SIGN;
        String [ ] languages = new String [ ] { language };
        int result = db.delete(tableName,  where, languages);
        close();
        return result;
    }

    /** Use Db.open(...) to open a new connection */
    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
            db = null;
        }
        if (dbFactory != null) {
            dbFactory.close();
            dbFactory = null;
        }
        instance =null;
    }

}