package tranghtmph26263.fpoly.ungdungbanxedap.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.CartAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.ProductBillAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.BillDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.CartDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.DiscountDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.ProductDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.UserDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Bill;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.CartDetail;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Discount;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.User;

public class HistoryBillDetailActivity extends AppCompatActivity {
    String TAG = "aaaa";
    RecyclerView recyclerView;
    TextView tv_discount, tv_tongBill, tv_ngMua, tv_ngNhan, tv_diaChi, tv_sdt, tv_ngayMua, tv_status;
    Button btnHuyDon;
    DiscountDAO discountDAO;
    UserDAO userDAO;
    BillDAO billDAO;
    ProductDAO productDAO;

    CartDAO dao;
    CartAdapter adapter;
    ArrayList<CartDetail> arrayList = new ArrayList<CartDetail>();
    ProductBillAdapter productBillAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_bill_detail);
        anhXa();

        productDAO = new ProductDAO(this);
        billDAO = new BillDAO(this);
        discountDAO = new DiscountDAO(this);
        userDAO = new UserDAO(this);
        dao = new CartDAO(this);

        Bundle bundle = getIntent().getExtras();
        if ( bundle == null){  return; }
        Bill obj  = (Bill) bundle.get("objBillUser");
        String detailString = obj.getDetail();
        arrayList = convertToCartDetails(detailString);

        adapter = new CartAdapter(HistoryBillDetailActivity.this, dao);
        productBillAdapter = new ProductBillAdapter(HistoryBillDetailActivity.this, dao);
        productBillAdapter.setData(arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(productBillAdapter);

        if (obj.getDiscount_id() == 0 ){
            tv_discount.setText("Mã giảm giá: Bạn không sử dụng giảm giá cho đơn hàng này!");
        }else{
            Discount objDiscount = discountDAO.selectOne(obj.getDiscount_id());
            tv_discount.setText("Mã giảm giá: "+objDiscount.getCode_name());
        }

        tv_tongBill.setText("Tổng bill: "+formatCurrency(obj.getReal_price()));
        User objUser = userDAO.selectOne(obj.getUser_id());
        tv_ngMua.setText("Người mua: "+objUser.getFullname());
        tv_ngNhan.setText("Người nhận: "+obj.getUser_fullname());
        tv_diaChi.setText("Địa chỉ nhận: "+obj.getAddress());
        tv_sdt.setText("Sđt người nhận: "+obj.getPhone());
        tv_ngayMua.setText("Ngày mua: "+obj.getCreated_date());
        if ( obj.getStatus() == 0){
            tv_status.setText("Trạng thái: Đang chờ xác nhận");
        }else if ( obj.getStatus() == 1){
            tv_status.setText("Trạng thái: Đã xác nhận");
            btnHuyDon.setEnabled(false);
        }else if ( obj.getStatus() == 2){
            tv_status.setText("Trạng thái: Đã bị hủy");
            btnHuyDon.setEnabled(false);
        }else if ( obj.getStatus() == 3){
            tv_status.setText("Trạng thái: Đang giao");
            btnHuyDon.setEnabled(false);
            btnHuyDon.setVisibility(View.INVISIBLE);
        }else if ( obj.getStatus() == 4){
            tv_status.setText("Trạng thái: Đã giao thành công");
            btnHuyDon.setEnabled(false);
            btnHuyDon.setVisibility(View.INVISIBLE);
        }

        btnHuyDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int res = billDAO.updateStatusTuChoi(obj);
                Toast.makeText(HistoryBillDetailActivity.this, "Bạn đã hủy đơn hàng này!", Toast.LENGTH_SHORT).show();
                btnHuyDon.setEnabled(false);
                tv_status.setText("Trạng thái: Đã bị hủy");
            }
        });
    }

    private static ArrayList<CartDetail> convertToCartDetails(String inputString) {
        Gson gson = new Gson();

        // Xác định kiểu dữ liệu bạn muốn phân tích cú pháp
        Type listType = new TypeToken<ArrayList<CartDetail>>() {}.getType();

        // Phân tích cú pháp chuỗi thành ArrayList<CartDetail>
        return gson.fromJson(inputString, listType);
    }

    public void anhXa(){
        recyclerView = findViewById(R.id.listSpBillDetail_user);
        tv_discount = findViewById(R.id.tv_giamGia_user);
        tv_tongBill = findViewById(R.id.tv_tongBIllDetail_user);
        tv_ngMua = findViewById(R.id.tv_ng_mua_user);
        tv_ngNhan = findViewById(R.id.tv_ng_nhan_user);
        tv_diaChi = findViewById(R.id.tv_dia_chi_user);
        tv_sdt = findViewById(R.id.tv_sdt_user);
        tv_ngayMua = findViewById(R.id.tv_ngay_mua_user);
        tv_status = findViewById(R.id.tv_trang_thai_user);
        btnHuyDon = findViewById(R.id.btn_huyDon);
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