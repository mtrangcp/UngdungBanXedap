package tranghtmph26263.fpoly.ungdungbanxedap.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.ProductAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.SpinCategoryAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.CategoryDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.ProductDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Category;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Product;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class ProductActivity extends AppCompatActivity {
    ProductAdapter adapterProduct;
    ArrayList<Product> arrayList = new ArrayList<>();
    RecyclerView recyclerView;
    FloatingActionButton fab;
    ProductDAO dao;
    int requestCodefolder = 123;
    ImageView imgAvatarInDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        recyclerView = findViewById(R.id.id_qly_product);
        fab = findViewById(R.id.id_fab_add_product);
        recyclerView.setHasFixedSize(true);

        adapterProduct = new ProductAdapter(ProductActivity.this, dao);
        dao = new ProductDAO(this);

        arrayList = dao.selectAll();
        adapterProduct.setData(arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterProduct);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ProductActivity.this, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert);
                dialog.setContentView(R.layout.dialog_add_product);

                TextInputEditText ed_name = dialog.findViewById(R.id.ed_add_nameProduct);
                TextInputEditText ed_price = dialog.findViewById(R.id.ed_add_priceProduct);
                TextInputEditText ed_stock = dialog.findViewById(R.id.ed_add_stockProduct);
                TextInputEditText ed_sold = dialog.findViewById(R.id.ed_add_soldProduct);
                TextInputEditText ed_describe = dialog.findViewById(R.id.ed_add_describeProduct);
                ImageView img_add = dialog.findViewById(R.id.add_img_product);
                ImageView img_avatar = dialog.findViewById(R.id.img_up_avatar_product);
                Button btnSave = dialog.findViewById(R.id.btnSaveAddProduct);

                final Spinner spinner = dialog.findViewById(R.id.spin_category);
                CategoryDAO categoryDAO = new CategoryDAO(ProductActivity.this);

                SpinCategoryAdapter adapter = new SpinCategoryAdapter(categoryDAO.selectAll());
                spinner.setAdapter(adapter);


                img_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, requestCodefolder);
                    }
                });
                imgAvatarInDialog = img_avatar;
                dialog.show();

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = ed_name.getText().toString().trim();
                        String describe = ed_describe.getText().toString().trim();
                        String price = ed_price.getText().toString().trim();
                        String stock = ed_stock.getText().toString().trim();
                        String sold = ed_sold.getText().toString().trim();

                        if ( name.isEmpty()){
                            Toast.makeText(ProductActivity.this, "Vui lòng nhập tên sản phẩm!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if ( describe.isEmpty()){
                            Toast.makeText(ProductActivity.this, "Vui lòng nhập mô tả sản phẩm!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if ( price.isEmpty()){
                            Toast.makeText(ProductActivity.this, "Vui lòng nhập giá sản phẩm!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if ( sold.isEmpty()){
                            Toast.makeText(ProductActivity.this, "Vui lòng nhập số lượng sản phẩm đã bán!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if ( stock.isEmpty()){
                            Toast.makeText(ProductActivity.this, "Vui lòng nhập số lượng sản phẩm còn trong kho!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Date currentDate = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String date = dateFormat.format(currentDate);

                        Bitmap bitmap = ((BitmapDrawable) img_avatar.getDrawable()).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] avatar = stream.toByteArray();

                        Category objCtegory = (Category) spinner.getSelectedItem();
                        int category_id = objCtegory.getId();

                        Product obj = new Product();
                        obj.setStock(Integer.parseInt(stock));
                        obj.setSold(Integer.parseInt(sold));
                        obj.setPrice(Integer.parseInt(price));
                        obj.setName(name);
                        obj.setDescribe(describe);
                        obj.setImport_date(date);
                        obj.setCategory_id(category_id);
                        obj.setAvatar(avatar);

                        long  rowId = dao.insert(obj);
                        if (rowId > 0) {
                            Toast.makeText(ProductActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProductActivity.this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                        arrayList.clear();
                        arrayList.addAll(dao.selectAll());

                        adapterProduct.notifyDataSetChanged();
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == requestCodefolder && resultCode == RESULT_OK && data != null){
            Uri imageUri = data.getData();
            Log.d("zzzz", "onActivityResult: da chon anh");
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAvatarInDialog.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}