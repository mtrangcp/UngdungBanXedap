package tranghtmph26263.fpoly.ungdungbanxedap.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "shop_ban_xe_dap";
    static final int DB_VERSION = 1;

    public MyDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table category( id integer not null primary key autoincrement, name text not null)";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into category(name) values('Xe đạp địa hình'), ('Xe đạp đường phố'), ('Xe đạp Nhật')," +
                "('Xe đạp trẻ em'), ('Xe đạp cào cào'), ('Xe đạp đua'), ('Xe đạp gấp'), ('Xe đạp không phanh')";
        sqLiteDatabase.execSQL(sql);


        sql = "create table product(id integer not null primary key autoincrement, name text not null unique, image blob not null ," +
                " price integer not null, describe text, stock integer, import_date text, sold integer, category_id integer not null )";
        sqLiteDatabase.execSQL(sql);


        sql = "create table user(id integer not null primary key autoincrement, username text not null unique, password text ," +
                " fullname text, phone text unique , active integer )";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into user(fullname, username, password, phone, active) values('Ha Minh Trang', 'mtrang', '111', '0363889233', 1), " +
                "('Nguyen Thu Hang', 'thang', '112', '0363887665', 0), ('Tran Khanh Linh', 'klinh', '113', '0364556237', 1), " +
                "('Le Nguyet Hang', 'nhang', '114', '0363776456', 1), ('Le Thi Hoa', 'thoa', '115', '0345446787', 1)";
        sqLiteDatabase.execSQL(sql);


        sql = "create table discount(id integer not null primary key autoincrement, code_name text not null unique, value integer ," +
                " start_date text, expiration_date text , detail text )";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into discount(code_name, start_date, expiration_date, detail, value) values" +
                "('VMCS20', '1/10/2023', '1/12/2023', 'Giảm 20k tổng giá trị đơn hàng', 20000), " +
                "('VMCS30', '1/11/2023', '1/12/2023', 'Giảm 30k tổng giá trị đơn hàng', 30000), " +
                "('VMCS40', '10/11/2023', '1/12/2023', 'Giảm 40k tổng giá trị đơn hàng', 40000), " +
                "('VMCS50', '13/10/2023', '15/12/2023', 'Giảm 50k tổng giá trị đơn hàng', 50000), " +
                "('VMCS60', '8/11/2023', '11/12/2023', 'Giảm 60k tổng giá trị đơn hàng', 60000), " +
                "('VMCS90', '11/11/2023', '11/12/2023', 'Giảm 90k tổng giá trị đơn hàng', 90000)";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
