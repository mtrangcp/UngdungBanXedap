package tranghtmph26263.fpoly.ungdungbanxedap.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.CommentDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.UserDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Comment;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.User;

public class CommentUserAdapter extends RecyclerView.Adapter<CommentUserAdapter.CommentUserViewHolder>{
    private Context context;
    ArrayList<Comment> arrayList ;
    CommentDAO dao;
    UserDAO userDAO;
    User objUserShare = null;

    public CommentUserAdapter(Context context, CommentDAO dao) {
        this.context = context;
        this.dao = dao;
    }

    public void setData(ArrayList<Comment> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_comment_pro, parent, false );
        return new CommentUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentUserViewHolder holder, int position) {
        dao = new CommentDAO(context);
        userDAO = new UserDAO(context);

        Comment obj = arrayList.get(position);
        User objUser = userDAO.selectOne(obj.getUser_id());

        holder.tvName.setText(objUser.getUsername());
        holder.tvNoidung.setText(obj.getContent());
        holder.tvTime.setText(obj.getTime());

        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
                String username = preferences.getString("USERNAME", "");
                objUserShare = userDAO.selectOneWithUsername(username);

                if ( objUserShare.getId() == obj.getUser_id() ){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Xóa thể loại?");
                    builder.setIcon(android.R.drawable.ic_delete);
                    builder.setMessage("Có chắc chắn xóa?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int res = dao.deleteRow(obj);
                            if ( res > 0){
                                arrayList.clear();
                                arrayList.addAll(dao.selectAllWithProsuctId(obj.getProduct_id()));
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa bình luận thành công!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Xóa bình luận thất bại!", Toast.LENGTH_SHORT).show();
                            }
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
                }else{
                    Toast.makeText(context, "Bạn không có quyền xóa bình luận này!", Toast.LENGTH_SHORT).show();
                }
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

    public class CommentUserViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvNoidung, tvTime;
        ImageView imgEdit, imgDel;

        public CommentUserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoidung = itemView.findViewById(R.id.id_content_comment);
            tvName = itemView.findViewById(R.id.id_name_user_comment);
            tvTime = itemView.findViewById(R.id.id_time_comment);
            imgEdit = itemView.findViewById(R.id.img_edit_comment);
            imgDel = itemView.findViewById(R.id.img_del_comment);
        }
    }
}
