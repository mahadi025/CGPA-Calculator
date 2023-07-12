package com.example.cgpacalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.List;

import kotlin.collections.ArrayDeque;

public class DataBase extends SQLiteOpenHelper {


    private final static String TABLE_NAME="GRADE_SHEET";
    private final static String GRADE_NAME_COLUMN="GRADE_NAME";
    private final static String GRADE_POINT_COLUMN="GRADE_POINT";
    public DataBase(@Nullable Context context) {
        super(context, "GradeSheet.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CreateTableStatement="CREATE TABLE "+TABLE_NAME+" ( "+GRADE_NAME_COLUMN +" TEXT PRIMARY KEY, "+GRADE_POINT_COLUMN+" REAL)";
        sqLiteDatabase.execSQL(CreateTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean addOne(Grade grade){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(GRADE_NAME_COLUMN, grade.getGradeName());
        cv.put(GRADE_POINT_COLUMN, grade.getGradePoint());
        long insert = db.insert(TABLE_NAME, null, cv);
        if(insert==-1)
            return false;
        else
            return true;
    }
    public boolean deleteOne(Grade grade){
        SQLiteDatabase db=this.getWritableDatabase();
        String queryString="DELETE FROM "+TABLE_NAME+" WHERE "+GRADE_NAME_COLUMN+" = \""+grade.getGradeName().toString()+"\"";
        System.out.println(queryString);
        Cursor cursor=db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }
    public List<Grade>getALL(){
        List<Grade> returnList=new ArrayDeque<>();
        String queryString="SELECT "+ GRADE_NAME_COLUMN+", "+GRADE_POINT_COLUMN +" FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String gradeName=cursor.getString(0);
                double gradePoint=cursor.getDouble(1);
                Grade grade=new Grade(gradeName, gradePoint);
                returnList.add(grade);
            }while(cursor.moveToNext());
        }
        else{

        }

        return returnList;
    }
}
