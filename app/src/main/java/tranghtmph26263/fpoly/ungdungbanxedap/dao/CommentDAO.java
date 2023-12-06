package tranghtmph26263.fpoly.ungdungbanxedap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.database.MyDbHelper;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Comment;

public class CommentDAO {
    SQLiteDatabase db;
    MyDbHelper dbHelper;

    public CommentDAO(Context context) {
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertNew(Comment obj){
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", obj.getUser_id());
        contentValues.put("product_id", obj.getProduct_id());
        contentValues.put("content", obj.getContent());
        contentValues.put("time", obj.getTime());

        long res = db.insert("comment",null, contentValues);
        return res;
    }

    public int updateRow( Comment obj){
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", obj.getUser_id());
        contentValues.put("product_id", obj.getProduct_id());
        contentValues.put("content", obj.getContent());
        contentValues.put("time", obj.getTime());

        int res = db.update("comment", contentValues, "id = ?", new String[]{obj.getId()+""});
        return res;
    }

    public int deleteRow(Comment obj){
        int res = db.delete("comment","id = ?", new String[]{obj.getId()+""});
        return res;
    }

    public ArrayList<Comment> selectAll(){
        ArrayList<Comment> listComment = new ArrayList<>();
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from comment",null);

        if ( cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int id = cursor.getInt(0);
                int user_id = cursor.getInt(1);
                int discount_id = cursor.getInt(2);
                String content = cursor.getString(3);
                String time = cursor.getString(4);

                listComment.add(new Comment(id, user_id, discount_id, content, time));
                cursor.moveToNext();
            }
        }

        return listComment;
    }



}
