package tranghtmph26263.fpoly.ungdungbanxedap.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.CartAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.CommentAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.ProductBillAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.CartDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.CommentDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Bill;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.CartDetail;

public class CommentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CartDAO dao;
    CommentDAO commentDAO;
    ArrayList<CartDetail> arrayList = new ArrayList<CartDetail>();
    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        recyclerView = findViewById(R.id.id_comment);
        recyclerView.setHasFixedSize(true);
        dao = new CartDAO(this);
        commentDAO = new CommentDAO(this);

        Bundle bundle = getIntent().getExtras();
        if ( bundle == null){  return; }
        Bill obj  = (Bill) bundle.get("ArrPro");
        String detailString = obj.getDetail();
        arrayList = convertToCartDetails(detailString);

        commentAdapter = new CommentAdapter(CommentActivity.this, commentDAO );
        commentAdapter.setData(arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(commentAdapter);

    }

    private static ArrayList<CartDetail> convertToCartDetails(String inputString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<CartDetail>>() {}.getType();

        return gson.fromJson(inputString, listType);
    }
}