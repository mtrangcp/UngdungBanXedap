package tranghtmph26263.fpoly.ungdungbanxedap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.UserDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Category;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    private Context context;
    private ArrayList<User> arrayList;
    UserDAO dao;

    public UserAdapter( Context context, UserDAO dao){
        this.context = context;
        this.dao = dao;
    }

    public void setData(ArrayList<User> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        dao = new UserDAO(context);
        User obj = arrayList.get(position);
        int index = position;

        if ( obj == null){
            return;
        }
        holder.tv_id.setText("ID: "+obj.getId());
        holder.tv_fullName.setText(obj.getFullname());
        holder.tv_userName.setText("Username: "+obj.getUsername());
        holder.tv_phone.setText("Phone: "+obj.getPhone());

        if ( obj.getActive() == 1){
            holder.btn_ban.setText("Ban");
        }else{
            holder.btn_ban.setText("Unban");
        }

        holder.btn_ban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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


    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView tv_fullName, tv_userName, tv_id, tv_phone;
        Button btn_ban;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.id_user);
            tv_userName = itemView.findViewById(R.id.id_username);
            tv_phone = itemView.findViewById(R.id.id_phone);
            tv_fullName = itemView.findViewById(R.id.id_fullname);
            btn_ban = itemView.findViewById(R.id.btn_ban_user);
        }
    }
}
