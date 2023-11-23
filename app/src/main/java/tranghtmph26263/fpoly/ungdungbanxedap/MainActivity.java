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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import tranghtmph26263.fpoly.ungdungbanxedap.adapter.ProductHorizontalAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.ProductVerticalAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.SpinCategoryAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.admin.ProductActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.chao.LoginActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.CategoryDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.ProductDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Category;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Product;
import tranghtmph26263.fpoly.ungdungbanxedap.fragment.ChangePassUserFragment;
import tranghtmph26263.fpoly.ungdungbanxedap.fragment.InfoFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ViewFlipper viewFlipper;
    Animation in, out;

    ProductHorizontalAdapter adapterProduct;
    ProductVerticalAdapter productVerticalAdapter;
    ArrayList<Product> arrayList = new ArrayList<>();
    ArrayList<Product> listNewPro = new ArrayList<Product>();
    RecyclerView recyclerViewNgang, recyclerViewDoc;
    ProductDAO dao;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;

    EditText ed_timKiem;
    ImageView img_search;
    Spinner spinner_loc;
    TextView username_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chuyenDong();

        ed_timKiem =  findViewById(R.id.ed_timKiem);
        img_search = findViewById(R.id.img_search);
        spinner_loc = findViewById(R.id.spinner_loc);

        drawerLayout = findViewById(R.id.id_drawerlayout);
        toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.opendrawer,R.string.closedrawer);
        toggle.syncState();

        navigationView = findViewById(R.id.id_navView);
        navigationView.setNavigationItemSelectedListener(this);

        username_account = navigationView.getHeaderView(0).findViewById(R.id.id_username_account);
        SharedPreferences sharedPreferences = getSharedPreferences("USER_INFO",MODE_PRIVATE );
        String user = sharedPreferences.getString("USERNAME", "admin");
        username_account.setText("Welcome: "+user);

        recyclerViewNgang = findViewById(R.id.layout_recyclerView_ngang);
        recyclerViewNgang.setHasFixedSize(true);
        recyclerViewDoc = findViewById(R.id.layout_recyclerView_doc);
        recyclerViewDoc.setHasFixedSize(true);
        dao = new ProductDAO(this);
        arrayList = dao.selectAllForUser();

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String today = dateFormat.format(currentDate);

        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date yesterdayDate = calendar.getTime();
        String yesterday = dateFormat.format(yesterdayDate);


        listNewPro = dao.selectAllNewProduct(today, yesterday);
        if ( listNewPro.isEmpty()){
            Toast.makeText(this, "k co sp moi", Toast.LENGTH_SHORT).show();
        }
        Log.d("aaaa", "list sp moi nhat: "+ listNewPro.size());

        adapterProduct = new ProductHorizontalAdapter(MainActivity.this, dao);
        adapterProduct.setData(listNewPro);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false);
        recyclerViewNgang.setLayoutManager(linearLayoutManager);
        recyclerViewNgang.setAdapter(adapterProduct);
        // spinner
        CategoryDAO categoryDAO = new CategoryDAO(MainActivity.this);
        SpinCategoryAdapter adapter = new SpinCategoryAdapter(categoryDAO.selectAll());
        spinner_loc.setAdapter(adapter);

        spinner_loc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Category objCtegory = (Category) spinner_loc.getSelectedItem();
                int category_id = objCtegory.getId();
                arrayList = dao.selectAllWithCategoryId(category_id);
                Log.d("aaaaa", "name da chon: "+ objCtegory.getName());
                Log.d("aaaaa", "list sau khi select with id: "+ arrayList.size()+ "\n"+ arrayList);

                productVerticalAdapter = new ProductVerticalAdapter(MainActivity.this, dao);
                productVerticalAdapter.setData(arrayList);
                StaggeredGridLayoutManager manager1 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recyclerViewDoc.setLayoutManager(manager1);
                recyclerViewDoc.setAdapter(productVerticalAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                arrayList = dao.selectAllForUser();
                productVerticalAdapter = new ProductVerticalAdapter(MainActivity.this, dao);
                productVerticalAdapter.setData(arrayList);
                StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recyclerViewDoc.setLayoutManager(manager);
                recyclerViewDoc.setAdapter(productVerticalAdapter);
                productVerticalAdapter.notifyDataSetChanged();
            }
        });

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Product> list = dao.selectAllForUser();;
                String keyword = ed_timKiem.getText().toString().trim();
                Log.d("aaaaa", "list trc khi tim kiem: "+ list.size());
                ArrayList<Product> searchResultList = new ArrayList<Product>();
                for (Product product : list) {
                    if (product.getName().toLowerCase().contains(keyword.toLowerCase())) {
                        searchResultList.add(product);
                    }
                }
                Log.d("aaaaa", "list sau khi tim kiem: "+ searchResultList.size());
                productVerticalAdapter = new ProductVerticalAdapter(MainActivity.this, dao);
                productVerticalAdapter.setData(searchResultList);
                StaggeredGridLayoutManager manager1 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recyclerViewDoc.setLayoutManager(manager1);
                recyclerViewDoc.setAdapter(productVerticalAdapter);
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