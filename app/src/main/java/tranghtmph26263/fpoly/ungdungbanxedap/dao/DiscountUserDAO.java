package tranghtmph26263.fpoly.ungdungbanxedap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import tranghtmph26263.fpoly.ungdungbanxedap.database.MyDbHelper;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.DiscountUser;

public class DiscountUserDAO {
    SQLiteDatabase db;
    MyDbHelper dbHelper;

    public DiscountUserDAO(Context context){
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertNew(DiscountUser obj){
        ContentValues contentValues = new ContentValues();
        contentValues.put("discount_id", obj.getDiscount_id());
        contentValues.put("user_id", obj.getUser_id());
        contentValues.put("status", obj.getStatus());

        long res = db.insert("discount_user", null, contentValues);
        return  res;
    }


}
