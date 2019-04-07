package com.seok.seok.wowsup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//로그인시 선택할수있는 액티비티
public class SelectActivity extends AppCompatActivity {

    private Button btnLogin, btnSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);


        //로그인 버튼을 눌렀을 경우
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectActivity.this, LoginActivity.class));
            }
        });
        //회원가입 버튼을 눌렀을 경우
        btnSignup = (Button)findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectActivity.this, RegisterActivity.class));
            }
        });
    }
}
