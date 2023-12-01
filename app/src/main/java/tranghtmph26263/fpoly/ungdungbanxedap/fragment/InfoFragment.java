package tranghtmph26263.fpoly.ungdungbanxedap.fragment;

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
import android.widget.TextView;

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
        TextView tv_username, tv_fullname, tv_phone;


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
        tv_username.setText("USER NAME: "+objUser.getUsername());
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

    }

    @Override
    public void onResume() {
        super.onResume();

        arrayList = billDAO.selectAllForUser(objUser.getId());
        Collections.reverse(arrayList);
        adapter.setData(arrayList);
    }
}