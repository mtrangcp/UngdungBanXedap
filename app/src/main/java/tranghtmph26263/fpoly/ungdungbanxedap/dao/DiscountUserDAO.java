package tranghtmph26263.fpoly.ungdungbanxedap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.database.MyDbHelper;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Discount;
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

    public int updateStatus(int ID){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", 1);
        int res = db.update("discount_user", values,"id = ?", new String[] {ID +"" } );
        return res;
    }

    public ArrayList<DiscountUser> selectAll(){
        ArrayList<DiscountUser> listDiscountUser = new ArrayList<DiscountUser>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from discount_user where status >0", null);

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int id = cursor.getInt(0);
                int discount_id= cursor.getInt(1);
                int user_id = cursor.getInt(2);
                int status= cursor.getInt(3);

                listDiscountUser.add(new DiscountUser(id, discount_id, user_id, status));
                cursor.moveToNext();
            }
        }
        return  listDiscountUser;
    }

    public ArrayList<Discount> selectForUser(int ID){
        ArrayList<Discount> listDiscountUser = new ArrayList<Discount>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from discount inner join discount_user on discount.id = discount_user.discount_id  " +
                "where discount_user.status = 0 and discount_user.user_id = ? " , new String[]{ID+""});

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int id = cursor.getInt(0);
                String name= cursor.getString(1);
                int value= cursor.getInt(2);
                String batDau= cursor.getString(3);
                String hetHan= cursor.getString(4);
                String detail= cursor.getString(5);

                listDiscountUser.add(new Discount(id,value, name, detail, batDau, hetHan));
                cursor.moveToNext();
            }
        }
        return  listDiscountUser;
    }

}
