package tranghtmph26263.fpoly.ungdungbanxedap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.database.MyDbHelper;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.CartDetail;

public class CartDAO {
    SQLiteDatabase db;
    MyDbHelper dbHelper;

    public CartDAO(Context context){
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertNew(CartDetail obj){
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", obj.getUser_id());
        contentValues.put("product_id",obj.getProduct_id() );
        contentValues.put("price", obj.getPrice());
        contentValues.put("quantity",obj.getQuantity() );

        long res = db.insert("cart_detail", null, contentValues);
        return  res;
    }

    public int deleteRow( CartDetail obj){
        int res = db.delete("cart_detail", "id = ?" , new String[]{ obj.getId() +"" });
        return  res;
    }

    public int updateRow(CartDetail obj){
        ContentValues contentValues= new ContentValues();
        contentValues.put("user_id", obj.getUser_id());
        contentValues.put("product_id",obj.getProduct_id() );
        contentValues.put("price", obj.getPrice());
        contentValues.put("quantity",obj.getQuantity() );

        int res = db.update("cart_detail", contentValues,"id = ?", new String[] {obj.getId() +"" } );
        return  res;
    }

    public ArrayList<CartDetail> selectAll(){
        ArrayList<CartDetail> listCart = new ArrayList<CartDetail>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from cart_detail", null);

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int id = cursor.getInt(0);
                int user_id= cursor.getInt(1);
                int pro_id = cursor.getInt(2);
                int price = cursor.getInt(3);
                int quantity = cursor.getInt(4);

                listCart.add(new CartDetail(id,user_id,pro_id, price, quantity));
                cursor.moveToNext();
            }
        }
        return  listCart;
    }

    public ArrayList<CartDetail> selectAllForUser(int userId){
        ArrayList<CartDetail> listCart = new ArrayList<CartDetail>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from cart_detail where user_id = ?", new String[]{userId+""});

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int id = cursor.getInt(0);
                int user_id= cursor.getInt(1);
                int pro_id = cursor.getInt(2);
                int price = cursor.getInt(3);
                int quantity = cursor.getInt(4);

                listCart.add(new CartDetail(id,user_id,pro_id, price, quantity));
                cursor.moveToNext();
            }
        }
        return  listCart;
    }

    public CartDetail selectOneWithIdUserAndIdProduct(int user_ID, int product_id){
        db = dbHelper.getReadableDatabase();
        CartDetail obj = null;

        Cursor cursor = db.rawQuery("select * from cart_detail where user_id = ? and product_id = ?",new String[] { user_ID + "", product_id+"" });
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            int user_id= cursor.getInt(1);
            int pro_id = cursor.getInt(2);
            int price = cursor.getInt(3);
            int quantity = cursor.getInt(4);
            obj = new CartDetail(id,user_id,pro_id, price, quantity);
        }
        return obj;
    }

    public CartDetail selectOne(int ID){
        db = dbHelper.getReadableDatabase();
        CartDetail obj = null;

        Cursor cursor = db.rawQuery("select * from cart_detail where id = ?",new String[] { ID + "" });
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            int user_id= cursor.getInt(1);
            int pro_id = cursor.getInt(2);
            int price = cursor.getInt(3);
            int quantity = cursor.getInt(4);
            obj = new CartDetail(id,user_id,pro_id, price, quantity);
        }
        return obj;
    }

    public int deleteRowWithUserId( int ID){
        int res = db.delete("cart_detail", "user_id = ?" , new String[]{ ID +"" });
        return  res;
    }

}
