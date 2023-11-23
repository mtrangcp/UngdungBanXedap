package tranghtmph26263.fpoly.ungdungbanxedap.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.SpinCategoryAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.CategoryDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.ProductDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Category;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Product;

public class EditProductActivity extends AppCompatActivity {
    int id, price, stock, sold,categoryId;
    String name, describe, importDate;
    byte[] img;
    int requestCodefolder = 123;
    ImageView imgAvatarInDialog;
    ProductDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        dao = new ProductDAO(this);
        Intent intent = getIntent();
        if ( intent != null){
            id = intent.getIntExtra("id", 0);
            name = intent.getStringExtra("name");
            img = intent.getByteArrayExtra("image");
            price = intent.getIntExtra("price", 0);
            describe = intent.getStringExtra("describe");
            stock = intent.getIntExtra("stock", 0);
            importDate = intent.getStringExtra("importDate");
            sold = intent.getIntExtra("sold", 0);
            categoryId = intent.getIntExtra("categoryId", 0);
        }else{
            Toast.makeText(this, "Khong co du lieu ve obj", Toast.LENGTH_SHORT).show();
        }

        TextInputEditText ed_name = findViewById(R.id.ed_edit_nameProduct);
        TextInputEditText ed_price = findViewById(R.id.ed_edit_priceProduct);
        TextInputEditText ed_stock = findViewById(R.id.ed_edit_stockProduct);
        TextInputEditText ed_sold = findViewById(R.id.ed_edit_soldProduct);
        TextInputEditText ed_describe = findViewById(R.id.ed_edit_describeProduct);
        ImageView img_edit = findViewById(R.id.edit_img_product);
        ImageView img_avatar = findViewById(R.id.img_upEdit_avatar_product);
        Button btnSave = findViewById(R.id.btnSaveEditProduct);

        ed_name.setText(name);
        ed_price.setText(price+"");
        ed_stock.setText(stock+"");
        ed_sold.setText(sold+"");
        ed_describe.setText(describe);
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        img_avatar.setImageBitmap(bitmap);

        final Spinner spinner = findViewById(R.id.spin_edit_category);
        CategoryDAO categoryDAO = new CategoryDAO(EditProductActivity.this);
        ArrayList<Category> list = categoryDAO.selectAllForUser();
        SpinCategoryAdapter adapter = new SpinCategoryAdapter(list);
        spinner.setAdapter(adapter);

        for ( int j = 0; j< list.size(); j++){
            Category tmp = list.get(j);
            if ( tmp.getId() == categoryId){
                spinner.setSelection(j);
                spinner.setSelected(true);
            }
        }

        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, requestCodefolder);
            }
        });
        imgAvatarInDialog = img_avatar;

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newname = ed_name.getText().toString().trim();
                String describe = ed_describe.getText().toString().trim();
                String price = ed_price.getText().toString().trim();
                String stock = ed_stock.getText().toString().trim();
                String sold = ed_sold.getText().toString().trim();

                if ( newname.isEmpty()){
                    Toast.makeText(EditProductActivity.this, "Vui lòng nhập tên sản phẩm!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( describe.isEmpty()){
                    Toast.makeText(EditProductActivity.this, "Vui lòng nhập mô tả sản phẩm!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( price.isEmpty()){
                    Toast.makeText(EditProductActivity.this, "Vui lòng nhập giá sản phẩm!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( sold.isEmpty()){
                    Toast.makeText(EditProductActivity.this, "Vui lòng nhập số lượng sản phẩm đã bán!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( stock.isEmpty()){
                    Toast.makeText(EditProductActivity.this, "Vui lòng nhập số lượng sản phẩm còn trong kho!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Bitmap bitmap = ((BitmapDrawable) img_avatar.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] avatar = stream.toByteArray();

                Category objCtegory = (Category) spinner.getSelectedItem();
                int category_id = objCtegory.getId();

                Product obj = new Product();
                obj.setStock(Integer.parseInt(ed_stock.getText().toString().trim()));
                obj.setSold(Integer.parseInt(ed_sold.getText().toString().trim()));
                obj.setPrice(Integer.parseInt( ed_price.getText().toString().trim()));
                obj.setName(newname);
                obj.setDescribe(describe);
                obj.setImport_date(importDate);
                obj.setCategory_id(category_id);
                obj.setAvatar(avatar);

                int res = dao.updateRow(obj, name);
                Log.d("ccccc", "ket qua update: "+res);
                if ( res > 0){
                    startActivity(new Intent(EditProductActivity.this, ProductActivity.class));
                    Toast.makeText(EditProductActivity.this, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(EditProductActivity.this, "Sửa thất bại!", Toast.LENGTH_SHORT).show();
                }
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