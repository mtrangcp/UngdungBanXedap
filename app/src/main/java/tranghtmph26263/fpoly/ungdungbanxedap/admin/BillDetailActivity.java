package tranghtmph26263.fpoly.ungdungbanxedap.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Product;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.User;
import tranghtmph26263.fpoly.ungdungbanxedap.user.BillUserActivity;

public class BillDetailActivity extends AppCompatActivity {
    String TAG = "aaaa";
    RecyclerView recyclerView;
    TextView tv_discount, tv_tongBill, tv_ngMua, tv_ngNhan, tv_diaChi, tv_sdt, tv_ngayMua, tv_status;
    Button btnTuChoi, btnChapNhan;
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
        setContentView(R.layout.activity_bill_detail);
        anhXa();
        productDAO = new ProductDAO(this);
        billDAO = new BillDAO(this);
        discountDAO = new DiscountDAO(this);
        userDAO = new UserDAO(this);
        dao = new CartDAO(this);
        adapter = new CartAdapter(BillDetailActivity.this, dao);
        productBillAdapter = new ProductBillAdapter(BillDetailActivity.this, dao);
        arrayList = dao.selectAll();
        productBillAdapter.setData(arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(productBillAdapter);

        Bundle bundle = getIntent().getExtras();
        if ( bundle == null){  return; }
        Bill obj  = (Bill) bundle.get("objBill");
        Log.d(TAG, "objBill: "+obj.toString());

        Discount objDiscount = discountDAO.selectOne(obj.getDiscount_id());
        tv_discount.setText("Mã giảm giá: "+objDiscount.getCode_name());

        tv_tongBill.setText("Tổng bill: "+obj.getReal_price());
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
            btnChapNhan.setEnabled(false);
            btnTuChoi.setEnabled(false);
        }else if ( obj.getStatus() == 2){
            tv_status.setText("Trạng thái: Đã bị hủy");
            btnChapNhan.setEnabled(false);
            btnTuChoi.setEnabled(false);
        }

        btnChapNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int res = billDAO.updateStatusChapNhan(obj);

                if ( res > 0){
                    int check = 0;
                    for ( int i=0; i< arrayList.size(); i++){
                        Product product = productDAO.selectOne(arrayList.get(i).getProduct_id());
                        int currentStock =  product.getStock() - arrayList.get(i).getQuantity();
                        int sold = product.getSold() + arrayList.get(i).getQuantity();
                        int kq = productDAO.updateStock(product.getId(), currentStock, sold);
                        if ( kq > 0){
                            check++;
                        }
                    }
                    if ( check == arrayList.size()){
                        Toast.makeText(BillDetailActivity.this, "Chấp nhận thành công!", Toast.LENGTH_SHORT).show();
                        tv_status.setText("Trạng thái: Đã xác nhận");
                    }
                    btnTuChoi.setEnabled(false);
                    btnChapNhan.setEnabled(false);
                }else{
                    Toast.makeText(BillDetailActivity.this, "Chấp nhận thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnTuChoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int res = billDAO.updateStatusTuChoi(obj);
                Toast.makeText(BillDetailActivity.this, "Bạn đã từ chối đơn hàng này!", Toast.LENGTH_SHORT).show();
                btnChapNhan.setEnabled(false);
                btnTuChoi.setEnabled(false);
                tv_status.setText("Trạng thái: Đã bị hủy");
            }
        });
    }
    public void anhXa(){
        recyclerView = findViewById(R.id.listSpBillDetail);
        tv_discount = findViewById(R.id.tv_giamGia);
        tv_tongBill = findViewById(R.id.tv_tongBIllDetail);
        tv_ngMua = findViewById(R.id.tv_ng_mua);
        tv_ngNhan = findViewById(R.id.tv_ng_nhan);
        tv_diaChi = findViewById(R.id.tv_dia_chi);
        tv_sdt = findViewById(R.id.tv_sdt);
        tv_ngayMua = findViewById(R.id.tv_ngay_mua);
        tv_status = findViewById(R.id.tv_trang_thai);
        btnTuChoi = findViewById(R.id.btn_tuChoi);
        btnChapNhan = findViewById(R.id.btn_chapNhan);
    }
}