package tranghtmph26263.fpoly.ungdungbanxedap.chao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import tranghtmph26263.fpoly.ungdungbanxedap.MainActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.R;
import tranghtmph26263.fpoly.ungdungbanxedap.admin.AdminActivity;
import tranghtmph26263.fpoly.ungdungbanxedap.dao.UserDAO;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText ed_username, ed_password;
    CheckBox chb_remember;
    Button btn_login;
    TextView txt_reg;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();

        SharedPreferences sharedPreferences = getSharedPreferences("ADMIN_INFO",MODE_PRIVATE );
        String user = sharedPreferences.getString("USERNAME", "admin");
        String pass = sharedPreferences.getString("PASSWORD", "123");
        boolean check = sharedPreferences.getBoolean("CHECK", false);

        userDAO = new UserDAO(this);
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

                if (u.equals(user) && p.equals(pass) ){
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công tài khoản Admin!", Toast.LENGTH_SHORT).show();
                    RememberAdmin(u, p, chb_remember.isChecked());

                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(intent);
                }else{
                    boolean check = userDAO.CheckLogin(u,p);
                    if ( check){
                        Log.d("zzzz", "check: "+check);
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        RememberUser(u, p, chb_remember.isChecked());

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        finish();

                    }else{
                        Log.d("zzzz", "check: "+check);
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
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

    public void RememberAdmin( String username, String password, boolean status){
        SharedPreferences sharedPreferences = getSharedPreferences("ADMIN_INFO",MODE_PRIVATE );
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

    public void RememberUser( String username, String password, boolean status){
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