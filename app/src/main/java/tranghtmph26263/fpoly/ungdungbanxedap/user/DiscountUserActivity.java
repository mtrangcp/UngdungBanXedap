package tranghtmph26263.fpoly.ungdungbanxedap.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.DiscountAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.DiscountUserAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.admin.DiscountActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.DiscountDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Discount;

public class DiscountUserActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DiscountUserAdapter adapter;
    ArrayList<Discount> arrayList;
    DiscountDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_user);

        recyclerView = findViewById(R.id.id_listDiscount);
        recyclerView.setHasFixedSize(true);

        dao = new DiscountDAO(this);
        adapter = new DiscountUserAdapter(DiscountUserActivity.this, dao);

        arrayList = dao.selectAll();
        adapter.setData(arrayList);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }
}