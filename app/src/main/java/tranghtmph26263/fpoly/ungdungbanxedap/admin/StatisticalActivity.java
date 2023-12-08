package tranghtmph26263.fpoly.ungdungbanxedap.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.ProductHorizontalAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.TopBanChayAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.BillDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.ProductDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Bill;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Product;
import tranghtmph26263.fpoly.ungdungbanxedap.myInterface.ClickItemProductListener;

public class StatisticalActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    BillDAO billDAO;
    ProductDAO productDAO;
    ArrayList<Product> arrayList = new ArrayList<>();
    ArrayList<Bill> arrayListAll = new ArrayList<Bill>();
    TopBanChayAdapter adapter;
    TextView tv_doanhThu, tv_tuNgay, tv_denNgay, tv_doanhThuTheoTime, tv_bill_huy, tv_tongSLBill;
    int doanhthu =0, ngay, thang, nam;
    EditText ed_tuNgay, ed_denNgay;
    ImageView img_doanhThu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);

        recyclerView = findViewById(R.id.list_ban_chay);
        recyclerView.setHasFixedSize(true);
        billDAO = new BillDAO(this);

        adapter = new TopBanChayAdapter(StatisticalActivity.this, productDAO);
        productDAO = new ProductDAO(this);
        arrayList = productDAO.selectTopBanChay();
        adapter.setData(arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        tv_doanhThu = findViewById(R.id.id_doanhThu);
        arrayListAll = billDAO.selectAllForDoanhThu();
        for ( int i=0; i< arrayListAll.size(); i++){
            doanhthu += arrayListAll.get(i).getReal_price();
        }
        tv_doanhThu.setText("Tổng doanh thu: "+ formatCurrency(doanhthu));

        tv_tuNgay = findViewById(R.id.tv_start_date);
        tv_denNgay = findViewById(R.id.tv_end_date);
        tv_doanhThuTheoTime = findViewById(R.id.id_doanhThu_theo_time);
        ed_tuNgay = findViewById(R.id.ed_start_date);
        ed_denNgay = findViewById(R.id.ed_end_date);

        tv_tuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar lich = Calendar.getInstance();

                ngay = lich.get(Calendar.DAY_OF_MONTH);
                thang = lich.get(Calendar.MONTH);
                nam = lich.get(Calendar.YEAR);
                DatePickerDialog bangLich = new DatePickerDialog(StatisticalActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String day = "";
                        if ( dayOfMonth < 10){
                            day = "0"+ String.valueOf(dayOfMonth);
                        }
                        ed_tuNgay.setText(String.format("%s/%s/%s", day, month+1, year));
                    }
                }, nam, thang, ngay);
                bangLich.show();
            }
        });

        tv_denNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar lich = Calendar.getInstance();

                ngay = lich.get(Calendar.DAY_OF_MONTH);
                thang = lich.get(Calendar.MONTH);
                nam = lich.get(Calendar.YEAR);
                DatePickerDialog bangLich = new DatePickerDialog(StatisticalActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String day = "";
                        if ( dayOfMonth < 10){
                            day = "0"+ String.valueOf(dayOfMonth);
                        }
                        ed_denNgay.setText(String.format("%s/%s/%s", day, month+1, year));
                    }
                }, nam, thang, ngay);
                bangLich.show();
            }
        });

        img_doanhThu = findViewById(R.id.img_doanhThu);
        img_doanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tuNgay = ed_tuNgay.getText().toString();
                String denNgay = ed_denNgay.getText().toString();
                BillDAO billDAO = new BillDAO(StatisticalActivity.this);
                tv_doanhThuTheoTime.setText("Doanh Thu: " + formatCurrency(billDAO.getDoanhThu(tuNgay, denNgay)) );
            }
        });

        tv_bill_huy = findViewById(R.id.id_bill_biHuy);
        ArrayList<Bill> arrayBillHuy = new ArrayList<>();
        arrayBillHuy = billDAO.selectBillHuy();
        tv_bill_huy.setText("Số bill bị hủy: "+ arrayBillHuy.size());

        tv_tongSLBill = findViewById(R.id.id_total_bill);
        ArrayList<Bill> arrayTotalBill = new ArrayList<>();
        arrayTotalBill = billDAO.selectAll();
        tv_tongSLBill.setText("Số lượng bill: "+ arrayTotalBill.size());

    }

    private static  String formatCurrency(int amount) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeVN);

        currencyFormatter.setCurrency(Currency.getInstance("VND"));

        return currencyFormatter.format(amount);
    }
}