package tranghtmph26263.fpoly.ungdungbanxedap.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.CartDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.UserDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.CartDetail;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Product;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.User;

public class ProductDetailActivity extends AppCompatActivity {
    String TAG = "aaaa";
    ImageView img_avatar, imgCart;
    TextView tv_name, tv_price, tv_sold, tv_stock, tv_describe;
    Button btn_addToCart;
    EditText ed_soLuong;
    UserDAO userDAO;
    CartDAO cartDAO;
    int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        img_avatar = findViewById(R.id.id_imgAvater_detail);
        tv_name = findViewById(R.id.id_namePro_detail);
        tv_price = findViewById(R.id.id_pricePro_detail);
        tv_sold = findViewById(R.id.id_soldPro_detail);
        tv_stock = findViewById(R.id.id_stockPro_detail);
        tv_describe = findViewById(R.id.id_describePro_detail);
        btn_addToCart = findViewById(R.id.btn_addProToCart);
        ed_soLuong = findViewById(R.id.ed_soLuongSp);
        imgCart = findViewById(R.id.id_cartDetail);
        userDAO = new UserDAO(ProductDetailActivity.this);
        cartDAO = new CartDAO(ProductDetailActivity.this);

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDetailActivity.this, CartActivity.class));
            }
        });

        Bundle bundle = getIntent().getExtras();
        if ( bundle == null){  return; }
        Product obj  = (Product) bundle.get("objProduct");
        Log.d(TAG, "objProduct: "+obj.toString());

        tv_name.setText(obj.getName());
        tv_price.setText("Giá: "+ obj.getPrice());
        tv_sold.setText("Đã bán: "+ obj.getSold());
        tv_stock.setText("Số lượng kho: "+ obj.getStock());
        tv_describe.setText("Mô tả: "+ obj.getDescribe());

        byte[] avatar = obj.getAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
        img_avatar.setImageBitmap(bitmap);

        btn_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_soLuong.getText().toString().trim().isEmpty() ){
                    Toast.makeText(ProductDetailActivity.this, "Vui lòng nhập số lượng!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( Integer.parseInt(ed_soLuong.getText().toString().trim()) > obj.getStock()){
                    Toast.makeText(ProductDetailActivity.this, "Số lượng bạn cần lớn hơn số hàng trong kho!", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences sharedPreferences = getSharedPreferences("USER_INFO",MODE_PRIVATE );
                String user = sharedPreferences.getString("USERNAME", "");
                if ( user.equals("")){
                    Log.d(TAG, "Thong tin user da luu: k lay dc");
                }else{
                    User objUser = new User();
                    objUser = userDAO.selectOneWithUsername(user);
                    idUser = objUser.getId();
                }

                CartDetail objCart = new CartDetail();
                objCart.setUser_id(idUser);
                objCart.setProduct_id(obj.getId());
                objCart.setPrice(obj.getPrice());
                objCart.setQuantity(Integer.parseInt(ed_soLuong.getText().toString().trim()));
                Log.d(TAG, "obj trc insert: "+ objCart.toString());

                long res = cartDAO.insertNew(objCart);
                if ( res > 0){
                    Toast.makeText(ProductDetailActivity.this, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(ProductDetailActivity.this, "Thêm vào giỏ hàng thất bại!", Toast.LENGTH_SHORT).show();
                }

                Log.d(TAG, "array gio hang: "+ cartDAO.selectAll().toString());

            }
        });
    }
}