package tranghtmph26263.fpoly.ungdungbanxedap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.database.MyDbHelper;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Category;

public class CategoryDAO {
    SQLiteDatabase db;
    MyDbHelper dbHelper;

    public CategoryDAO(Context context){
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertNew(Category obj){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", obj.getName());
        contentValues.put("active",obj.getActive() );

        long res = db.insert("category", null, contentValues);
        return  res;
    }

    public int deleteSoft(Category obj){
        db = dbHelper.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put( "name",obj.getName()  );
        values.put( "active",0  );
        int res = db.update("category", values,"id = ?", new String[] {obj.getId() +"" } );
        return  res;
    }

    public int deleteRow( Category obj){
        int res = db.delete("category", "id = ?" , new String[]{ obj.getId() +"" });
        return  res;
    }

    public int updateRow(Category obj){
        ContentValues values= new ContentValues();
        values.put( "name",obj.getName()  );

        int res = db.update("category", values,"id = ?", new String[] {obj.getId() +"" } );
        return  res;
    }

    public Category selectOne(int ID){
        db = dbHelper.getReadableDatabase();
        Category obj = null;

        Cursor cursor = db.rawQuery("select * from category where id = ?",new String[] { ID + "" });
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            String name= cursor.getString(1);
            obj = new Category(id,1, name);
        }
        return obj;
    }

    public ArrayList<Category> selectAll(){
        ArrayList<Category> listCategory = new ArrayList<Category>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from category", null);

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int id = cursor.getInt(0);
                String name= cursor.getString(1);

                listCategory.add(new Category(id,1, name));
                cursor.moveToNext();
            }
        }
        return  listCategory;
    }

    public ArrayList<Category> selectAllForUser(){
        ArrayList<Category> listCategory = new ArrayList<Category>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from category where active > 0", null);

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int id = cursor.getInt(0);
                String name= cursor.getString(1);

                listCategory.add(new Category(id,1, name));
                cursor.moveToNext();
            }
        }
        return  listCategory;
    }

}
