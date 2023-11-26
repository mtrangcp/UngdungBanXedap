package tranghtmph26263.fpoly.ungdungbanxedap.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.ArrayList;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.DiscountDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Category;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Discount;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.DiscountViewholder>{
    private Context context;
    private ArrayList<Discount> arrayList;
    DiscountDAO dao;

    public DiscountAdapter(Context context, DiscountDAO dao) {
        this.context = context;
        this.dao = dao;
    }

    public void setData(ArrayList<Discount> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DiscountViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_discount, parent, false);
        return new DiscountViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountViewholder holder, int position) {
        dao = new DiscountDAO(context);
        int index = position;
        Discount obj = arrayList.get(index);
        if ( obj == null){
            return;
        }
        holder.tvName.setText(obj.getCode_name());
        holder.tvValue.setText("Giá trị: "+obj.getValue());
        holder.tvStartDate.setText("Bắt đầu: "+obj.getStart_date());
        holder.tvExpirationDate.setText("Hết hạn: "+obj.getExpiration_date());
        holder.tvDetail.setText("Mô tả: "+obj.getDetail());

        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEdit(obj, index, context);
            }
        });
        holder.img_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa phiếu giảm giá?");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setMessage("Có chắc chắn xóa: "+ obj.getCode_name()+ "?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int result = dao.deleteRow(obj);
                        if ( result> 0){
                            arrayList.remove(index);
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

    public void  showDialogEdit(Discount obj, int index, Context context){
        final Dialog dialog = new Dialog(context, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert);
        dialog.setContentView(R.layout.dialog_edit_discount);

        TextInputEditText ed_name = dialog.findViewById(R.id.ed_edit_codename);
        TextInputEditText ed_value = dialog.findViewById(R.id.ed_edit_Value);
        TextInputEditText ed_startDate = dialog.findViewById(R.id.ed_edit_startDate);
        TextInputEditText ed_expirationDate = dialog.findViewById(R.id.ed_edit_expirationDate);
        TextInputEditText ed_detail = dialog.findViewById(R.id.ed_edit_detail);

        ed_name.setText(obj.getCode_name());
        ed_startDate.setText(obj.getStart_date());
        ed_expirationDate.setText(obj.getExpiration_date());
        ed_detail.setText(obj.getDetail());
        ed_value.setText(""+obj.getValue());

        Button btnSave = dialog.findViewById(R.id.btnSaveEditDiscount);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( ed_name.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Vui lòng nhập tên của phiếu giảm giá!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( ed_value.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Vui lòng nhập giá trị phiếu giảm giá!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( ed_startDate.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Vui lòng nhập ngày bắt đầu!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( ed_expirationDate.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Vui lòng nhập ngày hết hạn!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( ed_detail.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Vui lòng nhập mô tả!", Toast.LENGTH_SHORT).show();
                    return;
                }

                obj.setCode_name(ed_name.getText().toString().trim());
                obj.setDetail(ed_detail.getText().toString().trim());
                obj.setStart_date(ed_startDate.getText().toString().trim());
                obj.setExpiration_date(ed_expirationDate.getText().toString().trim());
                obj.setValue(Integer.parseInt(ed_value.getText().toString().trim()));

                Log.d("zzzzz", "onClick: "+ obj.getCode_name());
                int res = dao.updateRow(obj);
                Log.d("zzzzz", "onClick: "+ res);
                if  ( res > 0){
                    arrayList.set(index, obj);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Thay đổi thành công!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Thay đổi thất bại!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showDialogAdd( Context context){
        final Dialog dialog = new Dialog(context, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert);
        dialog.setContentView(R.layout.dialog_add_discount);

        TextInputEditText ed_name = dialog.findViewById(R.id.ed_add_codename);
        TextInputEditText ed_value = dialog.findViewById(R.id.ed_add_Value);
        TextInputEditText ed_startDate = dialog.findViewById(R.id.ed_add_startDate);
        TextInputEditText ed_expirationDate = dialog.findViewById(R.id.ed_add_expirationDate);
        TextInputEditText ed_detail = dialog.findViewById(R.id.ed_add_detail);
        Button btnSave = dialog.findViewById(R.id.btnSaveAddDiscount);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( ed_name.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Vui lòng nhập tên của phiếu giảm giá!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( ed_value.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Vui lòng nhập giá trị phiếu giảm giá!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( ed_startDate.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Vui lòng nhập ngày bắt đầu!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( ed_expirationDate.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Vui lòng nhập ngày hết hạn!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( ed_detail.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Vui lòng nhập mô tả!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Discount obj = new Discount();
                obj.setCode_name(ed_name.getText().toString().trim());
                obj.setValue(Integer.parseInt(ed_value.getText().toString().trim()));
                obj.setDetail(ed_detail.getText().toString().trim());
                obj.setStart_date(ed_startDate.getText().toString().trim());
                obj.setExpiration_date(ed_expirationDate.getText().toString().trim());

                long res = dao.insertNew(obj);
                if ( res > 0){
                    arrayList.clear();
                    arrayList.addAll(dao.selectAll());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Thêm mới thành công!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Thêm mới thất bại!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public int getItemCount() {
        if ( arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    public class DiscountViewholder extends RecyclerView.ViewHolder{
        private TextView tvName, tvDetail, tvStartDate, tvExpirationDate, tvValue;
        ImageView img_edit, img_del;

        public DiscountViewholder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.id_name_discount);
            tvDetail = itemView.findViewById(R.id.id_detail_discount);
            tvStartDate = itemView.findViewById(R.id.id_startDate_discount);
            tvExpirationDate = itemView.findViewById(R.id.id_expirationDate_discount);
            tvValue = itemView.findViewById(R.id.id_value_discount);

            img_edit = itemView.findViewById(R.id.edit_discount);
            img_del = itemView.findViewById(R.id.del_discount);

        }
    }


}
