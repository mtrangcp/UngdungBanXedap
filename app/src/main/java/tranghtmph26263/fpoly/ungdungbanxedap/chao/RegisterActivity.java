package tranghtmph26263.fpoly.ungdungbanxedap.chao;

import androidx.appcompat.app.AppCompatActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.UserDAO;
import tranghtmph26263.fpoly.ungdungbanxedap.entity.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText ed_username, ed_pass, ed_fullname, ed_phone;
    Button btnSave, btnHuy;
    TextView tv_login;
    UserDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ed_fullname = findViewById(R.id.ed_fullname_reg);
        ed_username = findViewById(R.id.ed_username_reg);
        ed_pass = findViewById(R.id.ed_password_reg);
        ed_phone = findViewById(R.id.ed_phone_reg);
        btnSave = findViewById(R.id.btn_reg);
        btnHuy = findViewById(R.id.btn_huy_reg);
        tv_login =  findViewById(R.id.txt_dangnhap);
        dao = new UserDAO(RegisterActivity.this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ed_fullname.getText().toString().trim().isEmpty() ){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập fullname!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(ed_username.getText().toString().trim().isEmpty() ){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập username!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(ed_pass.getText().toString().trim().isEmpty() ){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(ed_phone.getText().toString().trim().isEmpty() ){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập phone!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (ed_phone.getText().toString().trim().length() >10 || ed_phone.getText().toString().trim().equals("0000000000") ){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đúng phone có 10 số!", Toast.LENGTH_SHORT).show();
                    return;
                }

                User obj = new User();
                obj.setFullname(ed_fullname.getText().toString().trim());
                obj.setUsername(ed_username.getText().toString().trim());
                obj.setPassword(ed_pass.getText().toString().trim());
                obj.setPhone(ed_phone.getText().toString().trim());
                obj.setActive(1);

                long res = dao.insertNew(obj);
                if ( res > 0){
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_pass.setText("");
                ed_username.setText("");
                ed_fullname.setText("");
                ed_phone.setText("");
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }

}