package tranghtmph26263.fpoly.ungdungbanxedap.chao;

import androidx.appcompat.app.AppCompatActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.login.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ChaoActivity extends AppCompatActivity {
    ImageView img_chao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chao);

        img_chao = findViewById(R.id.img_logoChao);

        Glide.with(this).load(R.mipmap.dapxe).into(img_chao);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent  = new Intent(ChaoActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        }, 5000);

    }
}