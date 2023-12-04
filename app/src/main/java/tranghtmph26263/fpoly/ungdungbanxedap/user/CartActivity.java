package tranghtmph26263.fpoly.ungdungbanxedap.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.MainActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.CartAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.CartDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.UserDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.CartDetail;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.User;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button btn_muahang;
    ImageView img;
    CartDAO dao;
    UserDAO userDAO;
    CartAdapter adapter;
    ArrayList<CartDetail> arrayList = new ArrayList<CartDetail>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        img = findViewById(R.id.id_home_cart);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, MainActivity.class));
                finish();
            }
        });

        userDAO = new UserDAO(this);
        SharedPreferences preferences = getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String username = preferences.getString("USERNAME", "");
        User objUser = userDAO.selectOneWithUsername(username);

        recyclerView = findViewById(R.id.id_listcart);
        recyclerView.setHasFixedSize(true);
        btn_muahang = findViewById(R.id.btn_muaHang);

        dao = new CartDAO(this);
        adapter = new CartAdapter(CartActivity.this, dao);
        arrayList = dao.selectAllForUser(objUser.getId());
        if (arrayList.isEmpty()){
            Toast.makeText(CartActivity.this, "Giỏ hàng của bạn đang trống!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        adapter.setData(arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        btn_muahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrayList.isEmpty()){
                    Toast.makeText(CartActivity.this, "Giỏ hàng của bạn đang trống!", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    startActivity(new Intent(CartActivity.this, BillUserActivity.class));
                }
            }
        });
    }
}