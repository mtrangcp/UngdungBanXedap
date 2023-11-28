package tranghtmph26263.fpoly.ungdungbanxedap.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.CartDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.ProductDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.CartDetail;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Product;

public class ProductBillAdapter extends RecyclerView.Adapter<ProductBillAdapter.ProductBillHolder>{
    private Context context;
    private ArrayList<CartDetail> arrayList;
    CartDAO dao ;
    ProductDAO productDAO;

    public ProductBillAdapter(Context context, CartDAO dao) {
        this.context = context;
        this.dao = dao;
    }

    public void setData(ArrayList<CartDetail> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductBillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_pro_bill, parent, false );
        return new ProductBillHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductBillHolder holder, int position) {
        dao = new CartDAO(context);
        productDAO = new ProductDAO(context);
        CartDetail obj = arrayList.get(position);
        int index = position;

        if ( obj == null){
            return;
        }
        Product objProduct = productDAO.selectOne(obj.getProduct_id());
        byte[] avatar = objProduct.getAvatar();
        if ( avatar != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
            holder.img_avatar.setImageBitmap(bitmap);
        }

        holder.tvName.setText(objProduct.getName());
        holder.tvPrice.setText("Giá: "+obj.getPrice());
        holder.tvQuantity.setText("Số lượng: "+obj.getQuantity());

    }

    @Override
    public int getItemCount() {
        if ( arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    public class ProductBillHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvPrice, tvQuantity;
        private ImageView img_avatar;

        public ProductBillHolder(@NonNull View itemView) {
            super(itemView);
            tvPrice = itemView.findViewById(R.id.id_pricePro_bill);
            tvName = itemView.findViewById(R.id.id_namePro_bill);
            tvQuantity = itemView.findViewById(R.id.id_quantityPro_bill);
            img_avatar = itemView.findViewById(R.id.id_avatarPro_bill);
        }
    }
}
