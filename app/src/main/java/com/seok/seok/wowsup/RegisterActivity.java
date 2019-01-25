package com.seok.seok.wowsup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private Button btnJoin;
    private EditText edtID, edtPW, edtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtID = findViewById(R.id.register_edittext_id);
        edtPW = findViewById(R.id.register_edittext_pwd);
        edtEmail = findViewById(R.id.register_edittext_email);
        btnJoin = findViewById(R.id.register_button_join);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
