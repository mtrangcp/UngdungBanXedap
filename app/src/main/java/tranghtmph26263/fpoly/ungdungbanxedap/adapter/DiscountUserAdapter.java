package tranghtmph26263.fpoly.ungdungbanxedap.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.DiscountDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.DiscountUserDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.UserDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Discount;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.DiscountUser;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.User;

public class DiscountUserAdapter extends RecyclerView.Adapter<DiscountUserAdapter.DiscountUserViewholder>{
    private Context context;
    private ArrayList<Discount> arrayList;
    DiscountDAO dao;
    DiscountUserDAO discountUserDAO;
    UserDAO userDAO;
    String TAG = "aaaa";

    public DiscountUserAdapter(Context context, DiscountDAO dao) {
        this.context = context;
        this.dao = dao;
    }

    public void setData(ArrayList<Discount> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DiscountUserViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_discount_user, parent, false);
        return new DiscountUserViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountUserViewholder holder, int position) {
        dao = new DiscountDAO(context);
        userDAO = new UserDAO(context);
        discountUserDAO = new DiscountUserDAO(context);
        int index = position;
        Discount obj = arrayList.get(index);
        if ( obj == null){
            return;
        }
        holder.tvName.setText(obj.getCode_name());
        holder.tvValue.setText("Giá trị: "+formatCurrency(obj.getValue()));
        holder.tvDetail.setText("Mô tả: "+obj.getDetail());
        holder.btnLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( holder.btnLay.getText().equals("Lấy")){
                    SharedPreferences sharedPreferences = context.getSharedPreferences("USER_INFO", MODE_PRIVATE);
                    String user = sharedPreferences.getString("USERNAME", "admin");
                    User objUser = userDAO.selectOneWithUsername(user);

                    DiscountUser objDiscountUser = new DiscountUser();
                    objDiscountUser.setDiscount_id(obj.getId());
                    objDiscountUser.setUser_id(objUser.getId());
                    objDiscountUser.setStatus(0);

                    long res = discountUserDAO.insertNew(objDiscountUser);
                    if ( res > 0){
                        Toast.makeText(context, "Lấy thành công!", Toast.LENGTH_SHORT).show();
                        holder.btnLay.setEnabled(false);
                    }else{
                        Toast.makeText(context, "Lấy thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context, "Bạn đã lấy phiếu giảm giá này!", Toast.LENGTH_SHORT).show();
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

    public class DiscountUserViewholder extends RecyclerView.ViewHolder {
        TextView tvName, tvValue, tvDetail;
        Button btnLay;
        public DiscountUserViewholder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.id_name_discount_user);
            tvDetail = itemView.findViewById(R.id.id_detail_discount_user);
            tvValue = itemView.findViewById(R.id.id_value_discount_user);
            btnLay=  itemView.findViewById(R.id.btn_lay_discount);
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
