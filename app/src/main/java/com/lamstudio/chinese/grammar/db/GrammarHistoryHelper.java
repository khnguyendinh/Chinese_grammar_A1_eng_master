package com.lamstudio.chinese.grammar.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lamstudio.chinese.grammar.object.GrammarObj;
import com.lamstudio.chinese.grammar.util.Constant;


/**
 * Created by AdministratorPC on 12/30/2015.
 */
public class GrammarHistoryHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "btl_history";
    public static final String CREATE_TABLE_NAME = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            " (" + Constant._ID + " INTEGER PRIMARY KEY, " +
            Constant.TITLE + " TEXT, " +
            Constant.CONTENTS + " TEXT, " +
            Constant.LEVEL + "INTEGER" + ")";

    public GrammarHistoryHelper(Context context) {
        super(context, GrammarDBHelper.DATABASE_NAME, null, GrammarDBHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_NAME);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertNewHistoryObj(GrammarObj obj) {
        ContentValues values = new ContentValues();
        values.put(Constant._ID, obj.getG_id());
        values.put(Constant.TITLE, obj.getTitle());
        values.put(Constant.CONTENTS, obj.getDetail());
        values.put(Constant.LEVEL, obj.getLevel());
        return getWritableDatabase().insert(TABLE_NAME, null, values);
    }
}
