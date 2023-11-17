package tranghtmph26263.fpoly.ungdungbanxedap.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.CategoryAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.CategoryDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.database.MyDbHelper;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Category;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    CategoryAdapter adapter;
    ArrayList<Category> arrayList = new ArrayList<>();
    RecyclerView recyclerView;
    FloatingActionButton fab;
    CategoryDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        fab = findViewById(R.id.id_fab_add_category);
        recyclerView = findViewById(R.id.id_qly_category);
        recyclerView.setHasFixedSize(true);

        adapter = new CategoryAdapter(CategoryActivity.this, dao);
        dao = new CategoryDAO(this);

        arrayList = dao.selectAll();
        adapter.setData(arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.showDialogAdd(CategoryActivity.this);
            }
        });

    }
}