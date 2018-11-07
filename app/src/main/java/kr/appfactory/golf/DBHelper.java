package kr.appfactory.golf;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {


    private static final String Database = "JappfactoryGolf.db";
    private static final String Table = "JappfactoryGolf.db";

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context) {
        super(context, Database, null, 1);

        SQLiteDatabase db=this.getWritableDatabase();
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 MONEYBOOK이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE "+ Table + " (idx INTEGER PRIMARY KEY AUTOINCREMENT, videoId TEXT, title TEXT, videodesc TEXT, publishedAt TEXT,  thum_pic TEXT);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+Table +"");
        onCreate(db);

    }

    public void insert(String videoId, String title, String description, String publishedAt, String thum_pic) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO "+ Table + " VALUES(null, '" + videoId + "' , '" + title + "' , '" + description + "' , '" + publishedAt + "' , '" + thum_pic + "');");
        db.close();
    }

    public void update(String videoId, String title) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE "+ Table + "  SET title=" + title + " WHERE videoId='" + videoId + "';");
        db.close();
    }

    public void delete(String videoId) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM  "+ Table + " WHERE videoId='" + videoId + "';");
        db.close();
    }

    public String getCnt(String videoId) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Table + " where videoId=" + videoId + "", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0)
                    + " : "
                    + cursor.getString(1)
                    + " | "
                    + cursor.getString(2)
                    + "원 "
                    + cursor.getString(3)
                    + "\n";
        }

        return result;
    }
    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Table + " ", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0)
                    + " : "
                    + cursor.getString(1)
                    + " | "
                    + cursor.getString(2)
                    + "원 "
                    + cursor.getString(3)
                    + "\n";
        }

        return result;
    }
}