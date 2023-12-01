package tranghtmph26263.fpoly.ungdungbanxedap.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tranghtmph26263.fpoly.ungdungbanxedap.MainActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.BillAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.BillDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Bill;
import tranghtmph26263.fpoly.ungdungbanxedap.myInterface.ClickItemBillListener;
import tranghtmph26263.fpoly.ungdungbanxedap.user.ProductDetailActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class BillActivity extends AppCompatActivity {
    String TAG = "aaaa";
    RecyclerView recyclerView;
    ImageView imgHome;
    BillAdapter adapter;
    BillDAO dao;
    ArrayList<Bill> arrayList = new ArrayList<Bill>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        recyclerView = findViewById(R.id.id_listBillAdmin);
        recyclerView.setHasFixedSize(true);

        dao = new BillDAO(this);
        adapter = new BillAdapter(BillActivity.this, dao, new ClickItemBillListener() {
            @Override
            public void onClickItemBIll(Bill obj) {
                Intent intent = new Intent(BillActivity.this, BillDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("objBill", obj);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        arrayList = dao.selectAll();
        adapter.setData(arrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        imgHome = findViewById(R.id.id_bill_home);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BillActivity.this, AdminActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        arrayList = dao.selectAll();
        adapter.setData(arrayList);
    }
}