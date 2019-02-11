package com.example.mohammedalrabeai.ali2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;


/**
 * Class responsible for defining and creating, updating or deleting DB, tables and its columns.
 *
 * @author Ricardo Ferreira
 * @version 1.0
 * @since 17/07/2014
 */
public class DbFactory extends SQLiteOpenHelper {

    // Database & Name Version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "yourdatabasename.db";

    // your table name. In this example language
    public static final String LANGUAGE = "language";
    public static final String LANGUAGE_ID = "language_id";
    public static final String LANGUAGE_NAME = "language_name";

    // used SQL statements
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

    private final String DB_FILEPATH;

    public DbFactory(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        final String packageName = context.getPackageName();
        DB_FILEPATH = "/data/data/" + packageName + "/databases/yourdatabasename.db";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createLanguageTable(db);
        populateLanguageOnce(db);
    }

    private void populateLanguageOnce(SQLiteDatabase db) {
        String [ ] languages = new String [ ] { "C", "C++", "C#", "Java" };
        for (String language : languages) {
            insertLanguages(db, language);
        }
    }

    private void insertLanguages(SQLiteDatabase db, String value) {
        ContentValues columnValuePair = new ContentValues();
        columnValuePair.put(LANGUAGE_NAME, value);
        db.insert(LANGUAGE, null, columnValuePair);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        recreateTables(db);
    }

    private void dropTables(SQLiteDatabase db) {
        db.execSQL(DROP_TABLE_IF_EXISTS + LANGUAGE);
    }

    private void recreateTables(SQLiteDatabase db) {
        onCreate(db);
    }

    private void createLanguageTable(SQLiteDatabase db) {
        final StringBuilder sql = new StringBuilder();
        sql.append(CREATE_TABLE_IF_NOT_EXISTS);
        sql.append(LANGUAGE);
        sql.append(PARENTHSE_LEFT);
        sql.append(LANGUAGE_ID);
        sql.append(INTEGER_PRIMARY_KEY);
        sql.append(LANGUAGE_NAME);
        sql.append(TEXTC);
        sql.append(UNIQUE); // ensures uniqueness for languages
        sql.append(PARENTHSE_LEFT);
        sql.append(LANGUAGE_NAME);
        sql.append(PARENTHSE_RIGHT);
        sql.append(PARENTHSE_RIGHT);
        Log.d("CREATE_TABLE_LANGUAGE", sql.toString());
        db.execSQL(sql.toString());
    }

    /**
     * Copies the database file at the specified location
     * over the current internal application database.
     * */
    public boolean importDatabase(String dbPath) throws IOException {

        // Close the SQLiteOpenHelper so it will
        // commit the created empty database to internal storage.
        close();
        File newDb = new File(dbPath);
        File oldDb = new File(DB_FILEPATH);
        if (newDb.exists()) {
            copyFile(new FileInputStream(newDb), new FileOutputStream(oldDb));
            // Access the copied database so SQLiteHelper
            // will cache it and mark it as created.
            getWritableDatabase().close();
            return true;
        }
        return false;
    }

    private void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }

    public void backupDatabase() throws IOException {

        if (isSDCardWriteable()) {
            // Open your local db as the input stream
            String inFileName = DB_FILEPATH;
            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);

            String outFileName = Environment.getExternalStorageDirectory() + "/syntaxionary";
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
}

