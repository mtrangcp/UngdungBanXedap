package tranghtmph26263.fpoly.ungdungbanxedap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Blob;
import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.database.MyDbHelper;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Category;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Product;

public class ProductDao {
    SQLiteDatabase db;
    MyDbHelper dbHelper;
    public ProductDao(Context context){
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertNew(Product obj){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", obj.getName());
        contentValues.put("image", obj.getAvatar().toString());
        contentValues.put("price",obj.getPrice());
        contentValues.put("describe",obj.getDescribe());
        contentValues.put("stock",obj.getStock());
        contentValues.put("import_date",obj.getImport_date());
        contentValues.put("sold",obj.getSold());
        contentValues.put("category_id",obj.getCategory_id());

        long res = db.insert("product", null, contentValues);

        return  res;
    }

    public int deleteRow(Product obj){
        int res = db.delete("product", "id = ?" , new String[] { obj.getId() +"" });
        return  res;
    }

    public int updateRow(Product obj){
        ContentValues contentValues= new ContentValues();
        contentValues.put( "name",obj.getName());
        contentValues.put("image", obj.getAvatar().toString());
        contentValues.put("price",obj.getPrice());
        contentValues.put("describe",obj.getDescribe());
        contentValues.put("stock",obj.getStock());
        contentValues.put("import_date",obj.getImport_date());
        contentValues.put("sold",obj.getSold());
        contentValues.put("category_id",obj.getCategory_id());

        int res = db.update("product", contentValues,"id = ?", new String[] {obj.getId() +"" } );
        return  res;
    }

    public ArrayList<Product> selectAll(){
        ArrayList<Product> listProduct = new ArrayList<Product>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from product", null);

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int id = cursor.getInt(0);
                String name= cursor.getString(1);
                byte[] img = cursor.getBlob(2);
                int price = cursor.getInt(3);
                String describe = cursor.getString(4);
                int stock = cursor.getInt(5);
                String import_date = cursor.getString(6);
                int sold = cursor.getInt(7);
                int category_id = cursor.getInt(8);

//                listProduct.add(new Product(id, category_id,name,describe,import_date,img,price,stock,sold));
                cursor.moveToNext();
            }
        }
        return  listProduct;
    }}
