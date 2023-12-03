package tranghtmph26263.fpoly.ungdungbanxedap.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.BillDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.CartDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.ProductDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.UserDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Bill;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.CartDetail;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.User;
import tranghtmph26263.fpoly.ungdungbanxedap.myInterface.ClickItemBillListener;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewholder>{
    private Context context;
    private ArrayList<Bill> arrayList;
    BillDAO dao ;
    UserDAO userDAO;
    private ClickItemBillListener listener;

    public BillAdapter(Context context, BillDAO dao, ClickItemBillListener listener) {
        this.context = context;
        this.dao = dao;
        this.listener = listener;
    }
    public void setData(ArrayList<Bill> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BillViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_bill_admin, parent, false );
        return new BillViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewholder holder, int position) {
        dao = new BillDAO(context);
        userDAO = new UserDAO(context);

        Bill obj = arrayList.get(position);
        if ( obj == null){
            Log.d("aaaa", "onBindViewHolder: obj null");
            return;
        }
        User objUser = userDAO.selectOne(obj.getUser_id());
        holder.tv_price.setText("Tổng tiền: "+obj.getReal_price());
        if ( obj.getStatus() == 0){
            holder.tv_status.setText("Trạng thái: Đang chờ xác nhận");
        }else if ( obj.getStatus() == 1){
            holder.tv_status.setText("Trạng thái: Đã xác nhận");
        }else if ( obj.getStatus() == 2){
            holder.tv_status.setText("Trạng thái: Đã từ chối");
        }else if ( obj.getStatus() == 3){
            holder.tv_status.setText("Trạng thái: Đang giao");
        }else if ( obj.getStatus() == 4){
            holder.tv_status.setText("Trạng thái: Đã giao thành công");
        }

        holder.tv_fullname.setText(objUser.getFullname());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickItemBIll(obj);
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

    public class BillViewholder extends RecyclerView.ViewHolder{
        TextView tv_fullname, tv_price, tv_status;
        LinearLayout linearLayout;

        public BillViewholder(@NonNull View itemView) {
            super(itemView);
            tv_fullname = itemView.findViewById(R.id.id_nameUser_billAdmin);
            tv_price = itemView.findViewById(R.id.id_price_billAdmin);
            tv_status = itemView.findViewById(R.id.id_status_billAdmin);
            linearLayout = itemView.findViewById(R.id.lv_list_bill);
        }
    }

}
