package tranghtmph26263.fpoly.ungdungbanxedap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.database.MyDbHelper;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Category;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.User;

public class UserDAO {
    SQLiteDatabase db;
    MyDbHelper dbHelper;

    public UserDAO(Context context){
        dbHelper = new MyDbHelper(context);
    }

    public void open(){
        db = dbHelper.getWritableDatabase();
    }

    public long insertNew(User obj){
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname", obj.getFullname());
        contentValues.put("username", obj.getUsername());
        contentValues.put("password", obj.getPassword());
        contentValues.put("phone", obj.getPhone());
        contentValues.put("active", obj.getActive());

        long res = db.insert("user", null, contentValues);
        return  res;
    }

    public int deleteRow(User obj){
        int res = db.delete("user", "id = ?" , new String[] { obj.getId() +"" });
        return  res;
    }

    public int updateRow(User obj){
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname", obj.getFullname());
        contentValues.put("username", obj.getUsername());
        contentValues.put("password", obj.getPassword());
        contentValues.put("phone", obj.getPhone());
        contentValues.put("active", obj.getActive());

        int res = db.update("user", contentValues,"id = ?", new String[] {obj.getId() +"" } );
        return  res;
    }

    public User selectOne(int ID){
        db = dbHelper.getReadableDatabase();
        User obj = null;

        Cursor cursor = db.rawQuery("select * from user where id = ?",new String[] { ID + "" });
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            String username = cursor.getString(1);
            String password = cursor.getString(2);
            String fullname = cursor.getString(3);
            String phone = cursor.getString(4);
            int active= cursor.getInt(5);

            obj = new User(id, username, password, fullname, phone, active);
        }

        return obj;
    }

    public ArrayList<User> selectAll(){
        ArrayList<User> listUser = new ArrayList<User>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user", null);

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int id = cursor.getInt(0);
                String username = cursor.getString(1);
                String password = cursor.getString(2);
                String fullname = cursor.getString(3);
                String phone = cursor.getString(4);
                int active= cursor.getInt(5);

                listUser.add(new User(id, username, password, fullname, phone, active));
                cursor.moveToNext();
            }
        }
        return  listUser;
    }

    public boolean CheckLogin( String username, String password){
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where username = ? and password = ? ", new String[]{username, password} );
        if (cursor.getCount() >0  ){
            cursor.moveToFirst();
            int activeIndex = cursor.getColumnIndex("active");
            int active = cursor.getInt(activeIndex);
            if ( active == 1 ){
                cursor.close();
                return true;
            }
        }
        return false;
    }

    public int updateActive(User obj ){
        int res = db.delete("user", "id = ?" , new String[] { obj.getId() +"" });
        return  res;
    }
}
