package tranghtmph26263.fpoly.ungdungbanxedap.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.DiscountAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.DiscountDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Discount;

public class DiscountActivity extends AppCompatActivity {
    DiscountAdapter adapter;
    ArrayList<Discount> arrayList;
    DiscountDAO dao;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);

        fab = findViewById(R.id.id_fab_add_discout);
        recyclerView = findViewById(R.id.id_qly_discout);
        recyclerView.setHasFixedSize(true);
        img = findViewById(R.id.id_discount_home);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DiscountActivity.this, AdminActivity.class));
            }
        });

        adapter = new DiscountAdapter(DiscountActivity.this, dao);
        dao = new DiscountDAO(this);

        arrayList = dao.selectAll();
        adapter.setData(arrayList);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.showDialogAdd(DiscountActivity.this);
            }
        });

    }
}