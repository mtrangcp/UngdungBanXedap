package tranghtmph26263.fpoly.ungdungbanxedap.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.BillAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.BillHistoryAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.SpinCategoryAdapter;
import tranghtmph26263.fpoly.ungdungbanxedap.adapter.SpinStatusBill;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BillDAO dao = new BillDAO(getContext());
        userDAO = new UserDAO(getContext());
        String TAG = "aaaa";


        SharedPreferences preferences = getActivity().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String username = preferences.getString("USERNAME", "");
        objUser = userDAO.selectOneWithUsername(username);

        ArrayList<String> list = new ArrayList();
        list.add("Tất cả");
        list.add("Đang chờ xác nhận");
        list.add("Đã xác nhận");
        list.add("Đã bị hủy");
        list.add("Đang giao hàng");
        list.add("Đã giao thành công");
        ImageView img_edit = view.findViewById(R.id.img_edit_user);
        Spinner spinner = view.findViewById(R.id.id_spinner_status_user);

        ArrayAdapter<String> adapterStatus = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterStatus);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object chuoi = spinner.getSelectedItem();
                if (chuoi != null){
                    if (chuoi.toString().equals("Tất cả") ){
                        arrayList = dao.selectAllForUser(objUser.getId());
                        Collections.reverse(arrayList);
                        adapter.setData(arrayList);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);

                    }else if (chuoi.toString().equals("Đang chờ xác nhận") ){
                        arrayList = dao.selectWithStatusForUser(0,objUser.getId() );
                        Collections.reverse(arrayList);
                        adapter.setData(arrayList);
                        if ( arrayList.isEmpty()){
                            Toast.makeText(getContext(), "Chưa có đơn hàng nào đang chờ xác nhận!", Toast.LENGTH_SHORT).show();
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);

                    }else if (chuoi.toString().equals("Đã xác nhận") ){
                        arrayList = dao.selectWithStatusForUser(1, objUser.getId());
                        Collections.reverse(arrayList);
                        adapter.setData(arrayList);
                        if ( arrayList.isEmpty()){
                            Toast.makeText(getContext(), "Chưa có đơn hàng nào đã xác nhận!", Toast.LENGTH_SHORT).show();
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);

                    }else if (chuoi.toString().equals("Đã bị hủy") ){
                        arrayList = dao.selectWithStatusForUser(2, objUser.getId());
                        Collections.reverse(arrayList);
                        adapter.setData(arrayList);
                        if ( arrayList.isEmpty()){
                            Toast.makeText(getContext(), "Không có đơn hàng nào đã bị hủy!", Toast.LENGTH_SHORT).show();
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);

                    }else if (chuoi.toString().equals("Đang giao hàng") ){
                        arrayList = dao.selectWithStatusForUser(3, objUser.getId());
                        Collections.reverse(arrayList);
                        adapter.setData(arrayList);
                        if ( arrayList.isEmpty()){
                            Toast.makeText(getContext(), "Không có đơn hàng nào đang giao!", Toast.LENGTH_SHORT).show();
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);

                    }else if (chuoi.toString().equals("Đã giao thành công") ){
                        arrayList = dao.selectWithStatusForUser(4, objUser.getId());
                        Collections.reverse(arrayList);
                        adapter.setData(arrayList);
                        if ( arrayList.isEmpty()){
                            Toast.makeText(getContext(), "Không có đơn hàng nào đã giao!", Toast.LENGTH_SHORT).show();
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);
                    }

                }else{
                    Toast.makeText(getContext(), "da chon: rong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        tv_username = view.findViewById(R.id.tv_username_info);
        tv_fullname = view.findViewById(R.id.tv_fullname_info);
        tv_phone = view.findViewById(R.id.tv_phone_info);
        recyclerView = view.findViewById(R.id.id_lsu_muaHang);
        recyclerView.setHasFixedSize(true);

        userDAO = new UserDAO(getContext());
        billDAO = new BillDAO(getContext());


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

                    SharedPreferences preferences = getActivity().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("USERNAME",objUser.getUsername() );
                    editor.commit();

                    Toast.makeText(context, "Thay đổi thành công!", Toast.LENGTH_SHORT).show();

//                    FragmentManager manager = requireActivity().getSupportFragmentManager();
//                    Fragment fragment = (InfoFragment) manager.findFragmentById(R.id.fragment_change_info);
//                    if ( fragment != null){
//                        manager.beginTransaction().remove(fragment).commit();
//                    }else {
//                        Toast.makeText(context, "k tim thay fragment", Toast.LENGTH_SHORT).show();
//                    }

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