package com.example.nguye.mylibr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText edtUsername, edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        linkStupid();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUsername.getText().toString().equals("admin")&&edtPassword.getText().toString().equals("admin")){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    edtUsername.setText(""); edtPassword.setText("");
                }else {
                    Toast.makeText(LoginActivity.this, "Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void linkStupid(){
        btnLogin = (Button)findViewById(R.id.btnLogin);
        edtUsername = (EditText)findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
    }
}
