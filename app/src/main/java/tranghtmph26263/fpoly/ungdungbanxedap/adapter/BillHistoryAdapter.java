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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.BillDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.UserDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Bill;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.User;
import tranghtmph26263.fpoly.ungdungbanxedap.myInterface.ClickItemBillListener;

public class BillHistoryAdapter extends RecyclerView.Adapter<BillHistoryAdapter.BillHistoryViewholder>{
    private Context context;
    private ArrayList<Bill> arrayList;
    BillDAO dao ;
    UserDAO userDAO;
    private ClickItemBillListener listener;

    public BillHistoryAdapter(Context context, BillDAO dao, ClickItemBillListener listener) {
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
    public BillHistoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_bill_admin, parent, false );
        return new BillHistoryViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillHistoryViewholder holder, int position) {
        dao = new BillDAO(context);
        userDAO = new UserDAO(context);

        Bill obj = arrayList.get(position);
        if ( obj == null){
            Log.d("aaaa", "onBindViewHolder: obj null");
            return;
        }
        User objUser = userDAO.selectOne(obj.getUser_id());
        holder.tv_price.setText("Tổng tiền: "+formatCurrency(obj.getReal_price()));
        if ( obj.getStatus() == 0){
            holder.tv_status.setText("Trạng thái: Đang chờ xác nhận");
        }else if ( obj.getStatus() == 1){
            holder.tv_status.setText("Trạng thái: Đã xác nhận");
        }else if ( obj.getStatus() == 2){
            holder.tv_status.setText("Trạng thái: Đã bị hủy");
        }else if ( obj.getStatus() == 3){
            holder.tv_status.setText("Trạng thái: Đang giao");
        }else if ( obj.getStatus() == 4){
            holder.tv_status.setText("Trạng thái: Đã giao thành công");
        }

        holder.tv_fullname.setText(obj.getUser_fullname());
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

    public class BillHistoryViewholder extends RecyclerView.ViewHolder{
        TextView tv_fullname, tv_price, tv_status;
        LinearLayout linearLayout;

        public BillHistoryViewholder(@NonNull View itemView) {
            super(itemView);
            tv_fullname = itemView.findViewById(R.id.id_nameUser_billAdmin);
            tv_price = itemView.findViewById(R.id.id_price_billAdmin);
            tv_status = itemView.findViewById(R.id.id_status_billAdmin);
            linearLayout = itemView.findViewById(R.id.lv_list_bill);
        }
    }

    private static  String formatCurrency(int amount) {
        // Locale cho tiếng Việt và định dạng tiền tệ
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeVN);

        // Đặt loại tiền tệ là đồng
        currencyFormatter.setCurrency(Currency.getInstance("VND"));

        // Định dạng số
        return currencyFormatter.format(amount);
    }
}
