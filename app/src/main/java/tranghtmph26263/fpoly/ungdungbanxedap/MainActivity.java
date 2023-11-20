package tranghtmph26263.fpoly.ungdungbanxedap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.adapter.ProductAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.admin.AdminActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.chao.LoginActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.ProductDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Product;

public class MainActivity extends AppCompatActivity {
    ImageView img_logout;
    ViewFlipper viewFlipper;
    Animation in, out;

    ProductAdapter adapterProduct;
    ArrayList<Product> arrayList = new ArrayList<>();
    RecyclerView recyclerViewNgang, recyclerViewDoc;
    ProductDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chuyenDong();

        recyclerViewNgang = findViewById(R.id.layout_recyclerView_ngang);
        recyclerViewDoc = findViewById(R.id.layout_recyclerView_doc);
        img_logout = findViewById(R.id.id_logout_user);
        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });




    }

    public void chuyenDong(){
        in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        out = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        viewFlipper = findViewById(R.id.id_viewFlipper);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(in);
        viewFlipper.setOutAnimation(out);
    }
}