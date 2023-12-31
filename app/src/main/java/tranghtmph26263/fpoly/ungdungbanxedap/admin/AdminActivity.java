package tranghtmph26263.fpoly.ungdungbanxedap.admin;

import androidx.appcompat.app.AppCompatActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AdminActivity extends AppCompatActivity {
    TextView tv_qly_user, tv_qly_category, tv_qly_product, tv_qly_bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        AnhXa();

        tv_qly_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tv_qly_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });
        tv_qly_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tv_qly_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    public void AnhXa(){
        tv_qly_user = findViewById(R.id.txt_qly_user);
        tv_qly_category = findViewById(R.id.txt_qly_category);
        tv_qly_product = findViewById(R.id.txt_qly_product);
        tv_qly_bill = findViewById(R.id.txt_qly_bill);
    }
}