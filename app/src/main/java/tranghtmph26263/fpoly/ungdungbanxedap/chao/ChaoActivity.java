package tranghtmph26263.fpoly.ungdungbanxedap.chao;

import androidx.appcompat.app.AppCompatActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.DiscountDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.DiscountUserDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Discount;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.DiscountUser;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ChaoActivity extends AppCompatActivity {
    ImageView img_chao;
    DiscountUserDAO discountDAO;
    DiscountDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chao);

        img_chao = findViewById(R.id.img_logoChao);

//        dao = new DiscountDAO(ChaoActivity.this);
//        ArrayList<Discount> arrayListDiscount = dao.selectAll();
//        Log.d("cccc", "ket qua delete: "+ arrayListDiscount.toString());

        Glide.with(this).load(R.mipmap.dapxe).into(img_chao);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent  = new Intent(ChaoActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);

    }
}