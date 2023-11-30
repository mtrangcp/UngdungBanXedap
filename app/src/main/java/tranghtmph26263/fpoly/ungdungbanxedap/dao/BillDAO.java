package tranghtmph26263.fpoly.ungdungbanxedap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import tranghtmph26263.fpoly.ungdungbanxedap.database.MyDbHelper;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Bill;

public class BillDAO {
    SQLiteDatabase db;
    MyDbHelper dbHelper;

    public BillDAO(Context context) {
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertNew(Bill obj){
        ContentValues contentValues = new ContentValues();
        contentValues.put("discount_id", obj.getDiscount_id());
        contentValues.put("user_id",obj.getUser_id() );
        contentValues.put("address", obj.getAddress());
        contentValues.put("user_fullname",obj.getUser_fullname() );
        contentValues.put("created_date", obj.getCreated_date());
        contentValues.put("phone",obj.getPhone() );
        contentValues.put("temp_price", obj.getTemp_price());
        contentValues.put("real_price",obj.getReal_price() );
        contentValues.put("status", obj.getStatus());
        contentValues.put("detail",obj.getDetail() );

        long res = db.insert("bill", null, contentValues);
        return  res;
    }




}
