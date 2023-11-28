package tranghtmph26263.fpoly.ungdungbanxedap.user;

import static tranghtmph26263.fpoly.ungdungbanxedap.user.DiscountUserActivity.arrayListForUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.CartAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.SpinCategoryAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.SpinDiscountAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.admin.ProductActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.CartDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.CategoryDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.DiscountDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.CartDetail;

public class BillUserActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView tv_tempPrice, tv_realPrice;
    Spinner spinner;
    TextInputEditText ed_fullname, ed_address, ed_phone;
    Button btn_datHang;

    CartDAO dao;
    CartAdapter adapter;
    ArrayList<CartDetail> arrayList = new ArrayList<CartDetail>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_user);
        anhXa();

        SpinDiscountAdapter adapterSpinner = new SpinDiscountAdapter(arrayListForUser);
        spinner.setAdapter(adapterSpinner);

        dao = new CartDAO(this);
        adapter = new CartAdapter(BillUserActivity.this, dao);
        arrayList = dao.selectAll();
        adapter.setData(arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        btn_datHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();





            }
        });

    }

    public void anhXa(){
        recyclerView = findViewById(R.id.listSpDatHang);
        recyclerView.setHasFixedSize(true);
        tv_tempPrice = findViewById(R.id.tv_tongBIll);
        tv_realPrice = findViewById(R.id.tv_giaDaGiam);
        spinner = findViewById(R.id.spinner_discount);
        ed_fullname = findViewById(R.id.id_fullname_bill);
        ed_address = findViewById(R.id.id_address_bill);
        ed_phone = findViewById(R.id.id_phone_bill);
        btn_datHang = findViewById(R.id.btn_datHang);
    }

    public void validate(){
        if ( ed_phone.getText().toString().trim().isEmpty()){
            Toast.makeText(BillUserActivity.this, "Vui lòng nhập số điện thoại người nhận!", Toast.LENGTH_SHORT).show();
            return;
        }
        if ( ed_fullname.getText().toString().trim().isEmpty()){
            Toast.makeText(BillUserActivity.this, "Vui lòng nhập họ tên người nhận!", Toast.LENGTH_SHORT).show();
            return;
        }
        if ( ed_address.getText().toString().trim().isEmpty()){
            Toast.makeText(BillUserActivity.this, "Vui lòng nhập địa chỉ nhận hàng!", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}