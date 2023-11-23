package tranghtmph26263.fpoly.ungdungbanxedap.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.ProductDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Product;
import tranghtmph26263.fpoly.ungdungbanxedap.myInterface.ClickItemProductListener;

public class ProductHorizontalAdapter extends RecyclerView.Adapter<ProductHorizontalAdapter.ProductHorizontalViewHolder>{
    private Context context;
    private ArrayList<Product> arrayList;
    ProductDAO dao;
    private ClickItemProductListener listener;

    public ProductHorizontalAdapter( Context context, ProductDAO dao, ClickItemProductListener listener){
        this.dao = dao;
        this.context = context;
        this.listener = listener;
    }

    public void setData(ArrayList<Product> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_horizontal, parent, false);
        return new ProductHorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHorizontalViewHolder holder, int position) {
        Product obj = arrayList.get(position);
        int index = position;
        byte[] avatar = obj.getAvatar();

        if ( obj == null){
            return;
        }
        holder.name.setText(obj.getName());
        if ( avatar != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
            holder.img_avatar.setImageBitmap(bitmap);
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickItemProduct(obj);
            }
        });
    }

    @Override
    public int getItemCount() {
        if ( arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    public class ProductHorizontalViewHolder extends RecyclerView.ViewHolder{
        ImageView img_avatar;
        TextView name;
        LinearLayout linearLayout;

        public ProductHorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            img_avatar = itemView.findViewById(R.id.img_avatarPro_horizontal);
            name = itemView.findViewById(R.id.id_namePro_horizontal);
            linearLayout = itemView.findViewById(R.id.list_ngang);
        }
    }
}
