package com.latihan.myaddressbook_2301853962.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.latihan.myaddressbook_2301853962.models.AddressBookEmployee;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "AddressEmployee", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table employee(" +
                "id INTEGER primary key," +
                "name TEXT," +
                "city TEXT," +
                "country TEXT," +
                "phone TEXT," +
                "email TEXT," +
                "image TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists employee");
    }

    public boolean insertData(AddressBookEmployee employee){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id",employee.getEmployeeID());
        contentValues.put("name",employee.getName());
        contentValues.put("city",employee.getCity());
        contentValues.put("country",employee.getCountry());
        contentValues.put("phone",employee.getPhone());
        contentValues.put("email",employee.getEmail());
        contentValues.put("image",employee.getUrlImg());
        long result = db.insert("employee",null, contentValues);
        if (result == -1) return false;
        return true;
    }

    public Cursor getAddressBookEmployees(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select * from employee",null);
    }
}
