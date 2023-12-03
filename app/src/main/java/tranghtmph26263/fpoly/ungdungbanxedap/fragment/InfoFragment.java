package tranghtmph26263.fpoly.ungdungbanxedap.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.BillAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.BillHistoryAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.admin.BillActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.admin.BillDetailActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.BillDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.UserDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.Bill;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.User;
import tranghtmph26263.fpoly.ungdungbanxedap.myInterface.ClickItemBillListener;
import tranghtmph26263.fpoly.ungdungbanxedap.user.HistoryBillDetailActivity;

public class InfoFragment extends Fragment {
    UserDAO userDAO;
    RecyclerView recyclerView;
    BillHistoryAdapter adapter;
    TextView tv_username, tv_fullname, tv_phone;

    BillDAO billDAO;
    ArrayList<Bill> arrayList = new ArrayList<Bill>();
    User objUser = null;

    public InfoFragment() {
    }

    public static InfoFragment newInstance() {
        InfoFragment fragment = new InfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView img_edit = view.findViewById(R.id.img_edit_user);

        tv_username = view.findViewById(R.id.tv_username_info);
        tv_fullname = view.findViewById(R.id.tv_fullname_info);
        tv_phone = view.findViewById(R.id.tv_phone_info);
        recyclerView = view.findViewById(R.id.id_lsu_muaHang);
        recyclerView.setHasFixedSize(true);

        userDAO = new UserDAO(getContext());
        billDAO = new BillDAO(getContext());

        SharedPreferences preferences = getActivity().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String username = preferences.getString("USERNAME", "");
        objUser = userDAO.selectOneWithUsername(username);

        tv_fullname.setText(objUser.getFullname());
        tv_username.setText("User name: "+objUser.getUsername());
        tv_phone.setText("SĐT: "+objUser.getPhone());

        adapter = new BillHistoryAdapter(getContext(), billDAO, new ClickItemBillListener() {
            @Override
            public void onClickItemBIll(Bill obj) {
                Intent intent = new Intent(getContext(), HistoryBillDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("objBillUser", obj);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        arrayList = billDAO.selectAllForUser(objUser.getId());
        Collections.reverse(arrayList);
        adapter.setData(arrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDIalogEdit(objUser,getContext() );

            }
        });

    }

    public void showDIalogEdit(User obj, Context context){
        final Dialog dialog = new Dialog(context, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert);
        dialog.setContentView(R.layout.dialog_edit_user);

        TextInputEditText ed_fullname = dialog.findViewById(R.id.ed_edit_fullname);
        ed_fullname.setText(obj.getFullname());
        TextInputEditText ed_username = dialog.findViewById(R.id.ed_edit_username);
        ed_username.setText(obj.getUsername());
        TextInputEditText ed_phone = dialog.findViewById(R.id.ed_edit_phone);
        ed_phone.setText(obj.getPhone());

        Button btnSave = dialog.findViewById(R.id.btnSaveEditUser);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( ed_fullname.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Vui lòng nhập họ tên!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( ed_username.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Vui lòng nhập tên đăng nhập!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( ed_phone.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
                    return;
                }

                obj.setFullname(ed_fullname.getText().toString().trim());
                obj.setUsername(ed_username.getText().toString().trim());
                obj.setPhone(ed_phone.getText().toString().trim());

                int res = userDAO.updateRow(obj);
                if ( res > 0){
                    tv_fullname.setText(objUser.getFullname());
                    tv_username.setText("User name: "+objUser.getUsername());
                    tv_phone.setText("SĐT: "+objUser.getPhone());
                    Toast.makeText(context, "Thay đổi thành công!", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(context, "Thay đổi thất bại!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();

        arrayList = billDAO.selectAllForUser(objUser.getId());
        Collections.reverse(arrayList);
        adapter.setData(arrayList);

    }
}