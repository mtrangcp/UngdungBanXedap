package tranghtmph26263.fpoly.ungdungbanxedap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.database.MyDbHelper;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Bill;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.CartDetail;

public class BillDAO {
    SQLiteDatabase db;
    MyDbHelper dbHelper;

    public BillDAO(Context context) {
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public int updateStatusChapNhan(Bill obj){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", 1);
        int res = db.update("bill", values,"id = ?", new String[] {obj.getId() +"" } );
        return res;
    }

    public int updateStatusTuChoi(Bill obj){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", 2);
        int res = db.update("bill", values,"id = ?", new String[] {obj.getId() +"" } );
        return res;
    }
    public int updateStatusDangGiao(Bill obj){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", 3);
        int res = db.update("bill", values,"id = ?", new String[] {obj.getId() +"" } );
        return res;
    }
    public int updateStatusDaGiao(Bill obj){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", 4);
        int res = db.update("bill", values,"id = ?", new String[] {obj.getId() +"" } );
        return res;
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

    public ArrayList<Bill> selectAll(){
        ArrayList<Bill> arrayList = new ArrayList<Bill>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from bill", null);

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int id = cursor.getInt(0);
                int discount_id= cursor.getInt(1);
                int user_id = cursor.getInt(2);
                String address = cursor.getString(3);
                String user_fullname = cursor.getString(4);
                String created_date = cursor.getString(5);
                String phone= cursor.getString(6);
                int temp_price = cursor.getInt(7);
                int real_price = cursor.getInt(8);
                int status = cursor.getInt(9);
                String detail = cursor.getString(10);

                arrayList.add(new Bill(id,discount_id,user_id, temp_price, real_price, status,address, user_fullname, created_date, phone, detail));
                cursor.moveToNext();
            }
        }
        return arrayList;
    }

    public ArrayList<Bill> selectAllForUser(int ID){
        ArrayList<Bill> arrayList = new ArrayList<Bill>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from bill where user_id = ?", new String[]{ID+""});

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int id = cursor.getInt(0);
                int discount_id= cursor.getInt(1);
                int user_id = cursor.getInt(2);
                String address = cursor.getString(3);
                String user_fullname = cursor.getString(4);
                String created_date = cursor.getString(5);
                String phone= cursor.getString(6);
                int temp_price = cursor.getInt(7);
                int real_price = cursor.getInt(8);
                int status = cursor.getInt(9);
                String detail = cursor.getString(10);

                arrayList.add(new Bill(id,discount_id,user_id, temp_price, real_price, status,address, user_fullname, created_date, phone, detail));
                cursor.moveToNext();
            }
        }
        return arrayList;
    }

    public int getDoanhThu(String tuNgay, String denNgay) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Integer> list = new ArrayList<>();
        try {
            Cursor cursor =db.rawQuery("SELECT SUM(real_price) FROM bill WHERE created_date BETWEEN ? AND ?", new String[] {tuNgay, denNgay});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    list.add(cursor.getInt(0));
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i("aaaa", "Lỗi" + e);
        }
        return list.get(0);
    }

    public ArrayList<Bill> selectBillHuy(){
        ArrayList<Bill> arrayList = new ArrayList<Bill>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from bill where status = 2", null);

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int id = cursor.getInt(0);
                int discount_id= cursor.getInt(1);
                int user_id = cursor.getInt(2);
                String address = cursor.getString(3);
                String user_fullname = cursor.getString(4);
                String created_date = cursor.getString(5);
                String phone= cursor.getString(6);
                int temp_price = cursor.getInt(7);
                int real_price = cursor.getInt(8);
                int status = cursor.getInt(9);
                String detail = cursor.getString(10);

                arrayList.add(new Bill(id,discount_id,user_id, temp_price, real_price, status,address, user_fullname, created_date, phone, detail));
                cursor.moveToNext();
            }
        }
        return arrayList;
    }
}
