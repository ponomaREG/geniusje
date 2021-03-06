package com.msulov.geniusje;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.msulov.geniusje.Logging.Logging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "sqlite.db";
    private static String DB_PATH = "";
    private SQLiteDatabase mDataBase;
    private boolean mNeedUpdate = false;
    private Context context;



    public DBHelper(Context context){
        super(context, DB_NAME, null, 1);
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        this.context = context;
    }


    public void updateDataBase(){
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();
            copyDataBase();

            mNeedUpdate = false;
        }
    }
    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }
    void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }
    private void copyDBFile() throws IOException {
        InputStream mInput = context.getAssets().open(DB_NAME);
        //InputStream mInput = mContext.getResources().openRawResource(R.raw.info);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }
    @Deprecated
    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }

    public void setOpenTaskNumberIs(int task, float time){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isLast = false;
        if (task == 31) {
            isLast = true;
            task = 30;
        }
        Cursor c = db.rawQuery(String.format("select * from tasks where id=%s;", task), null);
        c.moveToFirst();
        if (c.getInt(c.getColumnIndex("is_open")) == 0) {
                c.close();
                c = db.rawQuery(String.format("update tasks set is_open=1 where id=%s;", task), null);
                c.moveToFirst();
                c.close();
                c = db.rawQuery(String.format("select * from tasks where id=%s;", task - 1), null);
                c.moveToFirst();
                float time_in_db = c.getFloat(c.getColumnIndex("time"));
                if (time_in_db > time)
                    db.rawQuery(String.format("update tasks update time=%.1f where id=$s;", time, task - 1), null);

        }else{
            if(isLast){
                c.close();
                c = db.rawQuery(String.format("select * from tasks where id=%s;", task), null);
                c.moveToFirst();
                float time_in_db = c.getFloat(c.getColumnIndex("time"));
                if (time_in_db > time)
                    db.rawQuery(String.format("update tasks update time=%.1f where id=$s;", time, task), null);
            }
        }
            c.close();
    }


}