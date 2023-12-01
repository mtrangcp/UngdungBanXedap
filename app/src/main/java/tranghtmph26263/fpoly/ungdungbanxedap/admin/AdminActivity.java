package tranghtmph26263.fpoly.ungdungbanxedap.admin;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.chao.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {
    ImageView tvLogout;
    TextView tv_qly_user, tv_qly_category, tv_qly_product, tv_qly_bill,tv_qly_discount, tv_qly_thongKe, tv_changePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        AnhXa();
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, LoginActivity.class));
            }
        });

        tv_qly_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, UserActivity.class));
            }
        });
        tv_qly_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, CategoryActivity.class));
            }
        });
        tv_qly_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, ProductActivity.class));
            }
        });
        tv_qly_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, BillActivity.class));
            }
        });
        tv_qly_thongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, StatisticalActivity.class));
            }
        });
        tv_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, ChangePasswordActivity.class));            }
        });
        tv_qly_discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, DiscountActivity.class));
            }
        });
    }


    public void AnhXa(){
        tv_qly_user = findViewById(R.id.txt_qly_user);
        tv_qly_category = findViewById(R.id.txt_qly_category);
        tv_qly_product = findViewById(R.id.txt_qly_product);
        tv_qly_bill = findViewById(R.id.txt_qly_bill);
        tv_qly_thongKe = findViewById(R.id.txt_qly_thongKe);
        tv_changePass = findViewById(R.id.txt_doiMK);
        tv_qly_discount = findViewById(R.id.txt_qly_discount);
        tvLogout = findViewById(R.id.id_logout_admin);
    }
}