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
import tranghtmph26263.fpoly.ungdungbanxedap.dao.ProductDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Product;
import tranghtmph26263.fpoly.ungdungbanxedap.myInterface.ClickItemProductListener;

public class TopBanChayAdapter extends RecyclerView.Adapter<TopBanChayAdapter.TopBanChayViewHolder>{
    private Context context;
    private ArrayList<Product> arrayList;
    ProductDAO dao;
    private ClickItemProductListener listener;

    public TopBanChayAdapter( Context context, ProductDAO dao){
        this.dao = dao;
        this.context = context;
    }

    public void setData(ArrayList<Product> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopBanChayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_top_ban_chay, parent, false);
        return new TopBanChayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopBanChayViewHolder holder, int position) {
        Product obj = arrayList.get(position);
        int index = position;
        byte[] avatar = obj.getAvatar();

        if ( obj == null){
            return;
        }
        holder.name.setText(obj.getName());
        holder.top.setText("Đã bán: "+obj.getSold());
        if ( avatar != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
            holder.img_avatar.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        if ( arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    public class TopBanChayViewHolder extends RecyclerView.ViewHolder{
        ImageView img_avatar;
        TextView name, top;

        public TopBanChayViewHolder(@NonNull View itemView) {
            super(itemView);
            img_avatar = itemView.findViewById(R.id.img_avatarPro_top);
            name = itemView.findViewById(R.id.id_namePro_top);
            top = itemView.findViewById(R.id.id_soldPro_top);
        }
    }
}
