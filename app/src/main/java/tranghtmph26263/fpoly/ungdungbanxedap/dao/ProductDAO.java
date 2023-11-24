package tranghtmph26263.fpoly.ungdungbanxedap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.admin.ProductActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.database.MyDbHelper;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Category;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Product;

public class ProductDAO {
    SQLiteDatabase db;
    MyDbHelper dbHelper;
    String sql = "SELECT name FROM category INNER JOIN product ON category.id = product.category_id WHERE product.id = ?";


    public ProductDAO(Context context){
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public int softDelete(Product obj){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("stock", 0);
        int res = db.update("product", values,"id = ?", new String[] {obj.getId() +"" } );
        return res;
    }

    public long insert(Product obj){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", obj.getName());
        contentValues.put("image", obj.getAvatar());
        contentValues.put("price", obj.getPrice());
        contentValues.put("describe", obj.getDescribe());
        contentValues.put("stock", obj.getStock());
        contentValues.put("import_date", obj.getImport_date());
        contentValues.put("sold", obj.getSold());
        contentValues.put("category_id", obj.getCategory_id());

        Log.d("zzzzzz", "insert: put thanh cong");

        long res = db.insert("product", null, contentValues);
        return  res;
    }

    public int updateRow( Product obj, String name){
        Log.d("cccccc", "trong luc updateRow: "+obj.toString());
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", obj.getName());
        contentValues.put("image", obj.getAvatar());
        contentValues.put("price", obj.getPrice());
        contentValues.put("describe", obj.getDescribe());
        contentValues.put("stock", obj.getStock());
        contentValues.put("import_date", obj.getImport_date());
        contentValues.put("sold", obj.getSold());
        contentValues.put("category_id", obj.getCategory_id());

        int res = db.update("product", contentValues, "name = ?", new String[]{name});
        return res;
    }

    public ArrayList<Product> selectAll(){
        ArrayList<Product> listProduct = new ArrayList<Product>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from product", null);

        if ( cursor.moveToFirst()){
            while ( !cursor.isAfterLast()){
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                byte[] img = cursor.getBlob(2);
                int price = cursor.getInt(3);
                String describe = cursor.getString(4);
                int stock = cursor.getInt(5);
                String date = cursor.getString(6);
                int sold = cursor.getInt(7);
                int category_id = cursor.getInt(8);

                listProduct.add(new Product(id, category_id, name, describe, date,img, price, stock, sold));
                cursor.moveToNext();
            }
        }

        cursor.close();
        return listProduct;
    }

    public ArrayList<Product> selectAllForUser(){
        ArrayList<Product> listProduct = new ArrayList<Product>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from product where stock > 0", null);

        if ( cursor.moveToFirst()){
            while ( !cursor.isAfterLast()){
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                byte[] img = cursor.getBlob(2);
                int price = cursor.getInt(3);
                String describe = cursor.getString(4);
                int stock = cursor.getInt(5);
                String date = cursor.getString(6);
                int sold = cursor.getInt(7);
                int category_id = cursor.getInt(8);

                listProduct.add(new Product(id, category_id, name, describe, date,img, price, stock, sold));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listProduct;
    }

    public ArrayList<Product> selectAllWithCategoryId(int categoryId){
        ArrayList<Product> listProduct = new ArrayList<Product>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from product where category_id = ?", new String[]{categoryId+""});

        if ( cursor.moveToFirst()){
            while ( !cursor.isAfterLast()){
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                byte[] img = cursor.getBlob(2);
                int price = cursor.getInt(3);
                String describe = cursor.getString(4);
                int stock = cursor.getInt(5);
                String date = cursor.getString(6);
                int sold = cursor.getInt(7);
                int category_id = cursor.getInt(8);

                listProduct.add(new Product(id, category_id, name, describe, date,img, price, stock, sold));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listProduct;
    }

    public ArrayList<Product> selectAllNewProduct(String today){
        ArrayList<Product> listProduct = new ArrayList<Product>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from product where stock > 0 and import_date = ?", new String[]{today});

        if ( cursor.moveToFirst()){
            while ( !cursor.isAfterLast()){
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                byte[] img = cursor.getBlob(2);
                int price = cursor.getInt(3);
                String describe = cursor.getString(4);
                int stock = cursor.getInt(5);
                String date = cursor.getString(6);
                int sold = cursor.getInt(7);
                int category_id = cursor.getInt(8);

                listProduct.add(new Product(id, category_id, name, describe, date,img, price, stock, sold));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listProduct;
    }

}
