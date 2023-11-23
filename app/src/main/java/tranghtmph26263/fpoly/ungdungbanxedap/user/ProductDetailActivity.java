package tranghtmph26263.fpoly.ungdungbanxedap.user;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Product;

public class ProductDetailActivity extends AppCompatActivity {
    ImageView img_avatar;
    TextView tv_name, tv_price, tv_sold, tv_describe;
    Button btn_addToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        img_avatar = findViewById(R.id.id_imgAvater_detail);
        tv_name = findViewById(R.id.id_namePro_detail);
        tv_price = findViewById(R.id.id_pricePro_detail);
        tv_sold = findViewById(R.id.id_soldPro_detail);
        tv_describe = findViewById(R.id.id_describePro_detail);
        btn_addToCart = findViewById(R.id.btn_addProToCart);

        Bundle bundle = getIntent().getExtras();
        if ( bundle == null){  return; }
        Product obj  = (Product) bundle.get("objProduct");

        tv_name.setText(obj.getName());
        tv_price.setText("Giá: "+ obj.getPrice());
        tv_sold.setText("Đã bán: "+ obj.getSold());
        tv_describe.setText("Mô tả: "+ obj.getDescribe());

        byte[] avatar = obj.getAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
        img_avatar.setImageBitmap(bitmap);

        btn_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}