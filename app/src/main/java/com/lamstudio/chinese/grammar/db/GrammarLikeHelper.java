package com.lamstudio.chinese.grammar.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lamstudio.chinese.grammar.object.GrammarObj;
import com.lamstudio.chinese.grammar.util.Constant;

import java.util.HashMap;

/**
 * Created by AdministratorPC on 12/30/2015.
 */
public class GrammarLikeHelper extends SQLiteOpenHelper {


    public static final String TABLE_NAME = "tbl_like";
    public static final String CREATE_TABLE_NAME = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            " (" + Constant._ID + " INTEGER PRIMARY KEY, " +
            Constant.TITLE + " TEXT, " +
            Constant.CONTENTS + " TEXT, " +
            Constant.LEVEL + "INTEGER" + ")";

    public GrammarLikeHelper(Context context) {
        super(context, GrammarDBHelper.DATABASE_NAME, null, GrammarDBHelper.DATABASE_VERSION);
    }

    public long insertNewLikeObj(GrammarObj obj) {
        ContentValues values = new ContentValues();
        values.put(Constant._ID, obj.getG_id());
        values.put(Constant.TITLE, obj.getTitle());
        values.put(Constant.CONTENTS, obj.getDetail());
        values.put(Constant.LEVEL, obj.getLevel());
        //db.insert("CashData", null, insertValues);
        long id = getWritableDatabase().insertOrThrow(GrammarLikeHelper.TABLE_NAME, null, values);
        Log.d("hieubx", "id="+id);

        return id;
//        return getWritableDatabase().insert(GrammarLikeHelper.TABLE_NAME, null, values);
    }

    public HashMap<Integer, GrammarObj> selectAllGrammarObj() {
        HashMap<Integer, GrammarObj> result = new HashMap<>();
        String sql = "SELECT * FROM " + GrammarLikeHelper.TABLE_NAME + " ORDER BY level ASC ";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int column1 = cursor.getInt(cursor.getColumnIndex(Constant._ID));
                String column2 = cursor.getString(cursor.getColumnIndex(Constant.TITLE));
                String column3 = cursor.getString(cursor.getColumnIndex(Constant.CONTENTS));
                int column4 = cursor.getInt(cursor.getColumnIndex(Constant.LEVEL));
                result.put(column1, new GrammarObj(column1, column2, column3, column4));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
