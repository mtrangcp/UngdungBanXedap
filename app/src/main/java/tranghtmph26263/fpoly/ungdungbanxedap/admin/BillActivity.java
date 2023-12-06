package tranghtmph26263.fpoly.ungdungbanxedap.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tranghtmph26263.fpoly.ungdungbanxedap.MainActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.BillAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.SpinStatusBill;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.BillDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Bill;
import tranghtmph26263.fpoly.ungdungbanxedap.myInterface.ClickItemBillListener;
import tranghtmph26263.fpoly.ungdungbanxedap.user.ProductDetailActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class BillActivity extends AppCompatActivity {
    Spinner spinner;
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
        spinner = findViewById(R.id.id_spinner_status_admin);

        ArrayList<String> list = new ArrayList();
        list.add("Tất cả");
        list.add("Đang chờ xác nhận");
        list.add("Đã xác nhận");
        list.add("Đã bị hủy");
        list.add("Đang giao hàng");
        list.add("Đã giao thành công");

        ArrayAdapter<String> adapterStatus = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterStatus);
        spinner.setSelection(0);



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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object chuoi = spinner.getSelectedItem();
                if (chuoi != null){
                    Log.d(TAG, "onItemSelected: "+ chuoi.toString());
                    Toast.makeText(BillActivity.this, "da chon: "+ chuoi.toString(), Toast.LENGTH_SHORT).show();

                    if (chuoi.toString().equals("Tất cả") ){
                        arrayList = dao.selectAll();
                        adapter.setData(arrayList);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);

                    }else if (chuoi.toString().equals("Đang chờ xác nhận") ){
                        arrayList = dao.selectWithStatus(0);
                        adapter.setData(arrayList);
                        if ( arrayList.isEmpty()){
                            Toast.makeText(BillActivity.this, "Chưa có đơn hàng nào đang chờ xác nhận!", Toast.LENGTH_SHORT).show();
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);

                    }else if (chuoi.toString().equals("Đã xác nhận") ){
                        arrayList = dao.selectWithStatus(1);
                        adapter.setData(arrayList);
                        if ( arrayList.isEmpty()){
                            Toast.makeText(BillActivity.this, "Chưa có đơn hàng nào đã xác nhận!", Toast.LENGTH_SHORT).show();
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);

                    }else if (chuoi.toString().equals("Đã bị hủy") ){
                        arrayList = dao.selectWithStatus(2);
                        adapter.setData(arrayList);
                        if ( arrayList.isEmpty()){
                            Toast.makeText(BillActivity.this, "Không có đơn hàng nào đã bị hủy!", Toast.LENGTH_SHORT).show();
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);

                    }else if (chuoi.toString().equals("Đang giao hàng") ){
                        arrayList = dao.selectWithStatus(3);
                        adapter.setData(arrayList);
                        if ( arrayList.isEmpty()){
                            Toast.makeText(BillActivity.this, "Không có đơn hàng nào đang giao!", Toast.LENGTH_SHORT).show();
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);

                    }else if (chuoi.toString().equals("Đã giao thành công") ){
                        arrayList = dao.selectWithStatus(4);
                        adapter.setData(arrayList);
                        if ( arrayList.isEmpty()){
                            Toast.makeText(BillActivity.this, "Không có đơn hàng nào đã giao!", Toast.LENGTH_SHORT).show();
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);
                    }

                }else{
                    Toast.makeText(BillActivity.this, "da chon: rong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

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