package tranghtmph26263.fpoly.ungdungbanxedap.user;

import static tranghtmph26263.fpoly.ungdungbanxedap.user.DiscountUserActivity.arrayListForUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import tranghtmph26263.fpoly.ungdungbanxedap.MainActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.CartAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.ProductBillAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.SpinCategoryAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.SpinDiscountAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.admin.ProductActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.BillDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.CartDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.CategoryDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.DiscountDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.UserDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Bill;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.CartDetail;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Category;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Discount;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.User;

public class BillUserActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView tv_tempPrice, tv_realPrice;
    Spinner spinner;
    TextInputEditText ed_fullname, ed_address, ed_phone;
    Button btn_datHang;

    CartDAO dao;
    CartAdapter adapter;
    ArrayList<CartDetail> arrayList = new ArrayList<CartDetail>();
    ProductBillAdapter productBillAdapter;

    int tongTien =0;
    UserDAO userDAO;
    BillDAO billDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_user);
        anhXa();

        SpinDiscountAdapter adapterSpinner = new SpinDiscountAdapter(arrayListForUser);
        spinner.setAdapter(adapterSpinner);
        DiscountDAO discountDAO = new DiscountDAO(BillUserActivity.this);
        SpinDiscountAdapter spinDiscountAdapter = new SpinDiscountAdapter(discountDAO.selectAllForUser());
        spinner.setAdapter(spinDiscountAdapter);

        dao = new CartDAO(this);
        adapter = new CartAdapter(BillUserActivity.this, dao);
        productBillAdapter = new ProductBillAdapter(BillUserActivity.this, dao);
        arrayList = dao.selectAll();
        productBillAdapter.setData(arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(productBillAdapter);

        int i;
        for ( i=0; i<arrayList.size(); i++){
            tongTien += arrayList.get(i).getPrice()* arrayList.get(i).getQuantity();
        }
        tv_tempPrice.setText("Tổng tiền: "+formatCurrency(tongTien));
        Discount objDiscount = (Discount) spinner.getSelectedItem();
        int giam = objDiscount.getValue();
        int real_price = tongTien - giam;
//        tv_realPrice.setText("Giá đã giảm: "+ formatCurrency(real_price));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Discount objDiscount = (Discount) spinner.getSelectedItem();
                int giam = objDiscount.getValue();
                int real_price = tongTien - giam;
                tv_realPrice.setText("Giá đã giảm: "+formatCurrency(real_price));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                tv_realPrice.setText("Giá đã giảm: "+tongTien);
            }
        });

        btn_datHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( ed_phone.getText().toString().trim().isEmpty()){
                    Toast.makeText(BillUserActivity.this, "Vui lòng nhập số điện thoại người nhận!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ed_phone.getText().toString().trim().length()> 10){
                    Toast.makeText(BillUserActivity.this, "Vui lòng nhập đúng số điện thoại người nhận!", Toast.LENGTH_SHORT).show();
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
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String date = dateFormat.format(currentDate);

                SharedPreferences sharedPreferences = getSharedPreferences("USER_INFO",MODE_PRIVATE );
                String user = sharedPreferences.getString("USERNAME", "");
                userDAO = new UserDAO(BillUserActivity.this);
                User objUser = userDAO.selectOneWithUsername(user);
                Log.d("aaaa", "user mua hang: "+ objUser.getFullname());
                
                billDAO = new BillDAO(BillUserActivity.this);
                Discount objDiscountChon = (Discount) spinner.getSelectedItem();

                Bill objBill = new Bill();
                objBill.setAddress(ed_address.getText().toString().trim());
                objBill.setCreated_date(date);
                objBill.setPhone(ed_phone.getText().toString().trim());
                objBill.setDiscount_id(objDiscountChon.getId());
                objBill.setUser_fullname(ed_fullname.getText().toString().trim());
                objBill.setTemp_price(tongTien);
                objBill.setReal_price(real_price);
                objBill.setUser_id(objUser.getId());
                objBill.setStatus(0);
                objBill.setDetail(arrayList.toString());
                Log.d("aaaa", "\n detail: "+ objBill.getDetail());

                long res = billDAO.insertNew(objBill);
                if ( res > 0){
                    Toast.makeText(BillUserActivity.this, "Đặt hàng thành công, vui lòng chờ xác nhận!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BillUserActivity.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(BillUserActivity.this, "Đặt hàng thất bại!", Toast.LENGTH_SHORT).show();
                }

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

    private static  String formatCurrency(int amount) {
        // Locale cho tiếng Việt và định dạng tiền tệ
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeVN);

        // Đặt loại tiền tệ là đồng
        currencyFormatter.setCurrency(Currency.getInstance("VND"));

        // Định dạng số
        return currencyFormatter.format(amount);
    }

}