package tranghtmph26263.fpoly.ungdungbanxedap.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.CartDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.CategoryDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.CommentDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.ProductDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.UserDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.CartDetail;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Category;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Comment;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Product;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.User;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{
    private Context context;
    private ArrayList<CartDetail> arrayList;
    CommentDAO dao ;
    CartDAO cartDAO;
    UserDAO userDAO;
    ProductDAO productDAO;

    public CommentAdapter(Context context, CommentDAO dao) {
        this.context = context;
        this.dao = dao;
    }

    public void setData(ArrayList<CartDetail> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_comment, parent, false );
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        dao = new CommentDAO(context);
        cartDAO = new CartDAO(context);
        productDAO = new ProductDAO(context);
        userDAO = new UserDAO(context);
        CartDetail obj = arrayList.get(position);
        int index = position;

        if ( obj == null){
            return;
        }
        Product objProduct = productDAO.selectOne(obj.getProduct_id());
        byte[] avatar = objProduct.getAvatar();
        if ( avatar != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
            holder.img_anh.setImageBitmap(bitmap);
        }

        holder.tvName.setText(objProduct.getName());
        holder.btn_gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( holder.ed_noidung.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Bạn chưa nhập nội dung!", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences preferences = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
                String username = preferences.getString("USERNAME", "");
                User objUser = userDAO.selectOneWithUsername(username);

                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String date = dateFormat.format(currentDate);

                Comment objComment = new Comment();
                objComment.setUser_id(objUser.getId());
                objComment.setProduct_id(objProduct.getId());
                objComment.setContent(holder.ed_noidung.getText().toString().trim());
                objComment.setTime(date);

                long res = dao.insertNew(objComment);
                if ( res > 0){
                    Toast.makeText(context, "Gửi đánh giá thành công!", Toast.LENGTH_SHORT).show();
                    holder.ed_noidung.setText("");
                    Log.d("mmmm", "list danh gia: "+ dao.selectAll().toString());
                }else{
                    Toast.makeText(context, "Gửi đánh giá thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Log.d("mmmm", "list danh gia: "+ dao.selectAll().toString());
    }

    @Override
    public int getItemCount() {
        if ( arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private ImageView  img_anh;
        TextInputEditText ed_noidung;
        Button btn_gui;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            ed_noidung = itemView.findViewById(R.id.ed_noidung_commnent);
            tvName = itemView.findViewById(R.id.tv_tenSp_comment);
            img_anh = itemView.findViewById(R.id.img_anh_comment);
            btn_gui = itemView.findViewById(R.id.btn_add_comment);
        }
    }
}
