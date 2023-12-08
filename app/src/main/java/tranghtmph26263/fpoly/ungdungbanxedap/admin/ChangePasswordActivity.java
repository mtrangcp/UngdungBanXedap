package tranghtmph26263.fpoly.ungdungbanxedap.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.chao.LoginActivity;

public class ChangePasswordActivity extends AppCompatActivity {
    TextInputEditText ed_oldPass, ed_newPass, ed_reNewPass;
    Button btnSave, btnHuy;
    ImageView imghome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ed_oldPass = findViewById(R.id.ed_oldPass);
        ed_newPass = findViewById(R.id.ed_newPass);
        ed_reNewPass = findViewById(R.id.ed_reNewPass);
        btnSave = findViewById(R.id.btn_save_changePass);
        btnHuy = findViewById(R.id.btn_huy_changePass);
        imghome = findViewById(R.id.id_changePass_home);
        imghome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangePasswordActivity.this, AdminActivity.class));
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_oldPass.getText().toString().trim().isEmpty() ){
                    Toast.makeText(ChangePasswordActivity.this, "Vui lòng nhập mật khẩu cũ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ed_newPass.getText().toString().trim().isEmpty() ){
                    Toast.makeText(ChangePasswordActivity.this, "Vui lòng nhập mật khẩu mới!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ed_reNewPass.getText().toString().trim().isEmpty() ){
                    Toast.makeText(ChangePasswordActivity.this, "Vui lòng xác nhận mật khẩu mới!", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences pref = getApplication().getSharedPreferences("ADMIN_INFO", MODE_PRIVATE);
                String savedPass = pref.getString("PASSWORD", "123");

                Log.d("zzzzzz", "pass cu: "+savedPass + "  Và :"+ ed_oldPass.getText().toString().trim());


                if ( !savedPass.equals(ed_oldPass.getText().toString().trim())){
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu cũ không đúng!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( !ed_newPass.getText().toString().trim().equals(ed_reNewPass.getText().toString().trim())){
                    Toast.makeText(ChangePasswordActivity.this, "Hãy xác nhận lại mật khẩu mới!", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences.Editor editor = pref.edit();
                editor.putString("PASSWORD",ed_newPass.getText().toString().trim() );
                editor.commit();
                Toast.makeText(ChangePasswordActivity.this, "Thay đổi thành công!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));

            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_oldPass.setText("");
                ed_newPass.setText("");
                ed_reNewPass.setText("");
            }
        });


    }
}