package tranghtmph26263.fpoly.ungdungbanxedap.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.admin.EditProductActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.admin.ProductActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.CategoryDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.ProductDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Category;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{
    private Context context;
    private ArrayList<Product> arrayList;
    ProductDAO dao;

    public ProductAdapter( Context context, ProductDAO dao){
        this.dao = dao;
        this.context = context;
    }

    public void setData(ArrayList<Product> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        dao = new ProductDAO(context);
        Product obj = arrayList.get(position);
        int index = position;
        byte[] avatar = obj.getAvatar();

        if ( obj == null){
            return;
        }
        holder.name.setText(obj.getName());
        holder.price.setText("Giá: "+obj.getPrice());
        holder.describe.setText("Mô tả:"+obj.getDescribe());
        holder.stock.setText("SL kho:"+obj.getStock());
        holder.sold.setText("Đã bán:"+obj.getSold());
        holder.importDate.setText("Ngày nhập:"+obj.getImport_date());

        if ( avatar != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
            holder.img.setImageBitmap(bitmap);
        }

        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(view.getContext(), EditProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", obj.getId());
                bundle.putString("name", obj.getName());
                bundle.putByteArray("image", obj.getAvatar());
                bundle.putInt("price", obj.getPrice());
                bundle.putString("describe", obj.getDescribe());
                bundle.putInt("stock", obj.getStock());
                bundle.putString("importDate", obj.getImport_date());
                bundle.putInt("sold", obj.getSold());
                bundle.putInt("categoryId", obj.getCategory_id());

                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });

        holder.img_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa sản phầm?");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setMessage("Có chắc chắn xóa: "+ obj.getName()+ "?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int res = dao.softDelete(obj);
                        if ( res > 0){
                            arrayList.get(index).setStock(0);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                        }
                        dialogInterface.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
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

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView img, img_edit, img_del;
        TextView name, price, stock, sold, importDate, describe;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.id_img_qly_product);
            name = itemView.findViewById(R.id.id_name_qly_product);
            price = itemView.findViewById(R.id.id_price_qly_product);
            stock = itemView.findViewById(R.id.id_stock_qly_product);
            sold = itemView.findViewById(R.id.id_sold_qly_product);
            importDate = itemView.findViewById(R.id.id_import_product);
            describe = itemView.findViewById(R.id.id_describe_qly_product);

            img_edit = itemView.findViewById(R.id.id_edit_product);
            img_del = itemView.findViewById(R.id.id_del_product);
        }
    }
}
