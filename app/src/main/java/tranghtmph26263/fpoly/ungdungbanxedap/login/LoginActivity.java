package tranghtmph26263.fpoly.ungdungbanxedap.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.admin.AdminActivity;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText ed_username, ed_password;
    CheckBox chb_remember;
    Button btn_login;
    TextView txt_reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();

        SharedPreferences sharedPreferences = getSharedPreferences("USER_INFO",MODE_PRIVATE );
        String user = sharedPreferences.getString("USERNAME", "");
        String pass = sharedPreferences.getString("PASSWORD", "");
        boolean check = sharedPreferences.getBoolean("CHECK", false);

        ed_username.setText(user);
        ed_password.setText(pass);
        chb_remember.setChecked(check);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u = ed_username.getText().toString().trim();
                String p = ed_password.getText().toString().trim();

                if ( u.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( p.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập pass", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (u.equals("admin") && p.equals("123") ){
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công tài khoản Admin!", Toast.LENGTH_SHORT).show();
                    Remember(u, p, chb_remember.isChecked());

                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        txt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }

    public void Remember( String username, String password, boolean status){
        SharedPreferences sharedPreferences = getSharedPreferences("USER_INFO",MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if ( !status){
            editor.clear();
        }else{
            editor.putString("PASSWORD",password );
            editor.putString("USERNAME",username );
            editor.putBoolean("CHECK",true );
        }
        editor.commit();
    }

    public void AnhXa(){
        ed_username = findViewById(R.id.ed_username_login);
        ed_password = findViewById(R.id.ed_password_login);
        chb_remember = findViewById(R.id.chb_remember);
        btn_login = findViewById(R.id.btn_login);
        txt_reg = findViewById(R.id.txt_dangky);
    }
}