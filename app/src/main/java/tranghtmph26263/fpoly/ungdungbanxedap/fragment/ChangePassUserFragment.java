package tranghtmph26263.fpoly.ungdungbanxedap.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.admin.ChangePasswordActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.UserDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.User;

public class ChangePassUserFragment extends Fragment {

    public ChangePassUserFragment() {
    }
    public static ChangePassUserFragment newInstance() {
        ChangePassUserFragment fragment = new ChangePassUserFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_pass_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputEditText ed_oldPass, ed_newPass, ed_reNewPass;
        Button btn_save, btn_cancel;
        UserDAO dao;

        ed_oldPass = view.findViewById(R.id.ed_oldPass_user);
        ed_newPass = view.findViewById(R.id.ed_newPass_user);
        ed_reNewPass = view.findViewById(R.id.ed_reNewPass_user);
        btn_save = view.findViewById(R.id.btn_save_changePass_user);
        btn_cancel = view.findViewById(R.id.btn_huy_changePass_user);
        dao = new UserDAO(getContext());
        
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_oldPass.setText("");
                ed_newPass.setText("");
                ed_reNewPass.setText("");
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( ed_oldPass.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập mật khẩu cũ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( ed_newPass.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập mật khẩu mới!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( ed_reNewPass.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng xác nhận mật khẩu mới!", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences preferences = getActivity().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
                String username = preferences.getString("USERNAME", "");
                String mkCu = preferences.getString("PASSWORD", "");

                if ( !mkCu.equals(ed_oldPass.getText().toString().trim())){
                    Toast.makeText(getContext(), "Mật khẩu cũ không đúng!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( !ed_newPass.getText().toString().trim().equals(ed_reNewPass.getText().toString().trim())){
                    Toast.makeText(getContext(), "Hãy xác nhận lại mật khẩu mới!", Toast.LENGTH_SHORT).show();
                    return;
                }

                User obj = dao.selectOneWithUsername(username);
                int res = dao.changePass(obj,ed_newPass.getText().toString().trim());
                if ( res > 0){
                    Toast.makeText(getContext(), "Thay đổi thành công!", Toast.LENGTH_SHORT).show();
                    ed_oldPass.setText("");
                    ed_newPass.setText("");
                    ed_reNewPass.setText("");
                }else{
                    Toast.makeText(getContext(), "Thay đổi thất bại!", Toast.LENGTH_SHORT).show();
                }
                
            }
        });
    }
}