package tranghtmph26263.fpoly.ungdungbanxedap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.adapter.ProductHorizontalAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.ProductVerticalAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.chao.LoginActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.ProductDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Product;
import tranghtmph26263.fpoly.ungdungbanxedap.fragment.ChangePassUserFragment;
import tranghtmph26263.fpoly.ungdungbanxedap.fragment.InfoFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ViewFlipper viewFlipper;
    Animation in, out;

    ProductHorizontalAdapter adapterProduct;
    ProductVerticalAdapter productVerticalAdapter;
    ArrayList<Product> arrayList = new ArrayList<>();
    RecyclerView recyclerViewNgang, recyclerViewDoc;
    ProductDAO dao;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chuyenDong();

        drawerLayout = findViewById(R.id.id_drawerlayout);
        toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.opendrawer,R.string.closedrawer);
        toggle.syncState();

        navigationView = findViewById(R.id.id_navView);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerViewNgang = findViewById(R.id.layout_recyclerView_ngang);
        recyclerViewNgang.setHasFixedSize(true);
        recyclerViewDoc = findViewById(R.id.layout_recyclerView_doc);
        recyclerViewDoc.setHasFixedSize(true);

        adapterProduct = new ProductHorizontalAdapter(MainActivity.this, dao);
        dao = new ProductDAO(this);

        arrayList = dao.selectAllForUser();
        adapterProduct.setData(arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerViewNgang.setLayoutManager(linearLayoutManager);
        recyclerViewNgang.setAdapter(adapterProduct);

        productVerticalAdapter = new ProductVerticalAdapter(MainActivity.this, dao);
        productVerticalAdapter.setData(arrayList);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewDoc.setLayoutManager(manager);
        recyclerViewDoc.setAdapter(productVerticalAdapter);

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

    public void replacefragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.id_framlayout, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if ( drawerLayout.isDrawerOpen(navigationView)){
            drawerLayout.closeDrawer(navigationView);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.info) {
            replacefragment(InfoFragment.newInstance());

        } else if (id == R.id.change_pass_user) {
            replacefragment(ChangePassUserFragment.newInstance());

        } else if (id == R.id.logout) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }else if (id == R.id.id_home) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        drawerLayout.closeDrawer(navigationView);
        return true;
    }
}